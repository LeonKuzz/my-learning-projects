package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.example.Main.dateMap;


public class CsvParser {

    CsvParser (FileSearch fileSearch) throws IOException {
//        dateMap = new HashMap<>();
       List<File> list = fileSearch.listCSV;
       for (File csv : list) {
           parseCSV(csv);
       }
    }

    public void parseCSV (File fileCSV) throws IOException {
        List<String> textCSV = readCSV(fileCSV.getAbsolutePath());
        for (int i = 1; i < textCSV.size(); i++) {
            String[] dateInfo = textCSV.get(i).split(",");
            String name = dateInfo[0].toLowerCase().replaceAll("ё", "е");
            Date date = new Date(name, dateInfo[1]);
            if (!dateMap.containsKey(name)) {
               ArrayList<String> dateList = new ArrayList<>();
                dateList.add(date.getDate());
                dateMap.put(name, dateList);
            }
            if (dateMap.containsKey(name)) {
                if (!dateMap.get(name).contains(date.getDate())){
                    dateMap.get(name).add(date.getDate());
                }
            }
        }
    }

    public List<String> readCSV (String path) throws IOException {
        List<String> textCSV = Files.readAllLines(Path.of(path));
        return textCSV;
    }
}
