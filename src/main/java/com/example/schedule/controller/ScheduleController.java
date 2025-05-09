package com.example.schedule.controller;

import com.example.schedule.dto.RequestDto;
import com.example.schedule.dto.ResponseDto;
import com.example.schedule.service.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final Service service;

    public ScheduleController(Service service) {
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<ResponseDto> createSchedule(@RequestBody RequestDto request) {
        return new ResponseEntity<>(service.saveSchedule(request), HttpStatus.CREATED);

    }

    @GetMapping
    public List<ResponseDto> findScheduleByReq(@RequestBody RequestDto request) {
        return service.findScheduleByReq(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> findScheduleById(@PathVariable Long id) {
        return new ResponseEntity<>(service.findScheduleById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateSchedule(@RequestBody RequestDto request) {
        return new ResponseEntity<>(service.updateSchedule(request), HttpStatus.OK);
    }

    @DeleteMapping
    public void deleteSchedule(@RequestBody RequestDto request) {
        service.deleteSchedule(request);
    }
}
