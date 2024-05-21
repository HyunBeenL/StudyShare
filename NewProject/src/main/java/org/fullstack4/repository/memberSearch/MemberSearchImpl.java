package org.fullstack4.repository.memberSearch;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.domain.BoardEntity;
import org.fullstack4.domain.MemberEntity;
import org.fullstack4.domain.QMemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class MemberSearchImpl extends QuerydslRepositorySupport implements MemberSearch {
    public MemberSearchImpl() {
        super(MemberEntity.class);
    }
    @Override
    public Page<MemberEntity> search(Pageable pageable, String search_keyword) {
        QMemberEntity qMember = QMemberEntity.memberEntity;
        JPQLQuery<MemberEntity> query = from(qMember);

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if ((search_keyword != null && search_keyword.length() > 0)) {
            // type : t(제목), c(내용), u(사용자아이디)
            booleanBuilder.or(qMember.userId.like("%" + search_keyword + "%"));
            booleanBuilder.or(qMember.user_name.like("%" + search_keyword + "%"));
        }
        query.where(booleanBuilder);
        this.getQuerydsl().applyPagination(pageable, query);

        log.info("query : {}", query);

        List<MemberEntity> member = query.fetch();
        int total = (int)query.fetchCount();

        log.info("member : {}", member);
        log.info("total : {}", total);


        log.info("BoardSearchImpl >> search END");
        log.info("==============================");
        return new PageImpl<>(member, pageable, (long)total);
    }
}
