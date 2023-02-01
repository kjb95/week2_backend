package com.example.week2_backend.repository.impl;

import com.example.week2_backend.entity.UserAccess;
import com.example.week2_backend.repository.UserAccessRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UserAccessRepositoryImpl implements UserAccessRepository {
    private static Map<Long, UserAccess> store;
    private static long sequence = 0L;
    private static final String dummyDataStaticPath = "C:\\Users\\dev\\Desktop\\week2\\week2_backend\\src\\main\\resources\\static\\dummy.json";

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

    private static void initDummyData() {
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader(dummyDataStaticPath)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray jsonArray = (JSONArray) jsonObject.get("data");
            store = IntStream.range(0, jsonArray.size())
                             .mapToObj(i -> (JSONObject) jsonArray.get(i))
                             .map(UserAccessRepositoryImpl::crateUserAccess)
                             .collect(Collectors.toMap(UserAccess::getId, userAccess -> userAccess));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    static {
        initDummyData();
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
