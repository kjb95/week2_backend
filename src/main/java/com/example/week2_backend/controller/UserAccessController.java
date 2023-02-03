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
     * 유저 접근 데이터 추가
     *
     * @param userAccessDto 추가할 유저 접근 데이터
     */
    @PostMapping()
    public void createUserAccess(@RequestBody UserAccessDto userAccessDto) {
        userAccessService.createUserAccess(userAccessDto);
    }

    /**
     * 모든 유저접근 데이터 조회
     *
     * @return 모든 유저접근 데이터 (id값 제외)
     */
    @GetMapping("/all")
    public List<UserAccessDto> findAllUserAccess() {
        return userAccessService.findAllUserAccessDto();
    }

    /**
     * 모든 유저접근 데이터 중 특정 필드만 조회
     * Long 타입의 필드만 조회 가능
     *
     * @param fieldName 특정 필드 이름
     * @return 특정 필드 데이터 모음, 특정 필드 이름
     */
    @GetMapping("/all/{fieldName}")
    public ChartDataDto findAllSpecificField(@PathVariable String fieldName) {
        return userAccessService.findAllSpecificFieldAndFieldName(fieldName);
    }

    /**
     * id로 유저 접근 데이터 조회
     *
     * @param id 조회할 유저 접근 데이터 id
     * @return 조회할 유저 접근 데이터
     */
    @GetMapping("/{id}")
    public UserAccessDto findUserAccessById(@PathVariable String id) {
        return userAccessService.findUserAccessById(id);
    }

    /**
     * 유저접근 데이터 수정
     *
     * @param userAccessDto 업데이트 할 유저 접근 데이터
     * @param id            수정할 유저 접근 데이터의 id
     */
    @PutMapping("/{id}")
    public void updateUserAccess(@RequestBody UserAccessDto userAccessDto, @PathVariable String id) {
        userAccessService.updateUserAccess(userAccessDto, id);
    }

    /**
     * 모든 유저접근 데이터 삭제
     *
     * @param id 삭제할 유저 접근 데이터의 id
     */
    @DeleteMapping("/{id}")
    public void deleteUserAccessById(@PathVariable String id) {
        userAccessService.deleteUserAccessById(id);
    }

    /**
     * 모든 유저 접근 데이터 조회 (id 포함)
     * 엔티티 자체를 가져오는 테스트용 메소드
     *
     * @return 모든 유저 접근 데이터
     */
    @GetMapping("/all/with-id")
    public List<UserAccess> findAllUserAccessWithId() {
        return userAccessService.findAllUserAccessWithId();
    }
}
