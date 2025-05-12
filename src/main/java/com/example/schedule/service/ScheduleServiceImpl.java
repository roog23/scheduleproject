package com.example.schedule.service;

import com.example.schedule.dto.*;
import com.example.schedule.entity.Schedule;
import com.example.schedule.exception.ScheduleNotFoundException;
import com.example.schedule.exception.UserScheduleNotFoundException;
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

    //일정 생성, 저장
    @Transactional
    @Override
    public ResponseDto saveSchedule(RequestDto request) {
        // 유저가 존재하는지 확인합니다.
        Optional<UseridResponseDto> result = repository.findUser(request.getUser());
        if (result.isEmpty()) { //유저가 존재하지 않는다면 유저를 생성해주고 유저 id를 가져옵니다.
            Long userId = repository.saveUser(request.getUser(), request.getMail());
            request.setUserId(userId);
        }
        else {  //유저가 존재한다면 해당 유저 id를 가져옵니다.
            request.setUserId(result.get().getUserid());
        }
        //일정을 생성하고 저장합니다.
        Schedule schedule = new Schedule(request.getUserid(), request.getUser(), request.getPassword(), request.getTodo());
        return repository.saveSchedule(schedule);
    }

    //일정 id를 이용한 조회하기
    @Override
    public ScheduleInfoResponseDto findScheduleById(Long id) {
        return idCheck(id); //일정 id 조회 결과가 비어있는지 확인하고 존재하면 값을 반환해줍니다.
    }

    //유저 id를 이용한 조회하기
    @Override
    public List<ScheduleInfoResponseDto> findScheduleByUserId(Long userId) {
        List<ScheduleInfoResponseDto> userIdScheduleList = repository.findScheduleByUserId(userId);
        if(userIdScheduleList.isEmpty()){   //유저 id의 조회 결과가 비어있다면 오류 처리합니다.
            throw new UserScheduleNotFoundException();
        }
        return userIdScheduleList;
    }

    //페이지에 해당하는 일정을 출력하기
    @Override
    public List<ScheduleInfoResponseDto> findSchedulePage(int pageNumber, int pageSize) {
        return repository.findSchedulePage(pageNumber, pageSize);
    }

    //일정을 수정하기
    @Transactional
    @Override
    public ScheduleInfoResponseDto updateSchedule(RequestDto request) {
        passwordCheck(request);     // 비밀번호가 일치하는지 확인해줍니다.
        repository.updateSchedule(request); // 일정을 수정해줍니다.
        return idCheck(request.getId());    // 일정 id 조회 결과가 비어있는지 확인하고 존재하면 값을 반환해줍니다.
    }

    //일정을 삭제하기
    @Override
    public void deleteSchedule(RequestDto request) {
        passwordCheck(request); // 비밀번호가 일치하는지 확인해줍니다.
        repository.deleteSchedule(request); // 일정을 삭제합니다.
    }

    //일정 id 조회 결과가 비어있는지 확인하고 존재하면 값을 반환해주는 기능입니다.
    private ScheduleInfoResponseDto idCheck(Long id) {
        Optional<ScheduleInfoResponseDto> result = repository.findScheduleById(id);
        if (result.isEmpty()) { // 일정 조회 결과가 비어있다면 오류 처리합니다.
            throw new ScheduleNotFoundException();
        }
        return result.get();
    }

    //비밀번호가 일치하는지 확인해주고 일치하면 userid를 등록해줍니다.
    private void passwordCheck(RequestDto request) {
        Optional<PasswordResponseDto> password = repository.passwordGet(request.getId());
        if (password.isEmpty()) {   // 일정 조회 결과가 비어있다면 오류 처리합니다.
            throw new ScheduleNotFoundException();
        }
        // 비밀번호가 일치하는지 확인하고 다르다면 오류 처리합니다.
        if(!request.getPassword().equals(password.get().getPassword())) {
            throw new WrongPasswordException();
        }
        // 비밀번호가 일치하면 userid를 등록해줍니다.
        request.setUserId(password.get().getUserid());
    }
}
