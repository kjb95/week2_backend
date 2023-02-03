package com.example.week2_backend.service;

import com.example.week2_backend.dto.ChartDataDto;
import com.example.week2_backend.dto.UserAccessDto;
import com.example.week2_backend.entity.UserAccess;
import com.example.week2_backend.enums.ChartDataDictionary;
import com.example.week2_backend.repository.UserAccessRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAccessService {

    private final UserAccessRepository userAccessRepository;

    /**
     * 유저접근 데이터 생성
     *
     * @param userAccessDto 유저접근 데이터
     */
    public void createUserAccess(UserAccessDto userAccessDto) {
        ModelMapper modelMapper = new ModelMapper();
        UserAccess userAccess = modelMapper.map(userAccessDto, UserAccess.class);
        userAccessRepository.save(userAccess);
    }

    /**
     * 모든 유저접근 데이터 조회
     *
     * @return 모든 유저접근 데이터 (id값 제외)
     */
    public List<UserAccessDto> findAllUserAccessDto() {
        ModelMapper modelMapper = new ModelMapper();
        return userAccessRepository.findAll()
                                   .stream()
                                   .map(userAccess -> modelMapper.map(userAccess, UserAccessDto.class))
                                   .collect(Collectors.toList());
    }

    /**
     * 모든 유저접근 데이터 중 특정 필드만 조회
     * Long 타입의 필드만 조회 가능
     *
     * @param fieldName 특정 필드 이름
     * @return 특정 필드 데이터 모음, 특정 필드 이름
     */
    public ChartDataDto findAllSpecificFieldAndFieldName(String fieldName) {
        List<Long> specificFields = findAllSpecificField(fieldName);
        return createChartDataDto(fieldName, specificFields);
    }

    private List<Long> findAllSpecificField(String fieldName) {
        return userAccessRepository.findAll()
                                   .stream()
                                   .map(userAccess -> getFieldByFieldName(fieldName, userAccess))
                                   .collect(Collectors.toList());
    }

    private Long getFieldByFieldName(String fieldName, UserAccess userAccess) {
        if (fieldName.equals("impCnt")) {
            return userAccess.getImpCnt();
        }
        if (fieldName.equals("clickCnt")) {
            return userAccess.getClickCnt();
        }
        if (fieldName.equals("convCnt")) {
            return userAccess.getConvCnt();
        }
        if (fieldName.equals("sellCost")) {
            return userAccess.getSellCost();
        }
        if (fieldName.equals("adspend")) {
            return userAccess.getAdspend();
        }
        return null;
    }

    private ChartDataDto createChartDataDto(String fieldName, List<Long> specificFields) {
        return ChartDataDto.builder()
                           .name(ChartDataDictionary.findKoreanByEnglish(fieldName))
                           .data(specificFields)
                           .build();
    }

    /**
     * id로 유저 접근 데이터 조회
     *
     * @param id 조회할 유저 접근 데이터 id
     * @return 조회할 유저 접근 데이터
     */
    public UserAccessDto findUserAccessById(String id) {
        UserAccess userAccess = userAccessRepository.findById(Long.parseLong(id));
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userAccess, UserAccessDto.class);
    }

    /**
     * 유저접근 데이터 수정
     *
     * @param userAccessDto 업데이트 할 유저 접근 데이터
     * @param id            수정할 유저 접근 데이터의 id
     */
    public void updateUserAccess(UserAccessDto userAccessDto, String id) {
        UserAccess userAccess = userAccessRepository.findById(Long.parseLong(id));
        userAccess.update(userAccessDto);
        userAccessRepository.save(userAccess);
    }

    /**
     * 모든 유저접근 데이터 삭제
     *
     * @param id 삭제할 유저 접근 데이터의 id
     */
    public void deleteUserAccessById(String id) {
        UserAccess userAccess = userAccessRepository.findById(Long.parseLong(id));
        userAccessRepository.delete(userAccess);
    }

    /**
     * 모든 유저 접근 데이터 조회 (id 포함)
     * 엔티티 자체를 가져오는 테스트용 메소드
     *
     * @return 모든 유저 접근 데이터
     */
    public List<UserAccess> findAllUserAccessWithId() {
        return userAccessRepository.findAll();
    }

}
