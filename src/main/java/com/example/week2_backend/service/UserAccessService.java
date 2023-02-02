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

    public List<UserAccessDto> findAllUserAccess() {
        ModelMapper modelMapper = new ModelMapper();
        return userAccessRepository.findAll()
                .stream()
                .map(userAccess -> modelMapper.map(userAccess, UserAccessDto.class))
                .collect(Collectors.toList());
    }

    public ChartDataDto findAllSpecificField(String fieldName) {
        List<Long> specificFields = findAllLongTypeSpecificFields(fieldName);
        return createChartDataDto(fieldName, specificFields);
    }

    private List<Long> findAllLongTypeSpecificFields(String fieldName) {
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
        return null;
    }

    private ChartDataDto createChartDataDto(String fieldName, List<Long> specificFields) {
        return ChartDataDto.builder()
                .name(ChartDataDictionary.findKoreanByEnglish(fieldName))
                .data(specificFields)
                .build();
    }
}
