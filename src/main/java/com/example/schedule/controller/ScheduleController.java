package com.example.schedule.controller;

import com.example.schedule.dto.RequestDto;
import com.example.schedule.dto.ResponseDto;
import com.example.schedule.dto.ScheduleInfoResponseDto;
import com.example.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService service;

    @PostMapping
    public ResponseEntity<ResponseDto> createSchedule(@Valid @RequestBody RequestDto request) {
        return new ResponseEntity<>(service.saveSchedule(request), HttpStatus.CREATED);

    }

    @GetMapping
    public List<ScheduleInfoResponseDto> findScheduleByUserId(@RequestParam Long userId) {
        return service.findScheduleByUserId(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleInfoResponseDto> findScheduleById(@PathVariable Long id) {
        return new ResponseEntity<>(service.findScheduleById(id), HttpStatus.OK);
    }

    @GetMapping("/page")
    public List<ScheduleInfoResponseDto> findSchedulePage(@RequestParam(defaultValue = "1") int pageNumber, @RequestParam(defaultValue = "10") int pageSize ) {
        return service.findSchedulePage(pageNumber-1, pageSize);
    }

    @PutMapping
    public ResponseEntity<ScheduleInfoResponseDto> updateSchedule(@Valid @RequestBody RequestDto request) {
        return new ResponseEntity<>(service.updateSchedule(request), HttpStatus.OK);
    }

    @DeleteMapping
    public void deleteSchedule(@RequestBody RequestDto request) {
        service.deleteSchedule(request);
    }
}
