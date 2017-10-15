/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymjankari_v1.serviceimplementation;

import gymjankari_v1.models.Member;
import gymjankari_v1.service.MemberService;
import java.util.List;

/**
 *
 * @author AmitShrestha
 */
public class MemberServiceImplementation implements MemberService{

    @Override
    public boolean addMember(Member member) {
        return false;
    }

    @Override
    public List<Member> getAllMember() {
        return null;
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
