package com.example.schedule.service;

import com.example.schedule.dto.*;
import com.example.schedule.entity.Schedule;
import com.example.schedule.exception.ScheduleNotFoundException;
import com.example.schedule.exception.WrongPasswordException;
import com.example.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository repository;

    @Transactional
    @Override
    public ResponseDto saveSchedule(RequestDto request) {
        Optional<UseridResponseDto> result = repository.findUser(request.getUser());
        if (result.isEmpty()) {
            Long userId = repository.saveUser(request.getUser(), request.getMail());
            request.setUserId(userId);
        }
        else {
            request.setUserId(result.get().getUserid());
        }
        Schedule schedule = new Schedule(request.getUserid(), request.getUser(), request.getPassword(), request.getTodo());
        return repository.saveSchedule(schedule);
    }

    @Override
    public ScheduleInfoResponseDto findScheduleById(Long id) {
        return idCheck(id);
    }

    @Override
    public List<ScheduleInfoResponseDto> findScheduleByUserId(Long userId) {
        List<ScheduleInfoResponseDto> userIdScheduleList = repository.findScheduleByUserId(userId);
        if(userIdScheduleList.isEmpty()){
            throw new ScheduleNotFoundException();
        }
        return userIdScheduleList;
    }

    @Override
    public List<ScheduleInfoResponseDto> findSchedulePage(int pageNumber, int pageSize) {
        return repository.findSchedulePage(pageNumber, pageSize);
    }

    @Transactional
    @Override
    public ScheduleInfoResponseDto updateSchedule(RequestDto request) {
        passwordCheck(request);
        repository.updateSchedule(request);
        return idCheck(request.getId());
    }

    @Override
    public void deleteSchedule(RequestDto request) {
        passwordCheck(request);
        repository.deleteSchedule(request);
    }

    private ScheduleInfoResponseDto idCheck(Long id) {
        Optional<ScheduleInfoResponseDto> result = repository.findScheduleById(id);
        if (result.isEmpty()) {
            throw new ScheduleNotFoundException();
        }
        return result.get();
    }

    private void passwordCheck(RequestDto request) {
        Optional<PasswordResponseDto> password = repository.passwordGet(request.getId());
        if (password.isEmpty()) {
            throw new ScheduleNotFoundException();
        }

        if(!request.getPassword().equals(password.get().getPassword())) {
            throw new WrongPasswordException();
        }
        request.setUserId(password.get().getUserid());
    }
}
