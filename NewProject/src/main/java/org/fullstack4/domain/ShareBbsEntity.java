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
    private int bbs_idx;
    private String request_id;
    private String request_name;
    private String response_id;
    private String response_name;
    private LocalDateTime send_date;
}
