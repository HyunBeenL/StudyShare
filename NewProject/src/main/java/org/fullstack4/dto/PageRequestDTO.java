package org.fullstack4.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.net.URLEncoder;
import java.time.LocalDate;

@Log4j2
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageRequestDTO {
    @Builder.Default
    @PositiveOrZero
    @Min(value=0)
    private int total_count=0;
    @Builder.Default
    @PositiveOrZero
    @Min(value=0)
    private int page=0;
    @Builder.Default
    @PositiveOrZero
    @Min(value=1)
    private int page_size=10;
    @Builder.Default
    @PositiveOrZero
    @Min(value=1)
    private int total_page=1;
    @Builder.Default
    @PositiveOrZero
    @Min(value=0)
    private int page_skip_count=0;
    @Builder.Default
    @PositiveOrZero
    @Min(value=1)
    private int page_block_size=10;
    @Builder.Default
    @PositiveOrZero
    @Min(value=1)
    private int page_block_start=1;
    @Builder.Default
    @PositiveOrZero
    @Min(value=1)
    private int page_block_end=1;

    private String search_type;
    @Builder.Default
    private String[] search_types= new String[]{};
    @Builder.Default
    private String search_word ="";
    private String linkParams;
    private String sort_type;
    private LocalDate search_date1;
    private LocalDate search_date2;

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public int getPage_skip_count() {
        return (this.page)*this.page_size;
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
//            sb.append("page="+this.page);
            sb.append("&page_size="+this.page_size);

            if(search_type != null && !search_type.isEmpty()){
                sb.append("&search_type="+this.search_type);
            }
            if(search_word != null && !search_word.isEmpty()){

                sb.append("&search_word="+ URLEncoder.encode(this.search_word));

            }
            if(sort_type != null && !sort_type.isEmpty()){
                sb.append("&sort_type="+URLEncoder.encode(sort_type));
            }
            if(search_date1 != null && search_date2 !=null){
                sb.append("&search_date1="+URLEncoder.encode(String.valueOf(search_date1)));
                sb.append("&search_date2="+URLEncoder.encode(String.valueOf(search_date2)));
            }
            linkParams = sb.toString();
        }
        return this.linkParams;
    }


}
