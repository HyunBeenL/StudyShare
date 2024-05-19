package org.fullstack4.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {
    private int user_idx;
    private String user_name;
    private String userId;
    private String user_pwd;
    private String user_phone;
    private String user_phone2;
    private String user_phone3;
    private String userEmail1;
    private String userEmail2;
    private LocalDateTime regDate;
    private LocalDateTime modify_date;
    private int logincount;
    private LocalDateTime lastlogin_date;

}
