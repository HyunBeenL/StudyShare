package org.fullstack4.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="std_user")
public class MemberEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int user_idx;
    private String user_name;
    @Column(name = "user_id", unique = true)
    private String userId;
    private String user_pwd;
    private String user_phone;
    private String user_phone2;
    private String user_phone3;
    @Column(name = "user_email1")
    private String userEmail1;
    @Column(name = "user_email2")
    private String userEmail2;
    @Column(name = "logincount",nullable = true,insertable = false,updatable = true)
    private int logincount;
    @Column(name = "lastlogin_date",nullable = true,insertable = false,updatable = true)
    private LocalDateTime lastlogin_date;

}
