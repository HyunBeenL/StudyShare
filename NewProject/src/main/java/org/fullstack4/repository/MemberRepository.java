package org.fullstack4.repository;

import org.fullstack4.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {
    @Query(value= "SELECT NOW()", nativeQuery = true)
    public String getNow();
    MemberEntity findByUserId(String user_id);

}
