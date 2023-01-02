package com.example.reservation.repository;

import com.example.reservation.entity.Account;
import com.example.reservation.entity.Room;
import com.example.reservation.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    public List<Schedule> findAllByRsvRm(Room room);

    public Optional<Schedule> findByOwnerAndRsvCnNo(Account usrId, Long cnNo);

    @Query(value = "SELECT COUNT(*) FROM TH_RSV_CN " +
            "" +
            " WHERE " +
            " ( " +
            " ( " +
            " STR_TO_DATE(:#{#sc.reserveStart},'%Y/%m/%d %H:%i')>=STR_TO_DATE(RSV_STR_DT,'%Y/%m/%d %H:%i') AND STR_TO_DATE(RSV_END_DT,'%Y/%m/%d %H:%i')>STR_TO_DATE(:#{#sc.reserveStart},'%Y/%m/%d %H:%i')" +
            " ) " +
            " OR " +
            " (  " +
            "STR_TO_DATE(:#{#sc.reserveEnd},'%Y/%m/%d %H:%i')>=STR_TO_DATE(RSV_STR_DT,'%Y/%m/%d %H:%i') AND STR_TO_DATE(RSV_END_DT,'%Y/%m/%d %H:%i')>STR_TO_DATE(:#{#sc.reserveEnd},'%Y/%m/%d %H:%i')" +
            " ) " +
            " OR " +
            " ((" +
            " STR_TO_DATE(RSV_STR_DT,'%Y/%m/%d %H:%i')>=STR_TO_DATE(:#{#sc.reserveStart},'%Y/%m/%d %H:%i') AND STR_TO_DATE(:#{#sc.reserveEnd},'%Y/%m/%d %H:%i')>STR_TO_DATE(RSV_STR_DT,'%Y/%m/%d %H:%i')" +
            " ) " +
            " AND " +
            " ( " +
            "STR_TO_DATE(RSV_END_DT,'%Y/%m/%d %H:%i')>STR_TO_DATE(:#{#sc.reserveStart},'%Y/%m/%d %H:%i') AND STR_TO_DATE(:#{#sc.reserveEnd},'%Y/%m/%d %H:%i')>=STR_TO_DATE(RSV_END_DT,'%Y/%m/%d %H:%i')" +
            " )) " +
            " OR " +
            " ( " +
            " STR_TO_DATE(:#{#sc.reserveStart},'%Y/%m/%d %H:%i')=STR_TO_DATE(RSV_STR_DT,'%Y/%m/%d %H:%i') AND STR_TO_DATE(RSV_END_DT,'%Y/%m/%d %H:%i')=STR_TO_DATE(:#{#sc.reserveEnd},'%Y/%m/%d %H:%i')" +
            " ) " +
            " ) " +
            "" +
            " AND " +
            "" +
            " RM_ID=:#{#sc.rsvRm.rmId} ", nativeQuery = true)
    int findByAvailNativeQuery(@Param(value = "sc") Schedule sc);


    @Query(value = " SELECT COUNT(RM_ID) FROM TH_RSV_CN " +
            " WHERE " +
            " STR_TO_DATE(:#{#sc.reserveStart},'%Y/%m/%d')=STR_TO_DATE(RSV_STR_DT,'%Y/%m/%d') " +
            " AND " +
            " STR_TO_DATE(:#{#sc.reserveStart},'%Y/%m/%d')=STR_TO_DATE(RSV_END_DT,'%Y/%m/%d') " +
            " AND " +
            " RM_ID=:#{#sc.rsvRm.rmId}", nativeQuery = true)
    int findByAvailAllDayNativeQuery(@Param(value = "sc") Schedule sc);

}

