package org.fullstack4.repository;

import org.fullstack4.domain.ShareBbsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShareRepository extends JpaRepository<ShareBbsEntity, Integer> {
    List<ShareBbsEntity> findByBbsIdx(int bbs_idx);
    List<ShareBbsEntity> findByRequestId(String user_id);
    List<ShareBbsEntity> findByResponseId(String user_id);
    int countByBbsIdxAndResponseId(int bbs_idx,String response_id);
    int countByBbsIdx(int bbs_idx);
}
