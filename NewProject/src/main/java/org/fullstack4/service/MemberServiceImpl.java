package org.fullstack4.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.domain.MemberEntity;
import org.fullstack4.dto.MemberDTO;
import org.fullstack4.exception.InsufficientStockException;
import org.fullstack4.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Override
    public boolean login(String id,String pwd,HttpServletRequest req) {
        HttpSession session = req.getSession();
        MemberEntity member = memberRepository.findByUserId(id);
        if(member.getLogincount() >= 5){
            return false;
        }else {
            if (member.getUser_pwd().equals(pwd)) {
                session.setAttribute("user_id", id);
                session.setAttribute("user_name", member.getUser_name());

                MemberDTO dto = modelMapper.map(member, MemberDTO.class);
                dto.setLastlogin_date(LocalDateTime.now());
                dto.setLogincount(0);

                MemberEntity member1 = modelMapper.map(dto, MemberEntity.class);
                memberRepository.save(member1);

                return true;
            } else {

                MemberDTO dto = modelMapper.map(member, MemberDTO.class);
                dto.setLogincount(member.getLogincount()+1);
                MemberEntity member1 = modelMapper.map(dto, MemberEntity.class);
                memberRepository.save(member1);
                return false;
            }
        }
    }

    @Override
    @Transactional(rollbackFor = {InsufficientStockException.class, Exception.class})
    public void join(MemberDTO memberDTO) throws InsufficientStockException{
        if(memberDTO.getUserId() == null || memberDTO.getUserId().trim().equals("")){
            throw new InsufficientStockException("아이디를 입력해주세요.");
        }
        MemberEntity memberEntity = modelMapper.map(memberDTO, MemberEntity.class);
        memberRepository.save(memberEntity);

    }

    @Override
    public void modify(MemberDTO memberDTO) {
        int idx = memberRepository.findByUserId(memberDTO.getUserId()).getUser_idx();
        memberDTO.setUser_idx(idx);
        MemberEntity memberEntity = modelMapper.map(memberDTO, MemberEntity.class);
        memberEntity.setModify_date(LocalDateTime.now());
        memberRepository.save(memberEntity);

    }

    @Override
    public void pwdmodify(String id, String pwd) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public String findpwd(String id) {
        String pwd = memberRepository.findByUserId(id).getUser_pwd();
        return pwd;
    }

    @Override
    public MemberDTO view(String id) {
        MemberEntity member = memberRepository.findByUserId(id);
        MemberDTO memberDTO = modelMapper.map(member, MemberDTO.class);
        return memberDTO;
    }
}
