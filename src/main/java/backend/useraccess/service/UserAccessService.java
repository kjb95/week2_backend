package backend.useraccess.service;

import backend.useraccess.dto.ChartDataDto;
import backend.useraccess.dto.CreateUserAccessRequestDto;
import backend.useraccess.dto.UserAccessResponseDto;
import backend.useraccess.entity.UserAccess;
import backend.useraccess.enums.ChartDataDictionary;
import backend.useraccess.repository.UserAccessJpaRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class UserAccessService {
    private static final String DUMMY_PATH = "./static/dummy.json";
    private static final String DUMMY_JSON_KEY = "data";
    private static final String NOT_FOUND_USER_ACCESS_ID = "존재하지 않는 유저 접근 데이터 아이디 입니다.";

    private final UserAccessJpaRepository userAccessRepository;
    //    private final UserAccessRepository userAccessRepository;

    public UserAccessService(UserAccessJpaRepository userAccessRepository) {
        this.userAccessRepository = userAccessRepository;
        insertDummy();
    }

    private void insertDummy() {
        try {
            String dummyPath = computeDummyPath();
            JSONArray jsonArray = computeJSONArray(dummyPath);
            insertDummy(jsonArray);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private String computeDummyPath() throws IOException {
        ClassPathResource resource = new ClassPathResource(DUMMY_PATH);
        Path path = Paths.get(resource.getURI());
        return path.toString();
    }

    private JSONArray computeJSONArray(String dummyPath) throws IOException, ParseException {
        Reader reader = new FileReader(dummyPath);
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        return (JSONArray) jsonObject.get(DUMMY_JSON_KEY);
    }

    private UserAccess crateUserAccess(JSONObject jsonObject) {
        return UserAccess.builder()
                .basicDate((String) jsonObject.get("basicDate"))
                .impCnt((Long) jsonObject.get("impCnt"))
                .clickCnt((Long) jsonObject.get("clickCnt"))
                .convCnt((Long) jsonObject.get("convCnt"))
                .sellCost((Long) jsonObject.get("sellCost"))
                .adspend((Long) jsonObject.get("adspend"))
                .build();
    }

    private void insertDummy(JSONArray jsonArray) throws IOException, ParseException {
        IntStream.range(0, jsonArray.size())
                .mapToObj(i -> (JSONObject) jsonArray.get(i))
                .map(this::crateUserAccess)
                .forEach(userAccess -> userAccessRepository.save(userAccess));
    }

    /**
     * 유저접근 데이터 생성
     *
     * @param createUserAccessRequestDto 유저접근 데이터
     */
    public void createUserAccess(CreateUserAccessRequestDto createUserAccessRequestDto) {
        ModelMapper modelMapper = new ModelMapper();
        UserAccess userAccess = modelMapper.map(createUserAccessRequestDto, UserAccess.class);
        userAccessRepository.save(userAccess);
    }

    /**
     * 모든 유저접근 데이터 조회
     *
     * @return 모든 유저접근 데이터 (id값 제외)
     */
    public List<UserAccessResponseDto> findAllUserAccessDto() {
        ModelMapper modelMapper = new ModelMapper();
        return userAccessRepository.findAll()
                .stream()
                .map(userAccess -> modelMapper.map(userAccess, UserAccessResponseDto.class))
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
    public UserAccessResponseDto findUserAccessById(String id) {
        UserAccess userAccess = userAccessRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_USER_ACCESS_ID));
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userAccess, UserAccessResponseDto.class);
    }

    /**
     * 유저접근 데이터 수정
     *
     * @param userAccessResponseDto 업데이트 할 유저 접근 데이터
     * @param id                    수정할 유저 접근 데이터의 id
     */
    public void updateUserAccess(UserAccessResponseDto userAccessResponseDto, String id) {
        UserAccess userAccess = userAccessRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_USER_ACCESS_ID));
        userAccess.update(userAccessResponseDto);
        userAccessRepository.save(userAccess);
    }

    /**
     * 모든 유저접근 데이터 삭제
     *
     * @param id 삭제할 유저 접근 데이터의 id
     */
    public void deleteUserAccessById(String id) {
        UserAccess userAccess = userAccessRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_USER_ACCESS_ID));
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
