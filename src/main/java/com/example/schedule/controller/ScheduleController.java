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

    // 일정 생성 (user, password, main, todo를 json 형태로 요청 받음)
    @PostMapping
    public ResponseEntity<ResponseDto> createSchedule(@Valid @RequestBody RequestDto request) {

        return new ResponseEntity<>(service.saveSchedule(request), HttpStatus.CREATED);
    }

    // 유저 ID를 이용한 일정 조회 (userId를 쿼리 파라미터로 요청 받음)
    @GetMapping
    public List<ScheduleInfoResponseDto> findScheduleByUserId(@RequestParam Long userId) {

        return service.findScheduleByUserId(userId);
    }

    // 일정 ID를 이용한 일정 조회 (Id를 Path 파라미터로 요청 받음)
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleInfoResponseDto> findScheduleById(@PathVariable Long id) {

        return new ResponseEntity<>(service.findScheduleById(id), HttpStatus.OK);
    }

    // 페이지 일정 전체 조회 (pageNumber, pageSize를 쿼리 파라미터로 요청 받음)
    @GetMapping("/page")
    public List<ScheduleInfoResponseDto> findSchedulePage(@RequestParam(defaultValue = "1") int pageNumber, @RequestParam(defaultValue = "10") int pageSize ) {

        return service.findSchedulePage(pageNumber-1, pageSize);
    }

    // 일정 수정 (id, user, password, todo를 json 형태로 요청 받음)
    @PutMapping
    public ResponseEntity<ScheduleInfoResponseDto> updateSchedule(@Valid @RequestBody RequestDto request) {

        return new ResponseEntity<>(service.updateSchedule(request), HttpStatus.OK);
    }

    // 일정 삭제 (id, password를 json 형태로 요청 받음)
    @DeleteMapping
    public void deleteSchedule(@RequestBody RequestDto request) {

        service.deleteSchedule(request);
    }
}
