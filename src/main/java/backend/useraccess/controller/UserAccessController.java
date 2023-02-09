package backend.useraccess.controller;

import backend.useraccess.dto.ChartDataDto;
import backend.useraccess.dto.CreateUserAccessRequestDto;
import backend.useraccess.dto.UserAccessResponseDto;
import backend.useraccess.entity.UserAccess;
import backend.useraccess.service.UserAccessService;
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
     *
     * @param createUserAccessRequestDto 추가할 유저 접근 데이터
     */
    @PostMapping()
    public ResponseEntity<Void> createUserAccess(@RequestBody @Validated CreateUserAccessRequestDto createUserAccessRequestDto) {
        userAccessService.createUserAccess(createUserAccessRequestDto);
        return ResponseEntity.ok()
                .build();
    }

    /**
     * 모든 유저접근 데이터 조회
     *
     * @return 모든 유저접근 데이터 (id값 제외)
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
     *
     * @param fieldName 특정 필드 이름
     * @return 특정 필드 데이터 모음, 특정 필드 이름
     */
    @GetMapping("/all/{fieldName}")
    public ResponseEntity<ChartDataDto> findAllSpecificField(@PathVariable String fieldName) {
        ChartDataDto allSpecificField = userAccessService.findAllSpecificFieldAndFieldName(fieldName);
        return ResponseEntity.ok()
                .body(allSpecificField);
    }

    /**
     * id로 유저 접근 데이터 조회
     *
     * @param id 조회할 유저 접근 데이터 id
     * @return 조회할 유저 접근 데이터
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserAccessResponseDto> findUserAccessById(@PathVariable String id) {
        UserAccessResponseDto userAccessResponseDto = userAccessService.findUserAccessById(id);
        return ResponseEntity.ok()
                .body(userAccessResponseDto);
    }

    /**
     * 유저접근 데이터 수정
     *
     * @param userAccessResponseDto 업데이트 할 유저 접근 데이터
     * @param id                    수정할 유저 접근 데이터의 id
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUserAccess(@RequestBody @Validated UserAccessResponseDto userAccessResponseDto, @PathVariable String id) {
        userAccessService.updateUserAccess(userAccessResponseDto, id);
        return ResponseEntity.ok()
                .build();
    }

    /**
     * 모든 유저접근 데이터 삭제
     *
     * @param id 삭제할 유저 접근 데이터의 id
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
     *
     * @return 모든 유저 접근 데이터
     */
    @GetMapping("/all/with-id")
    public List<UserAccess> findAllUserAccessWithId() {
        return userAccessService.findAllUserAccessWithId();
    }
}
