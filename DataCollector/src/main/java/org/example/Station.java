package org.example;

import java.util.ArrayList;

import static org.example.Main.dateMap;
import static org.example.Main.depthMap;

public class Station implements Comparable <Station> {
    String name;
    String numberLine;
    String depth;
    String date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberLine() {
        return numberLine;
    }

    public void setNumberLine(String numberLine) {
        this.numberLine = numberLine;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Station(String name, String numberLine) {
        this.name = name;
        this.numberLine = numberLine;
    }
    public String toString(){

        return name ;
    }
    public String toJSON(){

        return "";
    }
    public void addDepth () throws NullPointerException {
        String stationName = name.toLowerCase().replaceAll("ё", "е");
        ArrayList<String> depthSet = depthMap.get(stationName);
        if (depthSet == null) {
            ;
        } else if (depthSet.size() == 1) {
            setDepth(depthSet.get(0));
        } else if (depthSet.size() > 1) {
//                System.out.println(stationName + " - " + station.getNumberLine() +
//                        System.lineSeparator() +
//                        "Выбериет значение :" + depthSet);
//                for (;depthSet.size() > 1 ; ) {
//                    String depth = scanner.nextLine();
//                    if (depthMap.get(stationName).contains(depth)) {
//                        station.setDepth(depth);
//                        break;
//                    } else {
//                        System.out.println("неверное значение");
//                    }
//                }
            int r = (int) (Math.random() * depthSet.size());
            setDepth(depthSet.get(r));
        }
    }
    public void addDate () {
        String stationName = name.toLowerCase().replaceAll("ё", "е");
        ArrayList<String> datesSet = dateMap.get(stationName);
        if (datesSet == null) {
            ;
        } else if (datesSet.size() == 1) {
            setDate(datesSet.get(0));
        } else if (datesSet.size() > 1) {
//                System.out.println(stationName + " - " + station.getNumberLine() +
//                        System.lineSeparator() +
//                        "Выбериет значение :" + datesSet);
//                for (;datesSet.size() > 1 ; ) {
//                    String date = scanner.nextLine();
//                    if (dateMap.get(stationName).contains(date)) {
//                        station.setDate(date);
//                        datesSet.remove(date);
//                        break;
//                    } else {
//                        System.out.println("неверное значение");
            int r = (int) (Math.random() * datesSet.size());
            setDate(datesSet.get(r));
        }
    }


    @Override
    public int compareTo(Station o) {
        if (!this.getName().equals(o.getName())){
            return this.getName().compareTo(o.getName());
        } else {
            return this.getNumberLine().compareTo(o.getNumberLine());
        }
    }
}
