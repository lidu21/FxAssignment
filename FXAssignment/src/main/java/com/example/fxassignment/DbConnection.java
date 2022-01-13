package com.example.fxassignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author THECREW
 */
public class DbConnection {

    private static final String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String user = "ADDIS";
    private static final String password = "123";

    public Connection connMethod() throws ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = null;

        try {
            con = DriverManager.getConnection(url, user, password);
            //JOptionPane.showMessageDialog(null, "connected");

        } catch (SQLException ex) {
           // JOptionPane.showConfirmDialog(null, "Error: " + ex);
        }
        return con;
    }

}