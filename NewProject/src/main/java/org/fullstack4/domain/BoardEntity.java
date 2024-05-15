package org.fullstack4.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="std_bbs")
public class BoardEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bbs_idx",unique = true, nullable = false)
    private int bbsIdx;

    @Column(length = 20, nullable = false)
    private String bbs_title;
    @Column(length = 20, nullable = false)
    private String bbs_content;
    @Column(length = 20, nullable = false)
    private String user_id;
    @Column(length = 20, nullable = true)
    private String bbs_file;
    @Column(length = 20, nullable = true)
    private String bbs_tag;
    @Column(name="bbs_good", nullable = true)
    private int bbsGood;
    private String bbs_exposure;
    private LocalDate bbs_duration1;
    private LocalDate bbs_duration2;
    private String bbs_part;

    public void modify(String user_id, String title, String content){
        this.user_id = user_id;
        this.bbs_title = title;
        this.bbs_content = content;
        super.setModify_date(LocalDateTime.now());
    }
}
