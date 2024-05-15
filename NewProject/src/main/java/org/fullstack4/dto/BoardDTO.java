package org.fullstack4.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDTO {
    private int idx;
    private int bbsIdx;
    private String bbs_title;
    private String bbs_content;
    private String user_id;
    private LocalDateTime regDate;
    private String bbs_file;
    private String bbs_exposure;
    private LocalDate bbs_duration1;
    private LocalDate bbs_duration2;
    private LocalDateTime modify_date;
    private String bbs_tag;
    private String bbs_part;
    private int bbsGood;

    private String request_id;
    private String request_name;
    private String response_id;
    private String response_name;
    private LocalDateTime send_date;
}
