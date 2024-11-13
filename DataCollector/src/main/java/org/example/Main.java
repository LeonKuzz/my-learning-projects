package org.example;

import org.json.simple.parser.ParseException;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private static StationIndex stationIndex;
    static Scanner scanner;
    public static HashMap <String, ArrayList<String>> dateMap;
    public static HashMap <String, ArrayList<String>> depthMap;

    public static void main(String[] args) throws IOException, ParseException {

        stationIndex = new StationIndex();
        scanner = new Scanner(System.in);
        dateMap = new HashMap<>();
        depthMap = new HashMap<>();


        WebParser webParser = new WebParser("https://skillbox-java.github.io/", stationIndex);

        Document page = webParser.getCode("https://skillbox-java.github.io/");
        webParser.parseWeb(page);
        FileSearch fileSearch = new FileSearch("C:\\java_basics\\FilesAndNetwork\\DataCollector\\data");
        CsvParser csvParser = new CsvParser(fileSearch);
        System.out.println(dateMap);
        System.out.println(fileSearch.ListJSON);
        System.out.println(fileSearch.listCSV);
        JsonParser jsonParser = new JsonParser(fileSearch);
        System.out.println(depthMap);
//        stationIndex.addDepthToStations();  //добавить данные станций вручную
//        stationIndex.addDateToStations(); //добавить данные станций вручную

        stationIndex.addRandomDate(); //добавляет станции случайную дату из одноимённого сета
        stationIndex.addRandomDepth(); //добавляет станции случайную глубину из одноимённого сета
        webParser.parseConnections(page);
        Writer writer = new Writer(stationIndex);
        writer.writeLinesMap(writer.writeConnection);
        writer.writeStationsInfo();
    }
}
