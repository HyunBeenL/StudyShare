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
@Table(name="std_sharebbs")
public class ShareBbsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int idx;
    @Column (name="bbs_idx")
    private int bbsIdx;
    @Column (name="request_id")
    private String requestId;
    private String request_name;
    @Column (name="response_id")
    private String responseId;
    private String response_name;
    private LocalDateTime send_date;
}
