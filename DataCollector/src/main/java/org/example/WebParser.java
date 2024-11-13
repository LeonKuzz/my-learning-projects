package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WebParser {

    private final StationIndex stationIndex;
    public WebParser(String url, StationIndex stationIndex) throws IOException {
        this.stationIndex = stationIndex;
        Document page = getCode(url);
        parseWeb(page);
    }

    public Document getCode (String url) throws IOException {
//        Document page = (Jsoup) Jsoup.connect(url);
        Document page = Jsoup.connect(url).get();
        return page;
    }
    public void parseWeb (Document page){
        parseLines(page);
        parseStations(page);
        }

    private void parseLines(Document page){
        Elements lines = page.select(".js-toggle-depend > .js-metro-line");
        for (Element line : lines){
            String text = line.toString();
            String info = text.substring(text.lastIndexOf("="), text.lastIndexOf("<")).replaceAll("=", "");
            info = info.replaceAll("\"", "");
            String[] lineInfo = info.split(">");
            stationIndex.addLine(lineInfo[0], lineInfo[1]);
        }
    }
    private void parseStations(Document page){
        Elements stationsLists = page.select(".t-metrostation-list-table");
        for (Element line : stationsLists){
            String numberLine = String.valueOf(line.attr("data-line"));
            Elements stations = line.select(".single-station");
            for (Element station : stations){
                String numberStation2Line = String.valueOf(station.select(".num"));
                String nameStation = station.select(".name").text();
                numberStation2Line = numberStation2Line.replaceAll("[^0-9]+", "");
                Station newStation = new Station(nameStation, numberLine);
                stationIndex.getLine(numberLine).addStation(Integer.parseInt(numberStation2Line) - 1, newStation);
                stationIndex.addStation(newStation);
            }
        }
    }
    public   void parseConnections(Document page){
        Elements stationsLists = page.select(".t-metrostation-list-table");
        for (Element line : stationsLists){
            String numberLine = String.valueOf(line.attr("data-line"));
            Elements stations = line.select(".single-station");
            for (Element station : stations){
                String nameStation = station.select(".name").text();
                String numberStation2Line = String.valueOf(station.select(".num"));
                numberStation2Line = numberStation2Line.replaceAll("[^0-9]+", "");
                Station mainStation = stationIndex.getLine(numberLine).getStation(nameStation);
                Elements connectionStations = station.select("[title]");
                if (!connectionStations.toString().isEmpty()){
                    stationIndex.addConnection(mainStation);
                }
                for (Element connectionStation : connectionStations) {
                    String text = connectionStation.toString();
                    int start = text.indexOf("ln-") + 3;
                    String numberLineConnectionStation = text.substring(start, text.indexOf(("\""), start));
                    String nameConnectionStation = text.substring(text.indexOf("«") + 1, text.lastIndexOf("»"));
                    stationIndex.getConnection(mainStation).
                            add(stationIndex.getLine(numberLineConnectionStation).getStation(nameConnectionStation));
                }
            }
        }
    }
}
