package com.example.schedule.service;

import com.example.schedule.dto.PasswordDto;
import com.example.schedule.dto.RequestDto;
import com.example.schedule.dto.ResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.Repository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ScheduleServiceImpl implements Service{
    private final Repository repository;

    public ScheduleServiceImpl(Repository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseDto saveSchedule(RequestDto request) {
        Schedule schedule = new Schedule(request.getUser(), request.getPassword(), request.getTodo());
        return repository.saveSchedule(schedule);
    }

    @Override
    public ResponseDto findScheduleById(Long id) {
        Optional<ResponseDto> result = repository.findScheduleById(id);
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 id를 찾을 수 없습니다.");
        }
        return result.get();
    }

    @Override
    public List<ResponseDto> findScheduleByReq(RequestDto request) {
        return repository.findScheduleByReq(request.getUser(),request.getUpdatedate());
    }

    @Override
    public ResponseDto updateSchedule(RequestDto request) {
        passwordCheck(request);
        repository.updateSchedule(request);
        Optional<ResponseDto> result = repository.findScheduleById(request.getId());
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 id를 찾을 수 없습니다.");
        }
        return result.get();
    }

    @Override
    public void deleteSchedule(RequestDto request) {
        passwordCheck(request);
        repository.deleteSchedule(request);
    }

    private void passwordCheck(RequestDto request) {
        Optional<PasswordDto> password = repository.passwordGet(request.getId());
        if (password.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 id를 찾을 수 없습니다.");
        }

        if(!request.getPassword().equals(password.get().getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 틀렸습니다.");
        }
    }
}
