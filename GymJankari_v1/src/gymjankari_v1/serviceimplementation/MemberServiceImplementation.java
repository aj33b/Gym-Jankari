/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymjankari_v1.serviceimplementation;

import gymjankari_v1.PaymentDetailsPage.PaymentDetailsPageController;
import gymjankari_v1.dateconverter.DateConverter;
import gymjankari_v1.dateconverter.DateConverterInterface;
import gymjankari_v1.dateconverter.NepaliDateStorage;
import gymjankari_v1.dbconnection.DBConnection;
import gymjankari_v1.models.Member;
import gymjankari_v1.service.MemberService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author AmitShrestha
 */
public class MemberServiceImplementation implements MemberService {

    Connection connect = null;
    Connection connect1 = null;

    public MemberServiceImplementation() {
        connect = DBConnection.Connector();
        connect1 = DBConnection.Connector();
    }

    @Override
    public boolean addMember(Member member) {
        try {
            String write_sql = "insert into gymjankaridb(MemberId,FullName,DateOfBirth,Gender,Height,Weight,Street,VDCMun,WardNo,District,EmailAddress,Landline,Mobile,StartTime,EndTime,MemberSince,PaymentDate,MonthlyRate,PaymentAmount,ExpiryDate,Picture,DaysRemaining)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            String write_sql1 = "insert into paymentdetailsdb(MemberId,PaymentDate,PaymentAmount)values(?,?,?)";
            PreparedStatement write_pstm = connect.prepareStatement(write_sql);
            PreparedStatement write_pstm1 = connect.prepareStatement(write_sql1);
            write_pstm.setString(1, member.getmId());
            write_pstm.setString(2, member.getFullName());
            write_pstm.setString(3, member.getDOB());
            write_pstm.setString(4, member.getGender());
            write_pstm.setString(5, member.getHeight());
            write_pstm.setString(6, member.getWeight());
            write_pstm.setString(7, member.getStreet());
            write_pstm.setString(8, member.getVdcmun());
            write_pstm.setString(9, member.getWard());
            write_pstm.setString(10, member.getDistrict());
            write_pstm.setString(11, member.getEmail());
            write_pstm.setString(12, member.getLandline());
            write_pstm.setString(13, member.getMobile());
            write_pstm.setString(14, member.getStartTime());
            write_pstm.setString(15, member.getEndTime());
            write_pstm.setString(16, member.getMemberSince());
            write_pstm.setString(17, member.getPayDate());
            write_pstm.setFloat(18, member.getPayRate());
            write_pstm.setFloat(19, member.getPayAmount());
            write_pstm.setString(20, member.getExpiryDate());
            write_pstm.setString(21, member.getPicture());
            write_pstm.setLong(22, member.getDay());
            write_pstm.execute();
            write_pstm1.setString(1, member.getmId());
            write_pstm1.setString(2, member.getPayDate());
            write_pstm1.setFloat(3, member.getPayAmount());
            write_pstm1.execute();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(MemberServiceImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public ObservableList<Member> getAllMember() {
        DateConverterInterface dateConverterInterface = new DateConverter();
        ObservableList<Member> memberList = FXCollections.observableArrayList();
        String read_sql = "select * from gymjankaridb";
        try {
            Statement read_stm = connect.createStatement();
            ResultSet rs = read_stm.executeQuery(read_sql);
            while (rs.next()) {
                Member member = new Member();
                member.setPrimaryId("PrimaryId");
                member.setDisplayId(rs.getString("MemberId"));
                member.setFullName(rs.getString("FullName"));
                member.setDOB(rs.getString("DateOfBirth"));
                member.setGender(rs.getString("Gender"));
                member.setHeight(rs.getString("Height"));
                member.setWeight(rs.getString("Weight"));
                member.setStreet(rs.getString("Street"));
                member.setVdcmun(rs.getString("VDCMun"));
                member.setWard(rs.getString("WardNo"));
                member.setDistrict(rs.getString("District"));
                member.setEmail(rs.getString("EmailAddress"));
                member.setLandline(rs.getString("Landline"));
                member.setMobile(rs.getString("Mobile"));
                member.setMemberSince(rs.getString("MemberSince"));
                member.setStartTime(rs.getString("StartTime"));
                member.setEndTime(rs.getString("EndTime"));
                member.setPayDate(rs.getString("PaymentDate"));
                member.setPayRate(rs.getFloat("MonthlyRate"));
                member.setPayAmount(rs.getFloat("PaymentAmount"));
                member.setExpiryDate(dateConverterInterface.convertAdToBs(rs.getString("ExpiryDate")));
                member.setPicture(rs.getString("Picture"));
                member.setDay(rs.getInt("DaysRemaining"));
                memberList.add(member);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MemberServiceImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return memberList;
    }

    @Override
    public boolean deleteMember(String id) {
        String delete_sql = "delete from gymjankaridb where MemberId='" + id + "'";
        String delete_sql1 = "delete from paymentdetailsdb where MemberId='" + id + "'";
        try {
            Statement delete_stm = connect.createStatement();
            Statement delete_stm1 = connect.createStatement();
            delete_stm.execute(delete_sql);
            delete_stm1.execute(delete_sql1);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MemberServiceImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean editMember(Member member, String id) {
        String update_sql = "update gymjankaridb set MemberId=?,FullName=?,DateOfBirth=?,Gender=?,Height=?,Weight=?,Street=?,VDCMun=?,Wardno=?,District=?,EmailAddress=?,Landline=?,Mobile=?,StartTime=?,EndTime=?,MemberSince=?,MonthlyRate=?,Picture=? where MemberId='" + id + "'";
        try {
            PreparedStatement update_pstm = connect.prepareStatement(update_sql);
            update_pstm.setString(1, member.getDisplayId());
            update_pstm.setString(2, member.getFullName());
            update_pstm.setString(3, member.getDOB());
            update_pstm.setString(4, member.getGender());
            update_pstm.setString(5, member.getHeight());
            update_pstm.setString(6, member.getWeight());
            update_pstm.setString(7, member.getStreet());
            update_pstm.setString(8, member.getVdcmun());
            update_pstm.setString(9, member.getWard());
            update_pstm.setString(10, member.getDistrict());
            update_pstm.setString(11, member.getEmail());
            update_pstm.setString(12, member.getLandline());
            update_pstm.setString(13, member.getMobile());
            update_pstm.setString(14, member.getStartTime());
            update_pstm.setString(15, member.getEndTime());
            update_pstm.setString(16, member.getMemberSince());
            update_pstm.setFloat(17, member.getPayRate());
            update_pstm.setString(18, member.getPicture());
            update_pstm.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MemberServiceImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Member getById(String displayId) {
        Member member = new Member();
        String read_sql = "select * from gymjankaridb where MemberId='" + displayId + "'";
        try {
            Statement read_stm = connect.createStatement();
            ResultSet rs = read_stm.executeQuery(read_sql);
            while (rs.next()) {
                member.setPrimaryId(rs.getString("PrimaryId"));
                member.setDisplayId(rs.getString("MemberId"));
                member.setFullName(rs.getString("FullName"));
                member.setDOB(rs.getString("DateOfBirth"));
                member.setGender(rs.getString("Gender"));
                member.setHeight(rs.getString("Height"));
                member.setWeight(rs.getString("Weight"));
                member.setStreet(rs.getString("Street"));
                member.setVdcmun(rs.getString("VDCMun"));
                member.setWard(rs.getString("WardNo"));
                member.setDistrict(rs.getString("District"));
                member.setEmail(rs.getString("EmailAddress"));
                member.setLandline(rs.getString("Landline"));
                member.setMobile(rs.getString("Mobile"));
                member.setMemberSince(rs.getString("MemberSince"));
                member.setStartTime(rs.getString("StartTime"));
                member.setEndTime(rs.getString("EndTime"));
                member.setPayDate(rs.getString("PaymentDate"));
                member.setPayRate(rs.getFloat("MonthlyRate"));
                member.setPayAmount(rs.getFloat("PaymentAmount"));
                member.setExpiryDate(rs.getString("ExpiryDate"));
                member.setPicture(rs.getString("Picture"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MemberServiceImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return member;
    }

    @Override
    public ObservableList<Member> searchById(String mId) {
        ObservableList<Member> memberList = FXCollections.observableArrayList();
        String read_sql = "select * from gymjankaridb where MemberId='" + mId + "'";
        try {
            Statement read_stm = connect.createStatement();
            ResultSet rs = read_stm.executeQuery(read_sql);
            while (rs.next()) {
                Member member = new Member();
                member.setPrimaryId(rs.getString("PrimaryId"));
                member.setDisplayId(rs.getString("MemberId"));
                member.setFullName(rs.getString("FullName"));
                member.setDOB(rs.getString("DateOfBirth"));
                member.setGender(rs.getString("Gender"));
                member.setHeight(rs.getString("Height"));
                member.setWeight(rs.getString("Weight"));
                member.setStreet(rs.getString("Street"));
                member.setVdcmun(rs.getString("VDCMun"));
                member.setWard(rs.getString("WardNo"));
                member.setDistrict(rs.getString("District"));
                member.setEmail(rs.getString("EmailAddress"));
                member.setLandline(rs.getString("Landline"));
                member.setMobile(rs.getString("Mobile"));
                member.setMemberSince(rs.getString("MemberSince"));
                member.setStartTime(rs.getString("StartTime"));
                member.setEndTime(rs.getString("EndTime"));
                member.setPayDate(rs.getString("PaymentDate"));
                member.setPayRate(rs.getFloat("MonthlyRate"));
                member.setPayAmount(rs.getFloat("PaymentAmount"));
                member.setExpiryDate(rs.getString("ExpiryDate"));
                member.setPicture(rs.getString("Picture"));
                memberList.add(member);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MemberServiceImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return memberList;
    }

    @Override
    public ObservableList<Member> searchByName(String fullName) {
        ObservableList<Member> memberList = FXCollections.observableArrayList();
        String read_sql = "select * from gymjankaridb where FullName='" + fullName + "'";
        try {
            Statement read_stm = connect.createStatement();
            ResultSet rs = read_stm.executeQuery(read_sql);
            while (rs.next()) {
                Member member = new Member();
                member.setPrimaryId(rs.getString("PrimaryId"));
                member.setDisplayId(rs.getString("MemberId"));
                member.setFullName(rs.getString("FullName"));
                member.setDOB(rs.getString("DateOfBirth"));
                member.setGender(rs.getString("Gender"));
                member.setHeight(rs.getString("Height"));
                member.setWeight(rs.getString("Weight"));
                member.setStreet(rs.getString("Street"));
                member.setVdcmun(rs.getString("VDCMun"));
                member.setWard(rs.getString("WardNo"));
                member.setDistrict(rs.getString("District"));
                member.setEmail(rs.getString("EmailAddress"));
                member.setLandline(rs.getString("Landline"));
                member.setMobile(rs.getString("Mobile"));
                member.setMemberSince(rs.getString("MemberSince"));
                member.setStartTime(rs.getString("StartTime"));
                member.setEndTime(rs.getString("EndTime"));
                member.setPayDate(rs.getString("PaymentDate"));
                member.setPayRate(rs.getFloat("MonthlyRate"));
                member.setPayAmount(rs.getFloat("PaymentAmount"));
                member.setExpiryDate(rs.getString("ExpiryDate"));
                member.setPicture("Picture");
                memberList.add(member);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MemberServiceImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return memberList;
    }

    @Override
    public boolean checkDuplicate(String mId) {
        String read_sql = "select MemberId from gymjankaridb";
        try {
            Statement read_stm = connect1.createStatement();
            ResultSet rs = read_stm.executeQuery(read_sql);
            while (rs.next()) {
                String id = rs.getString("MemberId");
                if (id.equalsIgnoreCase(mId)) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MemberServiceImplementation.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connect1.close();
            } catch (SQLException ex) {
                Logger.getLogger(MemberServiceImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public ObservableList<Member> getPaymentDetails(String mId) {
        DateConverterInterface dateConverterInterface = new DateConverter();
        ObservableList<Member> paymentDetails = FXCollections.observableArrayList();
        String read_sql = "select * from paymentdetailsdb where MemberId='" + mId + "'";
        try {
            Statement read_stm = connect.createStatement();
            ResultSet rs = read_stm.executeQuery(read_sql);
            while (rs.next()) {
                Member member = new Member();
                member.setPrimaryId(String.valueOf(rs.getInt("PrimaryId")));
                member.setDisplayId(rs.getString("MemberId"));
                member.setPayDate(dateConverterInterface.convertAdToBs(rs.getString("PaymentDate")));
                member.setPayAmount(rs.getFloat("PaymentAmount"));
                paymentDetails.add(member);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MemberServiceImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paymentDetails;
    }

    @Override
    public boolean updatePaymentDetails(Member member, String displayId) {
        try {
            String update_sql = "update gymjankaridb set PaymentDate=?,PaymentAmount=?,ExpiryDate=?,DaysRemaining=? where MemberId='" + displayId + "'";
            String write_sql1 = "insert into paymentdetailsdb(MemberId,PaymentDate,PaymentAmount)values(?,?,?)";
            PreparedStatement update_pstm = connect.prepareStatement(update_sql);
            PreparedStatement write_pstm1 = connect.prepareStatement(write_sql1);
            update_pstm.setString(1, member.getPayDate());
            update_pstm.setFloat(2, member.getPayAmount());
            update_pstm.setString(3, member.getExpiryDate());
            update_pstm.setInt(4, member.getDay());
            update_pstm.execute();
            write_pstm1.setString(1, displayId);
            write_pstm1.setString(2, member.getPayDate());
            write_pstm1.setFloat(3, member.getPayAmount());
            write_pstm1.execute();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(MemberServiceImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public String numberToMonthConversion(int day) {
        switch (day) {
            case 1:
                return "Baisakh";
            case 2:
                return "Jestha";
            case 3:
                return "Asadh";
            case 4:
                return "Shrawan";
            case 5:
                return "Bhadra";
            case 6:
                return "Asoj";
            case 7:
                return "Kartik";
            case 8:
                return "Mangsir";
            case 9:
                return "Poush";
            case 10:
                return "Magh";
            case 11:
                return "Falgun";
            case 12:
                return "Chaitra";
        }
        return null;
    }

    @Override
    public String monthToNumberConversion(String day) {
        switch (day) {
            case "Baisakh":
                return "01";
            case "Jestha":
                return "02";
            case "Asadh":
                return "03";
            case "Shrawan":
                return "04";
            case "Bhadra":
                return "05";
            case "Asoj":
                return "06";
            case "Kartik":
                return "07";
            case "Mangsir":
                return "08";
            case "Poush":
                return "09";
            case "Magh":
                return "10";
            case "Falgun":
                return "11";
            case "Chaitra":
                return "12";

        }
        return null;
    }

    @Override
    public ObservableList<String> dayValues(String bsYear, int bsMonth) {
        String[] days = {"01", "02", "03", "04", "05", "06", "07", "08", "09",
            "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21",
            "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32"};
        DateConverterInterface dateConverterInterface = new DateConverter();
        ObservableList<String> returnList = FXCollections.observableArrayList();
        int index = dateConverterInterface.getBsIndex(Integer.parseInt(bsYear));
        for (int i = 1; i <= NepaliDateStorage.nepaliMonthDays.get(index)[bsMonth - 1]; i++) {
            returnList.add(days[i - 1]);
        }
        return returnList;
    }

    @Override
    public int calculateDays(String string1) {
        long diff = 0;
        try {
            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = myFormat.parse(string1);
            Date date2 = myFormat.parse(LocalDate.now().toString());
            diff = date1.getTime() - date2.getTime();
        } catch (ParseException ex) {
            Logger.getLogger(PaymentDetailsPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean updateDaysRemaining() {
        MemberService memberService = new MemberServiceImplementation();
        try {
            String read_sql = "select MemberId,ExpiryDate from gymjankaridb";
            String update_sql;
            PreparedStatement update_pstm;
            Statement read_stm = connect.createStatement();
            ResultSet rs = read_stm.executeQuery(read_sql);
            while (rs.next()) {
                String memberId = rs.getString("MemberId");
                String expiryDate = rs.getString("ExpiryDate");
                update_sql = "update gymjankaridb set DaysRemaining=? where MemberId='" + memberId + "'";
                update_pstm = connect.prepareStatement(update_sql);
                update_pstm.setInt(1, memberService.calculateDays(expiryDate));
                update_pstm.execute();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MemberServiceImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

}
