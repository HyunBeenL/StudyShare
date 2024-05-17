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
    private String userId;
    private LocalDateTime regDate;
    private String bbs_file;
    private String fileorgname;
    private String bbsExposure;
    private LocalDate bbsDuration1;
    private LocalDate bbsDuration2;
    private LocalDateTime modify_date;
    private String bbs_tag;
    private String bbs_part;
    private int bbsGood;

    private String requestId;
    private String request_name;
    private String responseId;
    private String response_name;
    private LocalDateTime send_date;
}
