import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.sql.SQLException;

public class XMLHandler extends DefaultHandler {

    private String addition;
    private StringBuilder votersBuilder = new StringBuilder
            ("INSERT INTO voter_count (name, birthDate) VALUES ");
    private StringBuilder votersBuilder2 = new StringBuilder
            ("INSERT INTO voter_count (name, birthDate, `count`) VALUES ");
    private StringBuilder stationsBuilder = new StringBuilder
            ("INSERT INTO voting_station (`number`, `date`, `time`) VALUES ");
    XMLHandler (String addition) {
        this.addition = addition;
    }
    int i = 0;
    int ii = 0;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("voter")){
            String name = attributes.getValue("name");
            String birthDay = attributes.getValue("birthDay");
            birthDay = birthDay.replace('.', '-');
            addVoterToInsert(name, birthDay);
        } else if (qName.equals("visit")) {
            String station = attributes.getValue("station");
            String[] dateTime = attributes.getValue("time").replace('.', '-').split(" ");
            stationsBuilder.append("(");//("(" + station + ", '" + dateTime[0] + "', '" + dateTime[1] + "'), ");
            stationsBuilder.append(station);
            stationsBuilder.append(", '");
            stationsBuilder.append(dateTime[0]);
            stationsBuilder.append("', '");
            stationsBuilder.append(dateTime[1]);
            stationsBuilder.append("'), ");
            ii++;
            if (ii == 1250000) {
                String sql = stationsBuilder.substring(0, stationsBuilder.length() - 2) + ";";
                try {
                    DBConnection.getConnection().createStatement().execute(sql);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                stationsBuilder = new StringBuilder("INSERT INTO voting_station (`number`, `date`, `time`) VALUES ");
                ii = 0;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("voters")) {
            String sqlStations = stationsBuilder.substring(0, stationsBuilder.length() - 2) + ";";
            if(addition.isEmpty()) {
                String sql = votersBuilder.substring(0, votersBuilder.length() - 2) + ";";
                try {
                    DBConnection.getConnection().createStatement().execute(sql);
                    DBConnection.getConnection().createStatement().execute(sqlStations);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (addition.equals(" ON DUPLICATE KEY UPDATE `count`=`count` + 1;")) {
                String sql = votersBuilder2.substring(0, votersBuilder2.length() - 2) +
                        " ON DUPLICATE KEY UPDATE `count`=`count` + 1;";
                try {
                    DBConnection.getConnection2().createStatement().execute(sql);
                    DBConnection.getConnection2().createStatement().execute(sqlStations);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void addVoterToInsert(String name, String birthDay) {
        if (addition.isEmpty()) {
            votersBuilder.append("('");//("('" + name + "', '" + birthDay + "'), ");
            votersBuilder.append(name);
            votersBuilder.append("', '");
            votersBuilder.append(birthDay);
            votersBuilder.append("'), ");
            i++;
            if (i == 1250000) {
                String sql = votersBuilder.substring(0, votersBuilder.length() - 2) + ";";
                try {
                    DBConnection.getConnection().createStatement().execute(sql);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                votersBuilder = new StringBuilder("INSERT INTO voter_count (name, birthDate) VALUES ");
                i = 0;
            }
        }
        if (addition.equals(" ON DUPLICATE KEY UPDATE `count`=`count` + 1;")) {
            votersBuilder2.append("('");//("('" + name + "', '" + birthDay + "', 1), ");
            votersBuilder2.append(name);
            votersBuilder2.append("', '");
            votersBuilder2.append(birthDay);
            votersBuilder2.append("', 1), ");
            i++;
            if (i == 1250000) {
                String sql = votersBuilder2.substring(0, votersBuilder2.length() - 2) +
                        " ON DUPLICATE KEY UPDATE `count`=`count` + 1;";
                try {
                    DBConnection.getConnection2().createStatement().execute(sql);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                votersBuilder2 = new StringBuilder("INSERT INTO voter_count (name, birthDate, `count`) VALUES ");
                i = 0;
            }
        }
    }
}
