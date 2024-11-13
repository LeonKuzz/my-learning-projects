package org.example;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.gson.JsonArray;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;


public class Writer {

    StationIndex stationIndex;
    TreeMap <Station, List<Station>> writeConnection;
    public Writer (StationIndex stationIndex) {
        this.stationIndex = stationIndex;
        writeConnection = shortConnectionsMap(stationIndex.getAllConnections());
        System.out.println(writeConnection);

    }
    public TreeMap <Station, List<Station>> shortConnectionsMap (TreeMap <Station, List<Station>> connectionsMap) {
        TreeMap <Station, List<Station>> finalMap = new TreeMap<>();
        HashSet<Station> skipSet = new HashSet<>();
        for (Station key : connectionsMap.keySet()) {
            if (!skipSet.contains(key)) {
                List<Station> newList = connectionsMap.get(key);
                newList.add(key);
                finalMap.put(key, newList);
            }
            for (Station skipStation : connectionsMap.get(key)) {
                skipSet.add(skipStation);
            }
        }
        return finalMap;
    }
    public void writeStationsInfo () throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("CustomStationSerializer",
                new Version(1, 0, 0, null, null, null));
        module.addSerializer(Station.class, new StationSerializer());
        mapper.registerModule(module);
        PrintWriter writer1 = new PrintWriter("result/stations.json");
        writer1.write("{\n" +
                "  \"stations\": [\n");
        int i = 1;
        int ii = 0;
        for (String lineName : stationIndex.getNumber2line().keySet()) {
            Line line = stationIndex.getLine(lineName);
            ii = ii + line.stationList.size();
        }
        for (String lineName : stationIndex.getNumber2line().keySet()) {
            Line line = stationIndex.getLine(lineName);
            for (Station station : line.stationList) {
                if (i != ii) {
                    ObjectMapper mapper1 = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
                    Object jsonObject = mapper1.readValue(mapper.writeValueAsString(station), Object.class);
                    String prettyJson = mapper1.writeValueAsString(jsonObject);
                    writer1.write(prettyJson + ",\n");
                    i++;
                } else {
                    ObjectMapper mapper1 = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
                    Object jsonObject = mapper1.readValue(mapper.writeValueAsString(station), Object.class);
                    String prettyJson = mapper1.writeValueAsString(jsonObject);
                    writer1.write(prettyJson + "\n" + "]\n" + "}");
                }
            }
        }
        writer1.flush();
        writer1.close();
    }

    public void writeLinesMap (TreeMap <Station, List<Station>> map) throws IOException {
        PrintWriter writer = new PrintWriter("result/lines.json");
        writer.write("{\n" + "  \"stations\": {\n");
        int i = 0;
        int countLines = stationIndex.getNumber2line().size();
        for (String lineName : stationIndex.getNumber2line().keySet()) {
            i++;
            writer.write("      \"" + lineName + "\": [\n");
            Line line = stationIndex.getLine(lineName);
            int ii = 0;
            int countStations = line.stationList.size();
            for (Station station : line.stationList) {
                ii++;
                if (ii != countStations) {
                    writer.write("          \"" + station.getName() + "\",\n");
                } else {
                    writer.write("          \"" + station.getName() + "\"\n");
                }
            }
            if (i != countLines) {
                writer.write("      ],\n");
            } else {
                writer.write("      ]\n");
            }
        }
        writer.write("  },\n");
        writer.write("  \"connections\": [\n");
        int a = 0;
        int countConnections = map.size();
        for (Station station : map.keySet()) {
            a++;
            int aa = 0;
            int countStation = map.get(station).size();
            writer.write("      [\n");
            for (Station station1 : map.get(station)) {
                aa++;
                writer.write("          {\n" +
                        "               \"line\": \"" + station1.getNumberLine() + "\", \"station\": \""
                        + station1.getName());
                if (aa != countStation){
                    writer.write("\"           },\n");
                } else {
                    writer.write("\"           }\n");
                }
            }
            if (a != countConnections) {
                writer.write("      ],\n");
            } else {
                writer.write("      ]\n");
            }
        }
        writer.write("  ]");
        writer.write("\n}");
        writer.close();
    }
}
