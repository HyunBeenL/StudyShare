package org.fullstack4.repository.memberSearch;

import org.fullstack4.domain.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface MemberSearch {
    Page<MemberEntity> search(Pageable pageable, String search_keyword);
}
