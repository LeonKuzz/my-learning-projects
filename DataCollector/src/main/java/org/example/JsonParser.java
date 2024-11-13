package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.example.Main.depthMap;

public class JsonParser {

    JsonParser (FileSearch fileSearch) throws ParseException, IOException {
        List<File> list = fileSearch.ListJSON;
        for (File json : list) {
            parseDepth(json);
        }
    }
    public void parseDepth(File file) throws ParseException, IOException {
        JSONParser parser = new JSONParser();
        JSONArray depthsArray = (JSONArray) parser.parse(getJsonFile(file));
        for (Object depth : depthsArray) {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = depth.toString();
            Depth depth1 = objectMapper.readValue(jsonString, Depth.class);
            String depthKey = depth1.getStation_name().toLowerCase().replaceAll("ё", "е");
            if (!depthMap.containsKey(depthKey)) {
                ArrayList<String> depthSet = new ArrayList<>();
                depthSet.add(depth1.getDepth());
                depthMap.put(depthKey, depthSet);
            }
            if (depthMap.containsKey(depthKey)) {
                if (!depthMap.get(depthKey).contains(depth1.getDepth())) {
                    depthMap.get(depthKey).add(depth1.getDepth());
                }
            }
        }
    }
    private static String getJsonFile(File file) {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Path.of(file.getAbsolutePath()));
            lines.forEach(line -> builder.append(line));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return builder.toString();
    }
}
