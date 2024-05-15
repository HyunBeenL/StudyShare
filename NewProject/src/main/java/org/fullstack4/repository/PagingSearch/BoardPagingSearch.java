package org.fullstack4.repository.PagingSearch;

import org.fullstack4.domain.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface BoardPagingSearch {
    Page<BoardEntity> search(Pageable pageable, String types[], String search_keyword, LocalDate Search_date1, LocalDate Search_date2);
}
