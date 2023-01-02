package com.example.reservation.service;

import com.example.reservation.dto.RoomsResponse;
import com.example.reservation.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    final private RoomRepository repo;

    //회의실을 조회합니다.
    public List<RoomsResponse> rooms(String grId) {
        return repo.findByGrId(Long.valueOf(grId)).stream()
                .map(rm -> new RoomsResponse(rm))
                .collect(Collectors.toList());
    }

    // 필드 캡슐화
    @Autowired
    public RoomService(RoomRepository repo) {
        this.repo = repo;
    }
}
