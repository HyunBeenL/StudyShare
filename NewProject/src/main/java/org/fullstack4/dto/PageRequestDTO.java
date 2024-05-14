package org.fullstack4.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.net.URLEncoder;

@Log4j2
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageRequestDTO {
    private int total_count;
    private int page;
    private int page_size;
    private int total_page;
    private int page_skip_count;
    private int page_block_size;
    private int page_block_start;
    private int page_block_end;

    private String search_type;
    private String[] search_types;
    private String search_word;
    private String linkParams;

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public int getPage_skip_count() {
        return (this.page-1)*this.page_size;
    }
    public String[] getSearch_types() {
        if(search_type  == null || search_type.isEmpty()){
            return null;
        }
        return search_type.split("");
    }

    public PageRequest getPageable(String...props){
        return PageRequest.of(this.page-1, this.page_size, Sort.by(props).descending());
    }

    public String getLinkParams() {
        if(this.linkParams == null || this.linkParams.isEmpty()){
            StringBuilder sb = new StringBuilder();
            sb.append("page="+this.page);
            sb.append("&page_size="+this.page_size);

            if(search_type != null && !search_type.isEmpty()){
                sb.append("&search_type="+this.search_type);
            }
            if(search_word != null && !search_word.isEmpty()){

                sb.append("&search_word="+ URLEncoder.encode(this.search_word));

            }linkParams = sb.toString();
        }
        return this.linkParams;
    }


}
