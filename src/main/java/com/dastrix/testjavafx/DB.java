package com.dastrix.testjavafx;

import java.sql.*;

public class DB {
    private final String HOST = "localhost";
    private final String PORT = "3307";
    private final String DB_NAME = "url_shorter";
    private final String LOGIN = "root";
    private final String PASS = "root";

    private Connection dbConn = null;

    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConn = DriverManager.getConnection(connStr, LOGIN, PASS);
        return dbConn;
    }

    public void isConnected() throws SQLException, ClassNotFoundException {
        dbConn = getDbConnection();
        System.out.println(dbConn.isValid(1000));
    }
    //подключение
    public boolean addUrl(String poln_ss, String sokr_ss) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO `url_shorter` (`poln_ss`, `sokr_ss`) VALUES(?, ?)";

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM `url_shorter` WHERE `sokr_ss` = '" + sokr_ss + "' LIMIT 1");
        if(res.next())
            return false;

        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, poln_ss);
        prSt.setString(2, sokr_ss);
        prSt.executeUpdate();
        return true;
    }

    public ResultSet getShorts() throws SQLException, ClassNotFoundException {
        String sql = "SELECT `sokr_ss` FROM `url_shorter`";
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);
        return res;
    }
}
