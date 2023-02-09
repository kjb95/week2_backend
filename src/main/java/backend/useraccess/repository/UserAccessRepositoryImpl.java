package backend.useraccess.repository;

import backend.useraccess.entity.UserAccess;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//@Repository
public class UserAccessRepositoryImpl implements UserAccessRepository {

    private static final String DUMMY_PATH = "./static/dummy.json";
    private static final String DUMMY_JSON_KEY = "data";
    private static Map<Long, UserAccess> store;
    private static long sequence = 0L;

    static {
        insertDummy();
    }

    private static void insertDummy() {
        try {
            String dummyPath = computeDummyPath();
            JSONArray jsonArray = computeJSONArray(dummyPath);
            store = insertDummy(jsonArray);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static String computeDummyPath() throws IOException {
        ClassPathResource resource = new ClassPathResource(DUMMY_PATH);
        Path path = Paths.get(resource.getURI());
        return path.toString();
    }

    private static JSONArray computeJSONArray(String dummyPath) throws IOException, ParseException {
        Reader reader = new FileReader(dummyPath);
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        return (JSONArray) jsonObject.get(DUMMY_JSON_KEY);
    }

    private static UserAccess crateUserAccess(JSONObject jsonObject) {
        return UserAccess.builder()
                .id(++sequence)
                .basicDate((String) jsonObject.get("basicDate"))
                .impCnt((Long) jsonObject.get("impCnt"))
                .clickCnt((Long) jsonObject.get("clickCnt"))
                .convCnt((Long) jsonObject.get("convCnt"))
                .sellCost((Long) jsonObject.get("sellCost"))
                .adspend((Long) jsonObject.get("adspend"))
                .build();
    }

    private static Map<Long, UserAccess> insertDummy(JSONArray jsonArray) throws IOException, ParseException {
        return IntStream.range(0, jsonArray.size())
                .mapToObj(i -> (JSONObject) jsonArray.get(i))
                .map(UserAccessRepositoryImpl::crateUserAccess)
                .collect(Collectors.toMap(UserAccess::getId, userAccess -> userAccess));
    }

    @Override
    public UserAccess save(UserAccess userAccess) {
        if (userAccess.getId() != null) {
            return userAccess;
        }
        userAccess.setId(++sequence);
        store.put(userAccess.getId(), userAccess);
        return userAccess;
    }

    @Override
    public Optional<UserAccess> findById(Long id) {
        UserAccess userAccess = store.get(id);
        return Optional.ofNullable(userAccess);
    }

    @Override
    public List<UserAccess> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void delete(UserAccess userAccess) {
        store.remove(userAccess.getId());
    }
}
