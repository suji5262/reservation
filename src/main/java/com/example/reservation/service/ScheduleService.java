package com.example.reservation.service;

import com.example.reservation.dto.ScheduleInsertRequest;
import com.example.reservation.dto.ScheduleResponse;
import com.example.reservation.dto.ScheduleUpdateRequest;
import com.example.reservation.entity.Account;
import com.example.reservation.entity.Room;
import com.example.reservation.entity.Schedule;
import com.example.reservation.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private final ScheduleRepository repo;


    //스케쥴을 조회합니다.
    //회의실ID가 조회 조건입니다.
    public List<ScheduleResponse> schedules(String id) {
        return repo.findAllByRsvRm(Room.builder().rmId(Long.valueOf(id)).build()).stream()
                .map(schedule -> new ScheduleResponse(schedule))
                .collect(Collectors.toList());
    }

    //특정 스케쥴을 조회합니다.
    //스케쥴 ID가 조회 조건입니다.
    public ScheduleResponse detailSchedules(String id) {
        Schedule sr = repo.findById(Long.valueOf(id)).orElse(null);
        if (sr != null) {
            return new ScheduleResponse(sr);
        } else {
            return null;
        }
    }

    //단순히 스케쥴을 삽입합니다.
    //성공:success, 실패:failed
    //고급 : 다른 스케쥴이 겹치지 않을때만 삽입하세요.
    public String insertSchedule(ScheduleInsertRequest req) {

        try {
            Schedule sc = req.toEntity();
            if (sc.getReserveAllday().equals("1")) {
                int result = repo.findByAvailAllDayNativeQuery(sc);
                if (result == 0) {
                    repo.save(sc);
                    return "success";
                } else {
                    return "failed";
                }
            } else {
                int result = repo.findByAvailNativeQuery(sc);
                if (result == 0) {
                    repo.save(sc);
                    return "success";
                } else {
                    return "failed";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "failed";
        }
    }

    //스케쥴을 삭제합니다.
    //성공:success, 실패:failed
    public String deleteSchedule(String id) {

        try {
            repo.deleteById(Long.valueOf(id));
        } catch (Exception e) {
            e.printStackTrace();
            return "failed";
        }

        return "success";
    }

    //스케쥴을 수정합니다.
    //성공:success, 실패:failed
    //고급 : 다른 스케쥴이 겹치지 않을때만 수정하세요.
    public String updateSchedule(ScheduleUpdateRequest req) {
        try {
            Schedule sc = req.toEntity();
            System.out.println(sc);
            if (sc.getReserveAllday().equals("1")) {
                int result = repo.findByAvailAllDayNativeQuery(sc);
                if (result == 0) {
                    repo.findByOwnerAndRsvCnNo(Account.builder()
                            .usrId(req.getUsrId())
                            .build(), Long.valueOf(req.getId())).ifPresent(en -> repo.save(sc));
                    return "success";
                } else {
                    return "failed";
                }
            } else {
                int result = repo.findByAvailNativeQuery(sc);
                if (result == 0) {
                    repo.findByOwnerAndRsvCnNo(Account.builder()
                            .usrId(req.getUsrId())
                            .build(), Long.valueOf(req.getId())).ifPresent(en -> repo.save(sc));
                    return "success";
                } else {
                    return "failed";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "failed";
        }
    }

    // 필드 캡슐화
    @Autowired
    public ScheduleService(ScheduleRepository repo) {
        this.repo = repo;
    }
}
