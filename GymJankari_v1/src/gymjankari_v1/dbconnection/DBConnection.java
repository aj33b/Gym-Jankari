/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymjankari_v1.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author AmitShrestha
 */
public class DBConnection {

    public static Connection Connector() {
        String url = "jdbc:sqlite:GymJankariDB.db";
        String sql = "CREATE TABLE IF NOT EXISTS \"gymjankaridb\" ( `PrimaryId` INTEGER PRIMARY KEY AUTOINCREMENT, `MemberId` TEXT, `FullName` TEXT, `DateOfBirth` TEXT, `Gender` TEXT, `Height` TEXT, `Weight` TEXT, `Street` TEXT, `VDCMun` TEXT, `WardNo` TEXT, `District` TEXT, `EmailAddress` TEXT, `Landline` TEXT, `Mobile` TEXT, `StartTime` TEXT, `EndTime` TEXT, `MemberSince` TEXT, `PaymentDate` TEXT, `MonthlyRate` REAL, `PaymentAmount` REAL, `ExpiryDate` TEXT, `Picture` TEXT, `DaysRemaining` INTEGER )";
        String sql1 = "CREATE TABLE IF NOT EXISTS \"paymentdetailsdb\" ( `PrimaryId` INTEGER PRIMARY KEY AUTOINCREMENT, `MemberId` TEXT, `PaymentDate` TEXT, `PaymentAmount` REAL, `ExpiryDate` TEXT, `MonthlyRate` REAL )";
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connect = DriverManager.getConnection(url);
            Statement stm = connect.createStatement();
            stm.execute(sql);
            stm.execute(sql1);
            return connect;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
            return null;
        }
    }
}
