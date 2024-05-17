package org.fullstack4.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
@Data
public class PageResponseDTO<E> {
    private int total_count;
    private int page;
    private int page_size;
    private int total_page;
    private int page_skip_count;
    private int page_block_size;
    private int page_block_start;
    private int page_block_end;

    private boolean prev_page_plag;
    private boolean next_page_plag;

    private String search_type;
    private String[] search_types;
    private String search_word;
    private String linkParams;
    private String sort_type;
    private String user_id;

    List<E> dtolist;

    PageResponseDTO() {}

    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, int total_count){
        log.info("===============================================");
        log.info("PageResponseDTO START");

        this.total_count = total_count; // 전체 게시글 수
        this.page = pageRequestDTO.getPage(); // 현재 페이지 번호
        this.page_size = pageRequestDTO.getPage_size(); // 한 페이지에 표시될 게시글 수
        this.page_skip_count = (this.page-1) * this.page_size; //DB 조회해올 로우 시작 인덱스
        this.total_page = (this.total_count > 0) ? (int) Math.ceil(this.total_count / (double) this.page_size) : 1; // 총 페이지수
        this.page_block_size = pageRequestDTO.getPage_block_size(); // 페이지네이션에서 페이징 최대 한번에 몇 개씩 할지
        this.page_block_start = ((int) Math.floor((((double)page)*((double) 1/page_block_size)))*page_block_size)+1; // 현재 페이징의 시작 번호
        this.page_block_end = (page_block_start + (page_block_size-1)) <  total_page ? (page_block_start + (page_block_size-1)) : total_page;
        this.prev_page_plag = (this.page_block_start > 1); // 이전페이지 있는지 여부(페이지네이션에서 10개씩 이전 가는거)
        this.next_page_plag = (this.total_page > this.page_block_end); // 다음페이지 있는지 여부(페이지네이션에서 10개씩 다음 가는거)
        this.dtolist = dtoList;
        this.search_types=pageRequestDTO.getSearch_types();
        this.search_word = pageRequestDTO.getSearch_word();
        this.linkParams = pageRequestDTO.getLinkParams();
        this.sort_type = pageRequestDTO.getSort_type();

        log.info("pageRequestDTO : {}", pageRequestDTO);
        log.info("dtoList : {}", dtoList);
        log.info("pageRequestDTO : {}", pageRequestDTO);
        log.info("pageRequestDTO : {}", pageRequestDTO);
        log.info("PageResponseDTO END");
        log.info("===============================================");

    }

    public int getTotal_page() {
        return (this.total_count>0 ? (int)Math.ceil(this.total_count/(double)this.page_size):0);
    }

    public int getPage_skip_count(){
        return (this.page) * this.page_size;
    }

    public void setPage_block_start(){
        this.page_block_start = ((int)Math.floor(this.page/(double)this.page_block_size)*this.page_block_size)+1;
    }

    public void setPage_block_end(){
        this.page_block_end = (int)(Math.floor(this.page/(double)this.page_block_size) * this.page_block_size) + this.page_block_size;
    }
}
