package org.example;

import java.util.HashSet;
import java.util.List;

public class Connection {
    Station key;
    HashSet<Station> stationHashSet;

    public Connection (Station key) {
        this.key = key;
        stationHashSet = new HashSet<>();
    }
    public void addStationToConnect(Station station) {
        stationHashSet.add(station);
    }
}
