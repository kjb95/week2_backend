package com.example.week2_backend.repository.impl;

import com.example.week2_backend.constant.Constant;
import com.example.week2_backend.entity.UserAccess;
import com.example.week2_backend.repository.UserAccessRepository;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

@Repository
public class UserAccessRepositoryImpl implements UserAccessRepository {

    private static Map<Long, UserAccess> store;
    private static long sequence = 0L;

    static {
        initDummy();
    }

    private static void initDummy() {
        try {
            String dummyPath = computeDummyPath();
            JSONArray jsonArray = computeJSONArray(dummyPath);
            store = initDummy(jsonArray);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static String computeDummyPath() throws IOException {
        ClassPathResource resource = new ClassPathResource(Constant.DUMMY_PATH);
        Path path = Paths.get(resource.getURI());
        return path.toString();
    }

    private static JSONArray computeJSONArray(String dummyPath) throws IOException, ParseException {
        Reader reader = new FileReader(dummyPath);
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        return (JSONArray) jsonObject.get(Constant.DUMMY_JSON_KEY);
    }

    private static UserAccess crateUserAccess(JSONObject jsonObject) {
        return UserAccess.builder()
                .id(++sequence)
                .basicDate((String) jsonObject.get("basicDate"))
                .impCnt((Long) jsonObject.get("impCnt"))
                .clickCnt((Long) jsonObject.get("clickCnt"))
                .sellCost((Long) jsonObject.get("sellCost"))
                .adspend((Long) jsonObject.get("adspend"))
                .build();
    }

    private static Map<Long, UserAccess> initDummy(JSONArray jsonArray) throws IOException, ParseException {
        return IntStream.range(0, jsonArray.size())
                .mapToObj(i -> (JSONObject) jsonArray.get(i))
                .map(UserAccessRepositoryImpl::crateUserAccess)
                .collect(Collectors.toMap(UserAccess::getId, userAccess -> userAccess));
    }

    @Override
    public UserAccess save(UserAccess userAccess) {
        userAccess.setId(++sequence);
        store.put(userAccess.getId(), userAccess);
        return userAccess;
    }

    @Override
    public UserAccess findById(Long id) {
        return store.get(id);
    }

    @Override
    public List<UserAccess> findAll() {
        return new ArrayList<>(store.values());
    }
}
