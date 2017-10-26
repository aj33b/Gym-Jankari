/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymjankari_v1.service;

import gymjankari_v1.models.Member;
import javafx.collections.ObservableList;

/**
 *
 * @author AmitShrestha
 */
public interface MemberService {

    public boolean addMember(Member member);

    public ObservableList<Member> getAllMember();

    public boolean deleteMember(String id);

    public boolean editMember(Member member, String id);

    public Member getById(String mId);

    public ObservableList<Member> searchById(String mId);

    public ObservableList<Member> searchByName(String fullName);

    public boolean checkDuplicate(String mId);

    public ObservableList<Member> getPaymentDetails(String mId);

    public boolean updatePaymentDetails(Member member, String mId);
}
