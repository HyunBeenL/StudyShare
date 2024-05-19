package org.fullstack4.repository;

import org.fullstack4.domain.MemberEntity;
import org.fullstack4.repository.memberSearch.MemberSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer>, MemberSearch {
    @Query(value= "SELECT NOW()", nativeQuery = true)
    public String getNow();
    MemberEntity findByUserId(String user_id);
    MemberEntity findByUserEmail1AndUserEmail2(String user_email1,String user_email2);
//    int CountByUserId(String user_id);

}
