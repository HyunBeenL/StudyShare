//package org.fullstack4.repository;
//
//import jakarta.validation.Valid;
//import lombok.extern.log4j.Log4j2;
//import org.fullstack4.domain.MemberEntity;
//import org.fullstack4.dto.BoardDTO;
//import org.fullstack4.dto.MemberDTO;
//import org.fullstack4.dto.PageRequestDTO;
//import org.fullstack4.dto.PageResponseDTO;
//import org.fullstack4.service.MemberServiceImpl;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Optional;
//import java.util.stream.IntStream;
//
//@Log4j2
//@SpringBootTest
//public class MemberTest {
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    private MemberServiceImpl member;
//
//    @Test
//    public void testRegist() {
//        log.info("=====================================");
//        log.info("MemberRepositoryTest>> start ");
//        IntStream.rangeClosed(11, 30)
//                .forEach(i -> {
//                    MemberEntity member = MemberEntity.builder()
//                            .userId("test" + i)
//                            .user_name("테스트" + i)
//                            .user_pwd("password" + i)
//                            .user_phone("010")
//                            .user_phone2("111" + i)
//                            .user_phone3("222" + i)
//                            .userEmail1("test" + i)
//                            .userEmail2("naver.com")
//                            .build();
//
//                    MemberEntity memberResult = memberRepository.save(member);
//                    log.info("result : {}", memberResult);
//                });
//
//        log.info("=====================================");
//
//    }
//}
//    @Test
//    public void LoginTest(){
//        log.info("=====================================");
//        log.info("FindIdTest>> start ");
//
//        String id="test0";
//        String pwd="password1";
//        MemberEntity member = memberRepository.findByUserId(id);
//        if(member.getUser_pwd().equals(pwd)){
//            log.info("result : {}", "로그인 성공");
//        }else{
//            log.info("result : {}", "로그인 실패");
//        }
//
//
//    }
//
//    @Test
//    public void findpwd() {
//        String id="test0";
//        String pwd = memberRepository.findByUserId(id).getUser_pwd();
//        log.info("findpwd : {}", pwd);
//    }
//
//    @Test
//    public void modify(){
//        String id="test0";
//        int idx = memberRepository.findByUserId(id).getUser_idx();
//        MemberEntity member = MemberEntity.builder()
//                .user_idx(idx)
//                .userId(id)
//                .user_name("테스트")
//                .user_pwd("password")
//                .user_phone("010")
//                .user_phone2("111")
//                .user_phone3("222")
//                .user_email1("test")
//                .user_email2("naver.com")
//                .build();
//
//        memberRepository.save(member);
//
//    }
//
//    @Test
//    public void pwdmodify(){
//        String id="test0";
//        int idx = memberRepository.findByUserId(id).getUser_idx();
//        MemberEntity member = MemberEntity.builder()
//                .user_idx(idx)
//                .userId(id)
//                .user_pwd("password12")
//                .build();
//
//        memberRepository.save(member);
//
//    }
//
//    @Test
//    public void view(){
//        String id = "test0";
//        MemberDTO dto = member.view(id);
//        log.info("========================"+dto.toString()+"===============");
//    }
//
//    @Test
//    public void search(){
//        PageRequestDTO pageRequestDTO = new PageRequestDTO();
//        pageRequestDTO.setPage(0);
//        pageRequestDTO.setPage_size(5);
//        pageRequestDTO.setPage_block_size(5);
//        pageRequestDTO.setSearch_word("leehv1234");
//        PageResponseDTO<MemberDTO> responseDTO = member.search(pageRequestDTO);
//        log.info("responseDTO : {}", responseDTO);
//    }
//
//}
