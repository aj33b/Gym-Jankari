/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymjankari_v1.service;

import gymjankari_v1.models.Member;
import java.util.List;

/**
 *
 * @author AmitShrestha
 */
public interface MemberService {
    public boolean addMember(Member member);
    public List<Member> getAllMember();
    public boolean deleteMember(String mId);
    public boolean editMember(Member member);
    public Member getById(String mId);
    public List<Member> searchById(String mId);
    public List<Member> searchByName(String fullName);
    
}
