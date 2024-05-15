package org.fullstack4.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
public class BaseEntity {

    @CreatedDate
    @Column(name="reg_date", updatable = false)
    private LocalDateTime regDate;

    @Column(name = "modify_date",nullable = true,insertable = false,updatable = true)
    private LocalDateTime modify_date;

    @PrePersist
    protected void onCreate() {
        regDate = LocalDateTime.now();

    }
    public void setModify_date(LocalDateTime modify_date){
        this.modify_date = LocalDateTime.now();
    }
}

