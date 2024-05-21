package org.fullstack4.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.domain.MemberEntity;
import org.fullstack4.dto.MemberDTO;
import org.fullstack4.dto.PageRequestDTO;
import org.fullstack4.dto.PageResponseDTO;
import org.fullstack4.exception.InsufficientStockException;
import org.fullstack4.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(rollbackFor = {InsufficientStockException.class, Exception.class})
    public boolean login(String id,String pwd,HttpServletRequest req) throws InsufficientStockException {
        HttpSession session = req.getSession();
        MemberEntity member = memberRepository.findByUserId(id);
        if(id.equals("") || id == null){
            throw new InsufficientStockException("아이디를 입력하세요.");
        }
        if(pwd.equals("") || pwd == null){
            throw new InsufficientStockException("비밀번호를 입력하세요.");
        }
        if(member == null) {
            throw new InsufficientStockException("해당하는 정보가 없습니다.");
        }
        if(member.getLastlogin_date() != null) {
            LocalDateTime now = LocalDateTime.now();
            long monthsDifference = ChronoUnit.MONTHS.between(member.getLastlogin_date(),now );
            log.info("monthsDifference:{}",monthsDifference);
            if(monthsDifference >= 6){
                throw new InsufficientStockException("마지막으로 로그인한지 6개월 이상된 계정입니다. 관리자에게 문의하세요.");
            }
        }

        if(member.getLogincount() >= 5){
            throw new InsufficientStockException("비밀번호를 5회 이상 틀린 계정입니다. 관리자에게 문의하세요.");
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
        if(memberDTO.getUser_pwd() == null || memberDTO.getUser_pwd().trim().equals("")){
            throw new InsufficientStockException("비밀번호를 입력해주세요.");
        }
        MemberEntity memberEntity = modelMapper.map(memberDTO, MemberEntity.class);
        memberRepository.save(memberEntity);

    }

    @Override
    public void modify(MemberDTO memberDTO) {
        int idx = memberRepository.findByUserId(memberDTO.getUserId()).getUser_idx();
        String orgpwd = memberRepository.findById(idx).get().getUser_pwd();
        String pwd = memberDTO.getUser_pwd();
        memberDTO.setUser_idx(idx);
        if(pwd != null && !pwd.equals("")){
            memberDTO.setUser_pwd(pwd);
        }else{
            memberDTO.setUser_pwd(orgpwd);
        }
        MemberEntity memberEntity = modelMapper.map(memberDTO, MemberEntity.class);
        memberEntity.setModify_date(LocalDateTime.now());
        memberRepository.save(memberEntity);

    }

    @Override
    public int pwdmodify(String id, String pwd) {

        MemberEntity memberentity = memberRepository.findByUserId(id);
        MemberDTO memberDTO = modelMapper.map(memberentity, MemberDTO.class);
        memberDTO.setUser_pwd(pwd);
        MemberEntity member = modelMapper.map(memberDTO, MemberEntity.class);

        int memidx = memberRepository.save(member).getUser_idx();
        return memidx;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public String findemail(String id) {
        String email = memberRepository.findByUserId(id).getUserEmail1()+"@"+memberRepository.findByUserId(id).getUserEmail2();
        return email;
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

    @Override
    public PageResponseDTO<MemberDTO> search(PageRequestDTO pageRequestDTO) {
        PageRequest pageable = PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getPage_size());
        Page<MemberEntity> result = memberRepository.search(pageable, pageRequestDTO.getSearch_word());

        List<MemberDTO> memberDTOList = result.toList().stream()
                .map(entity ->modelMapper.map(entity, MemberDTO.class))
                .collect(Collectors.toList());

        PageResponseDTO<MemberDTO> responseDTO = PageResponseDTO.<MemberDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(memberDTOList)
                .total_count((int)result.getTotalElements())
                .build();

        return responseDTO;
    }

    @Override
    @Transactional(rollbackFor = {InsufficientStockException.class, Exception.class})
    public int checkId(String id) throws InsufficientStockException{
        if(id == null || id.trim().equals("")){
            throw new InsufficientStockException("아이디를 입력해주세요.");
        }
        MemberEntity member = memberRepository.findByUserId(id);

        if(member == null) {

            return 0;
        }else{

            return 1;
        }

    }

    @Override
    @Transactional(rollbackFor = {InsufficientStockException.class, Exception.class})
    public int checkEmail(String email1, String email2) throws InsufficientStockException {
        if(email1 == null || email2 == null || email1.trim().equals("") || email2.trim().equals("")){
            throw new InsufficientStockException("이메일을 입력해주세요.");
        }
        MemberEntity member = memberRepository.findByUserEmail1AndUserEmail2(email1,email2);
        if(member == null) {
            return 0;
        }else{
            return 1;
        }
    }
}
