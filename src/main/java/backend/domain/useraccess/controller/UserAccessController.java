package backend.domain.useraccess.controller;

import backend.domain.useraccess.dto.UserAccessResponseDto;
import backend.domain.useraccess.entity.UserAccess;
import backend.domain.useraccess.service.UserAccessService;
import backend.domain.useraccess.dto.ChartDataDto;
import backend.domain.useraccess.dto.CreateUserAccessRequestDto;
import backend.domain.useraccess.dto.UpdateUserAccessRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user-access")
@Slf4j
public class UserAccessController {

    private final UserAccessService userAccessService;

    /**
     * 유저 접근 데이터 추가
     */
    @PostMapping()
    public ResponseEntity<UserAccessResponseDto> createUserAccess(@RequestBody @Validated CreateUserAccessRequestDto createUserAccessRequestDto) {
        UserAccessResponseDto userAccessResponseDto = userAccessService.createUserAccess(createUserAccessRequestDto);
        return ResponseEntity.ok()
                .body(userAccessResponseDto);
    }

    /**
     * 모든 유저접근 데이터 조회
     */
    @GetMapping("/all")
    public ResponseEntity<List<UserAccessResponseDto>> findAllUserAccess() {
        List<UserAccessResponseDto> allUserAccessResponseDto = userAccessService.findAllUserAccessDto();
        return ResponseEntity.ok()
                .body(allUserAccessResponseDto);
    }

    /**
     * 모든 유저접근 데이터 중 특정 필드만 조회
     * Long 타입의 필드만 조회 가능
     */
    @GetMapping("/all/{fieldName}")
    public ResponseEntity<ChartDataDto> findAllSpecificField(@PathVariable String fieldName) {
        ChartDataDto allSpecificField = userAccessService.findAllSpecificFieldAndFieldName(fieldName);
        return ResponseEntity.ok()
                .body(allSpecificField);
    }

    /**
     * id로 유저 접근 데이터 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserAccessResponseDto> findUserAccessById(@PathVariable String id) {
        UserAccessResponseDto userAccessResponseDto = userAccessService.findUserAccessById(id);
        return ResponseEntity.ok()
                .body(userAccessResponseDto);
    }

    /**
     * 유저접근 데이터 수정
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUserAccess(@RequestBody @Validated UpdateUserAccessRequestDto updateUserAccessRequestDto, @PathVariable String id) {
        userAccessService.updateUserAccess(updateUserAccessRequestDto, id);
        return ResponseEntity.ok()
                .build();
    }

    /**
     * 모든 유저접근 데이터 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserAccessById(@PathVariable String id) {
        userAccessService.deleteUserAccessById(id);
        return ResponseEntity.ok()
                .build();
    }

    /**
     * 모든 유저 접근 데이터 조회 (id 포함)
     * 엔티티 자체를 가져오는 테스트용 메소드
     */
    @GetMapping("/all/with-id")
    public List<UserAccess> findAllUserAccessWithId() {
        return userAccessService.findAllUserAccessWithId();
    }
}
