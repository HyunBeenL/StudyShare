package org.fullstack4.repository;

import org.fullstack4.domain.BoardEntity;
import org.fullstack4.repository.pagingSearch.BoardPagingSearch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer>, BoardPagingSearch {
    List<BoardEntity> findByUserIdAndBbsExposureAndBbsDuration1LessThanEqualAndBbsDuration2GreaterThanEqualOrderByBbsDuration2(String userId, String bbsExposure, LocalDate bbsDuration1, LocalDate bbsDuration2);
}
