/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymjankari_v1.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author AmitShrestha
 */
public class DBConnection {
    public static Connection Connector(){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connect = DriverManager.getConnection("jdbc:sqlite:GymJankariDB.db");
            return connect;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
            return null;
        }
    } 
}
