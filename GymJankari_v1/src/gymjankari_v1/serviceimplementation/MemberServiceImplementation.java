/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymjankari_v1.serviceimplementation;

import gymjankari_v1.dbconnection.DBConnection;
import gymjankari_v1.models.Member;
import gymjankari_v1.service.MemberService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author AmitShrestha
 */
public class MemberServiceImplementation implements MemberService{
    
    Connection connect = null;
    Connection connect1 = null;
    public MemberServiceImplementation(){
        connect = DBConnection.Connector();
        connect1 = DBConnection.Connector();
    }

    @Override
    public boolean addMember(Member member) {
        try {
            String write_sql = "insert into gymjankaridb(MemberId,FullName,DateOfBirth,Gender,Height,Weight,Street,VDCMun,WardNo,District,EmailAddress,Landline,Mobile,StartTime,EndTime,MemberSince,PaymentDate,MonthlyRate,PaymentAmount,ExpiryDate,Picture)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement write_pstm = connect.prepareStatement(write_sql);
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
            write_pstm.execute();
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(MemberServiceImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public ObservableList<Member> getAllMember() {
        ObservableList<Member> memberList = FXCollections.observableArrayList();
        String read_sql = "select * from gymjankaridb";
        try {
            Statement read_stm = connect.createStatement();
            ResultSet rs = read_stm.executeQuery(read_sql);
            while(rs.next()){
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
    public boolean deleteMember(String id) {
        String delete_sql = "delete from gymjankaridb where MemberId='"+id+"'";
        try {
            Statement delete_stm = connect.createStatement();
            delete_stm.execute(delete_sql);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MemberServiceImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean editMember(Member member,String id) {
        String update_sql = "update gymjankaridb set MemberId=?,FullName=?,DateOfBirth=?,Gender=?,Height=?,Weight=?,Street=?,VDCMun=?,Wardno=?,District=?,EmailAddress=?,Landline=?,Mobile=?,StartTime=?,EndTime=?,MemberSince=?,MonthlyRate=?,Picture=? where MemberId='"+id+"'";
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
        String read_sql = "select * from gymjankaridb where MemberId='"+displayId+"'";
        try {
            Statement read_stm = connect.createStatement();
            ResultSet rs = read_stm.executeQuery(read_sql);
            while(rs.next()){
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
        String read_sql = "select * from gymjankaridb where MemberId='"+mId+"'";
        try {
            Statement read_stm = connect.createStatement();
            ResultSet rs = read_stm.executeQuery(read_sql);
            while(rs.next()){
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
        String read_sql = "select * from gymjankaridb where FullName='"+fullName+"'";
        try {
            Statement read_stm = connect.createStatement();
            ResultSet rs = read_stm.executeQuery(read_sql);
            while(rs.next()){
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
            while(rs.next()){
                String id = rs.getString("MemberId");
                if(id.equalsIgnoreCase(mId)){
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MemberServiceImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                connect1.close();
            } catch (SQLException ex) {
                Logger.getLogger(MemberServiceImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
}
