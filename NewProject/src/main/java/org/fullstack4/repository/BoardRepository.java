package org.fullstack4.repository;

import org.fullstack4.domain.BoardEntity;
import org.fullstack4.domain.MemberEntity;
import org.fullstack4.repository.PagingSearch.BoardPagingSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer>, BoardPagingSearch {
}
