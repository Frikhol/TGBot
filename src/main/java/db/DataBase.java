package db;

import java.sql.*;

public class DataBase {
    private static String PATH= "resources/DataBase/db.db";

    public static void setPATH(String PATH) {
        DataBase.PATH = PATH;
    }

    private static Connection conn = null;
    private static ResultSet resultSet;
    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static void Conn() throws ClassNotFoundException, SQLException
    {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:"+PATH);
    }
    public ResultSet read(String order) throws SQLException {
        Statement statmt = conn.createStatement();
        resultSet = statmt.executeQuery(order);
        statmt.close();
        return resultSet;
    }

    public boolean update(String order) throws SQLException {
        Statement statmt = conn.createStatement();
        boolean result = statmt.execute(order);
        statmt.close();
        return result;
    }

    public static ResultSet getLastResult() {
        return resultSet;
    }

    public static void CloseDB() throws  SQLException
    {
        conn.close();
        resultSet.close();
        System.out.println("Соединения закрыты");
    }
}
