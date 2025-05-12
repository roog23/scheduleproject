package com.example.schedule.service;

import com.example.schedule.dto.*;
import com.example.schedule.entity.Schedule;
import com.example.schedule.exception.ScheduleNotFoundException;
import com.example.schedule.exception.UserScheduleNotFoundException;
import com.example.schedule.exception.WrongPasswordException;
import com.example.schedule.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ScheduleServiceImpl implements Service{
    private final Repository repository;

    public ScheduleServiceImpl(Repository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public ResponseDto saveSchedule(RequestDto request) {
        Optional<UseridDto> result = repository.findUser(request.getUser());
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
    public ResponseDto findScheduleById(Long id) {
        return ScheduleCheck(id);
    }

    @Override
    public List<ScheduleListDto> findScheduleByUserId(Long userId) {
        List<ScheduleListDto> userIdScheduleList = repository.findScheduleByUserId(userId);
        if(userIdScheduleList.isEmpty()){
            throw new UserScheduleNotFoundException();
        }
        return userIdScheduleList;
    }

    @Override
    public List<ScheduleListDto> findSchedulePage(int pageNumber, int pageSize) {
        return repository.findSchedulePage(pageNumber, pageSize);
    }

    @Transactional
    @Override
    public ResponseDto updateSchedule(RequestDto request) {
        passwordCheck(request);
        repository.updateSchedule(request);
        return ScheduleCheck(request.getId());
    }

    @Override
    public void deleteSchedule(RequestDto request) {
        passwordCheck(request);
        repository.deleteSchedule(request);
    }

    private ResponseDto ScheduleCheck(Long id) {
        Optional<ResponseDto> result = repository.findScheduleById(id);
        if (result.isEmpty()) {
            throw new ScheduleNotFoundException();
        }
        return result.get();
    }

    private void passwordCheck(RequestDto request) {
        Optional<PasswordDto> password = repository.passwordGet(request.getId());
        if (password.isEmpty()) {
            throw new ScheduleNotFoundException();
        }

        if(!request.getPassword().equals(password.get().getPassword())) {
            throw new WrongPasswordException();
        }
        request.setUserId(password.get().getUserid());
    }
}
