package org.example;

import java.util.*;

import static org.example.Main.*;

public class StationIndex {
    public static Map<String, Line> number2line;
    private final TreeSet<Station> stations;
    public static TreeMap <Station, List<Station>> connections;

    public StationIndex() {
        number2line = new TreeMap<>();
        stations = new TreeSet<>();
        connections = new TreeMap<>();
    }
    public void addLine (String number, String name){
        Line line = new Line(number, name);
        number2line.put(number, line);
    }
    public void addStation (Station station){
        stations.add(station);
    }
    public Line getLine(String number){
        return number2line.get(number);
    }
    public Map<String, Line> getNumber2line(){
        return number2line;
    }
    public void addConnection(Station keyStation) {
        connections.put(keyStation, new ArrayList<>());
    }
    public List<Station> getConnection (Station station) {
        return connections.get(station);
    }

    public TreeMap<Station, List<Station>> getAllConnections() {
        return connections;
    }

    public void addDateToStations() {
        for (Station station : stations) {
            String stationName = station.getName().toLowerCase().replaceAll("ё", "е");
            ArrayList<String> datesSet = dateMap.get(stationName);
            if (datesSet == null) {
                continue;
            } else if (datesSet.size() == 1) {
                station.setDate(datesSet.get(0));
            } else if (datesSet.size() > 1) {
                System.out.println(stationName + " - " + station.getNumberLine() +
                        System.lineSeparator() +
                        "Выбериет значение :" + datesSet);
                for (; datesSet.size() > 1; ) {
                    String date = scanner.nextLine();
                    if (dateMap.get(stationName).contains(date)) {
                        station.setDate(date);
                        datesSet.remove(date);
                        break;
                    } else {
                        System.out.println("неверное значение");
                    }
                }
            }
        }
    }
    public void  addDepthToStations(){
        for (Station station : stations) {
            String stationName = station.getName().toLowerCase().replaceAll("ё", "е");
            ArrayList<String> depthSet = depthMap.get(stationName);
            if (depthSet == null) {
                continue;
            } else if (depthSet.size() == 1) {
                station.setDepth(depthSet.get(0));
            } else if (depthSet.size() > 1) {
                System.out.println(stationName + " - " + station.getNumberLine() +
                        System.lineSeparator() +
                        "Выбериет значение :" + depthSet);
                for (;depthSet.size() > 1 ; ) {
                    String depth = scanner.nextLine();
                    if (depthMap.get(stationName).contains(depth)) {
                        station.setDepth(depth);
                        break;
                    } else {
                        System.out.println("неверное значение");
                    }
                }
            }
        }
    }
    public void addRandomDepth(){
        for (String lineName : number2line.keySet()) {
            for (Station station : number2line.get(lineName).stationList){
                station.addDepth();
            }
        }
    }
    public void addRandomDate (){
        for (String lineName : number2line.keySet()) {
            for (Station station : number2line.get(lineName).stationList){
                station.addDate();
            }
        }
    }
}
