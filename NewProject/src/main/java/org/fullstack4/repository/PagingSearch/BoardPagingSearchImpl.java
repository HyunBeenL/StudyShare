package org.fullstack4.repository.pagingSearch;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.domain.BoardEntity;
import org.fullstack4.domain.QBoardEntity;
import org.fullstack4.domain.QShareBbsEntity;
import org.fullstack4.domain.ShareBbsEntity;
import org.fullstack4.dto.PageRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDate;
import java.util.List;

@Log4j2
public class BoardPagingSearchImpl extends QuerydslRepositorySupport implements BoardPagingSearch {
    public BoardPagingSearchImpl() {
        super(BoardEntity.class);
    }

    @Override
    public Page<BoardEntity> search(Pageable pageable, String types[], String search_keyword, LocalDate Search_date1, LocalDate Search_date2,String user_id) {
        log.info("==============================");
        log.info("BoardSearchImpl >> search START");
        log.info("Search_date1 = " + Search_date1);
        log.info("Search_date2 = " + Search_date2);
        QBoardEntity qBoard = QBoardEntity.boardEntity; // QBoardEntity 객체 생성
        // SELECT ~~ FROM QBoardEntity <- tbl_board // tbl_board에서 SELECT 해오라는 뜻과 같음
        JPQLQuery<BoardEntity> query = from(qBoard);

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if ((types != null && types.length > 0) && (search_keyword != null && search_keyword.length() > 0)) {
            // type : t(제목), c(내용), u(사용자아이디)
            for (String type : types) {
                switch (type) {
                    case "t" : booleanBuilder.or(qBoard.bbs_title.like("%" + search_keyword + "%"));
                        break;
                    case "c" : booleanBuilder.or(qBoard.bbs_content.like("%" + search_keyword + "%"));
                        break;
                }
            }
        }
        if(Search_date1 !=null && Search_date2 != null){
            booleanBuilder.or(qBoard.regDate.between(Search_date1.atStartOfDay(), Search_date2.atStartOfDay()));
        }
        query.where(booleanBuilder);
        query.where(qBoard.userId.eq(user_id));
        query.where(qBoard.bbsIdx.gt(0));


        // paging
        this.getQuerydsl().applyPagination(pageable, query);

        log.info("query : {}", query);

        List<BoardEntity> boards = query.fetch();
        int total = (int)query.fetchCount();

        log.info("boards : {}", boards);
        log.info("total : {}", total);


        log.info("BoardSearchImpl >> search END");
        log.info("==============================");

        return new PageImpl<>(boards, pageable, (long)total);
    }

    @Override
    public Page<BoardEntity> shareBbsSearch(Pageable pageable, PageRequestDTO requestDTO) {
        QBoardEntity qBoard = QBoardEntity.boardEntity;
        QShareBbsEntity qShareBbs = QShareBbsEntity.shareBbsEntity;

        JPQLQuery<BoardEntity> query = from(qBoard).distinct();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if ((requestDTO.getSearch_types() != null && requestDTO.getSearch_types().length > 0) && (requestDTO.getSearch_word() != null && requestDTO.getSearch_word().length() > 0)) {
            // type : t(제목), c(내용), u(사용자아이디)
            for (String type : requestDTO.getSearch_types()) {
                switch (type) {
                    case "t" : booleanBuilder.or(qBoard.bbs_title.like("%" + requestDTO.getSearch_word() + "%"));
                        break;
                    case "c" : booleanBuilder.or(qBoard.bbs_content.like("%" + requestDTO.getSearch_word() + "%"));
                        break;
                }
            }
        }
        if(requestDTO.getSearch_date1() !=null && requestDTO.getSearch_date2() != null){
            booleanBuilder.or(qBoard.regDate.between(requestDTO.getSearch_date1().atStartOfDay(), requestDTO.getSearch_date2().atStartOfDay()));
        }
        query.innerJoin(qShareBbs).on(qBoard.bbsIdx.eq(qShareBbs.bbsIdx));
        if(requestDTO.getSort_type() !=null && !requestDTO.getSort_type().isEmpty()) {
            if (requestDTO.getSort_type().equals("you")) {
                query.where(booleanBuilder.and(qShareBbs.responseId.eq(requestDTO.getUser_id())));
            } else {
                query.where(booleanBuilder.and(qShareBbs.requestId.eq(requestDTO.getUser_id())));
            }
        }else{
            query.where(booleanBuilder.and(qShareBbs.requestId.eq(requestDTO.getUser_id())));
        }


        this.getQuerydsl().applyPagination(pageable, query);

        log.info("query : {}", query);

        List<BoardEntity> boards = query.fetch();
        int total = (int)query.fetchCount();

        log.info("boards : {}", boards);
        log.info("total : {}", total);


        log.info("BoardSearchImpl >> search END");
        log.info("==============================");
        return new PageImpl<>(boards, pageable, (long)total);
    }
}
