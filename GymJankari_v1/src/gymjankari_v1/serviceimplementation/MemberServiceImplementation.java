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
import java.util.ArrayList;
import java.util.List;
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
    public MemberServiceImplementation(){
        connect = DBConnection.Connector();
    }

    @Override
    public boolean addMember(Member member) {
        try {
            String write_sql = "insert into gymjankaridb(MemberId,FullName,DateOfBirth,Gender,Height,Weight,Street,VDCMun,WardNo,District,EmailAddress,Landline,Mobile,Shift,MemberSince,PaymentDate,MonthlyRate,PaymentAmount,ExpiryDate)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
            write_pstm.setString(14, member.getShift());
            write_pstm.setString(15, member.getMemberSince());
            write_pstm.setString(16, member.getPayDate());
            write_pstm.setFloat(17, member.getPayRate());
            write_pstm.setFloat(18, member.getPayAmount());
            write_pstm.setString(19, member.getExpiryDate());
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
                System.out.println(rs.getString("MemberId"));
                member.setmId(rs.getString("MemberId"));
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
                member.setShift(rs.getString("Shift"));   
                member.setPayDate(rs.getString("PaymentDate"));
                member.setPayRate(rs.getFloat("MonthlyRate"));
                member.setPayAmount(rs.getFloat("PaymentAmount"));
                member.setExpiryDate(rs.getString("ExpiryDate"));
                memberList.add(member);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MemberServiceImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return memberList;
    }

    @Override
    public boolean deleteMember(String mId) {
        return false;
    }

    @Override
    public boolean editMember(Member member) {
        return false;
    }

    @Override
    public Member getById(String mId) {
        return null;
    }

    @Override
    public List<Member> searchById(String mId) {
        return null;
    }

    @Override
    public List<Member> searchByName(String fullName) {
        return null;
    }
    
}
