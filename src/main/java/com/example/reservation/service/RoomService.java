package com.example.reservation.service;

import com.example.reservation.dto.RoomsResponse;
import com.example.reservation.repository.RoomRepository;
import com.example.reservation.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    final private RoomRepository repo;

    public List<RoomsResponse> rooms(String grId){
        return null;
    }

    @Autowired
    public RoomService(RoomRepository repo){
        this.repo=repo;
    }
}
