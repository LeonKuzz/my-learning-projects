package org.example;

import java.util.ArrayList;
import java.util.List;
import static org.example.StationIndex.*;

public class Line {
    String name;
    String number;
    List<Station> stationList;

    public Line (String number, String name){
        this.name = name;
        this.number = number;
        stationList = new ArrayList<>();
    }
    public void addStation(int num, Station station){
        stationList.add(num, station);
    }

    public String getName() {
        return name;
    }
    public Station getStation (String name) {
        for (Station station : stationList) {
            if (station.getName().equals(name)) {
                return station;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder(number + " - " + name + System.lineSeparator());
        for (int i = 0; i < stationList.size(); i++){
            Station station = stationList.get(i);
            text.append("   " + (i + 1) + " " + station.toString() + System.lineSeparator());
        }
        return String.valueOf(text);
    }
}
