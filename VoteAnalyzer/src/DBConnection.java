import java.sql.*;

public class DBConnection {

    private static Connection connection;

    private static String dbName = "learn";
    private static String dbUser = "root";
    private static String dbPass = "testtest";

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + dbName +
                        "?user=" + dbUser + "&password=" + dbPass);
                connection.createStatement().execute("DROP TABLE IF EXISTS voter_count");
                connection.createStatement().execute("DROP TABLE IF EXISTS voting_station");
                connection.createStatement().execute("CREATE TABLE voter_count(" +
                    "id INT NOT NULL AUTO_INCREMENT, " +
                    "name TINYTEXT NOT NULL, " +
                    "birthDate DATE NOT NULL, " +
                    "PRIMARY KEY(id), " +
                    "KEY name_date(name(50), birthDate))");
                connection.createStatement().execute("CREATE TABLE voting_station(" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "number INT NOT NULL, " +
                        "date DATE NOT NULL, " +
                        "time TIME NOT NULL, " +
                        "PRIMARY KEY(id), KEY(number));");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static Connection getConnection2() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/" + dbName + "_two" +
                                "?user=" + dbUser + "&password=" + dbPass);
                connection.createStatement().execute("DROP TABLE IF EXISTS voter_count");
                connection.createStatement().execute("DROP TABLE IF EXISTS voting_station");
                connection.createStatement().execute("CREATE TABLE voter_count(" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "name TINYTEXT NOT NULL, " +
                        "birthDate DATE NOT NULL, " +
                        "`count` INT NOT NULL, " +
                        "PRIMARY KEY(id), " +
                        "UNIQUE KEY name_date(name(50), birthDate))");
                connection.createStatement().execute("CREATE TABLE voting_station(" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "number INT NOT NULL, " +
                        "date DATE NOT NULL, " +
                        "time TIME NOT NULL, " +
                        "PRIMARY KEY(id), KEY(number));");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void countVoter(String name, String birthDay) throws SQLException {
        birthDay = birthDay.replace('.', '-');
        String sql =
                "SELECT id FROM voter_count WHERE birthDate='" + birthDay + "' AND name='" + name + "'";
        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
        if (!rs.next()) {
            DBConnection.getConnection().createStatement()
                    .execute("INSERT INTO voter_count(name, birthDate, `count`) VALUES('" +
                            name + "', '" + birthDay + "', 1)");
        } else {
            Integer id = rs.getInt("id");
            DBConnection.getConnection().createStatement()
                    .execute("UPDATE voter_count SET `count`=`count`+1 WHERE id=" + id);
        }
        rs.close();
    }

    public static void printVoterCounts() throws SQLException {
        String sql = "SELECT name, birthDate, `count` FROM voter_count WHERE `count` > 1";
        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
        while (rs.next()) {
            System.out.println("\t" + rs.getString("name") + " (" +
                rs.getString("birthDate") + ") - " + rs.getInt("count"));
        }
    }
}
