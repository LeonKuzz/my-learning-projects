import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

public class Loader {
    private static String fileName = "res/data-18M.xml";

    public static void main(String[] args) throws Exception {

        performWithGrouping();
        performWithUniqueKey();
    }

    private static void performWithGrouping () {
        long start = System.currentTimeMillis();
        Connection connection = DBConnection.getConnection();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLHandler handler = new XMLHandler("");
            parser.parse(new File(fileName), handler);

            //Getting and printing duplicated voters

            System.out.println("Duplicated voters: ");

            String sqlVoters = "SELECT `name`, `birthDate`, COUNT(*) as count\n" +
                    "FROM learn.voter_count \n" +
                    "group by `name`, `birthDate`\n" +
                    "having `count` > 1;";

            ResultSet resultSetVoters = connection.createStatement().executeQuery(sqlVoters);
            while (resultSetVoters.next()) {
                System.out.println("\t" + resultSetVoters.getString(1) + " ("
                        + resultSetVoters.getString(2) + ") - " + resultSetVoters.getString(3));
            }
            resultSetVoters.close();

            //Getting and printing stations info

            System.out.println("Voting station work times: ");

            String sqlStations = "SELECT number, date, MIN(time) as 'start_of_work', MAX(time) as 'end_of_work' \n" +
                    "FROM learn.voting_station\n" +
                    "group by number, date\n" +
                    "order by number;";
            ResultSet resultSetStations = connection.createStatement().executeQuery(sqlStations);
            printStationsInfo(resultSetStations);
            resultSetStations.close();
            connection.close();
            long f = System.currentTimeMillis();
            System.out.println(f - start);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void performWithUniqueKey () {
        long start = System.currentTimeMillis();
        Connection connection1 = DBConnection.getConnection2();
        String addition = " ON DUPLICATE KEY UPDATE `count`=`count` + 1;";
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLHandler handler = new XMLHandler(addition);
            parser.parse(new File(fileName), handler);

            //Getting and printing duplicated voters

            System.out.println("Duplicated voters: ");
            String sqlVoters = "SELECT `name`, `birthDate`, `count`" +
                    "FROM learn_two.voter_count \n" +
                    "WHERE `count` > 1;";
            ResultSet resultSetVoters = connection1.createStatement().executeQuery(sqlVoters);
            while (resultSetVoters.next()) {
                System.out.println("\t" + resultSetVoters.getString(1) + " ("
                        + resultSetVoters.getString(2) + ") - " + resultSetVoters.getString(3));
            }
            resultSetVoters.close();

            //Getting and printing stations info

            System.out.println("Voting station work times: ");

            String sqlStations = "SELECT number, date, MIN(time) as 'start_of_work', MAX(time) as 'end_of_work' \n" +
                    "FROM learn_two.voting_station\n" +
                    "group by number, date\n" +
                    "order by number;";
            ResultSet resultSetStations = connection1.createStatement().executeQuery(sqlStations);
            printStationsInfo(resultSetStations);
            resultSetStations.close();
            connection1.close();
            long f = System.currentTimeMillis();
            System.out.println(f - start);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void printStationsInfo(ResultSet resultSetStations) throws SQLException {
        while (resultSetStations.next()) {
            String number = resultSetStations.getString(1);
            StringBuilder builder = new StringBuilder("\t" + number + " - ");
            builder.append(resultSetStations.getString(2) + " ");
            builder.append(resultSetStations.getString(3).substring(0,5) +
                    "-" + resultSetStations.getString(4).substring(0,5) + ", ");
            resultSetStations.next();
            builder.append(resultSetStations.getString(2) + " ");
            builder.append(resultSetStations.getString(3).substring(0,5) +
                    "-" + resultSetStations.getString(4).substring(0,5) + ", ");
            resultSetStations.next();
            builder.append(resultSetStations.getString(2) + " ");
            builder.append(resultSetStations.getString(3).substring(0,5) +
                    "-" + resultSetStations.getString(4).substring(0,5));
            System.out.println(builder);
        }
    }
}