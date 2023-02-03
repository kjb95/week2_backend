package com.example.week2_backend.controller;

import com.example.week2_backend.dto.ChartDataDto;
import com.example.week2_backend.dto.UserAccessDto;
import com.example.week2_backend.entity.UserAccess;
import com.example.week2_backend.service.UserAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user-access")
public class UserAccessController {

    private final UserAccessService userAccessService;

    /**
     * @param userAccessDto 추가할 유저 접근 데이터
     * @description 유저 접근 데이터 추가
     */
    @PostMapping()
    public void createUserAccess(@RequestBody UserAccessDto userAccessDto) {
        userAccessService.createUserAccess(userAccessDto);
    }

    /**
     * @return 모든 유저접근 데이터 (id값 제외)
     * @description 모든 유저접근 데이터 조회
     */
    @GetMapping("/all")
    public List<UserAccessDto> findAllUserAccess() {
        return userAccessService.findAllUserAccessDto();
    }

    /**
     * @param fieldName 특정 필드 이름
     * @return 특정 필드 데이터 모음, 특정 필드 이름
     * @description 모든 유저접근 데이터 중 특정 필드만 조회
     * Long 타입의 필드만 조회 가능
     */
    @GetMapping("/all/{fieldName}")
    public ChartDataDto findAllSpecificField(@PathVariable String fieldName) {
        return userAccessService.findAllSpecificFieldAndFieldName(fieldName);
    }

    /**
     * @param id 조회할 유저 접근 데이터 id
     * @return 조회할 유저 접근 데이터
     * @description id로 유저 접근 데이터 조회
     */
    @GetMapping("/{id}")
    public UserAccessDto findUserAccessById(@PathVariable String id) {
        return userAccessService.findUserAccessById(id);
    }

    /**
     * @param userAccessDto 업데이트 할 유저 접근 데이터
     * @param id            수정할 유저 접근 데이터의 id
     * @description 유저접근 데이터 수정
     */
    @PutMapping("/{id}")
    public void updateUserAccess(@RequestBody UserAccessDto userAccessDto, @PathVariable String id) {
        userAccessService.updateUserAccess(userAccessDto, id);
    }

    /**
     * @param id 삭제할 유저 접근 데이터의 id
     * @description 모든 유저접근 데이터 삭제
     */
    @DeleteMapping("/{id}")
    public void deleteUserAccessById(@PathVariable String id) {
        userAccessService.deleteUserAccessById(id);
    }

    /**
     * @return 모든 유저 접근 데이터
     * @description 모든 유저 접근 데이터 조회 (id 포함)
     * 엔티티 자체를 가져오는 테스트용 메소드
     */
    @GetMapping("/all/with-id")
    public List<UserAccess> findAllUserAccessWithId() {
        return userAccessService.findAllUserAccessWithId();
    }
}
