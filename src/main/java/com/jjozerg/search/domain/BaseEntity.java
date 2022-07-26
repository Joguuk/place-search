package com.jjozerg.search.domain;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * packageName : com.jjozerg.search.domain
 * fileName : BaseEntity
 * author : joguk
 * description : BaseEntity
 */
@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@DynamicInsert
public abstract class BaseEntity {
    @LastModifiedDate
    @Column(name = "data_input_dttm", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataInputDttm;

    @LastModifiedDate
    @Column(name = "data_update_dttm", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataUpdateDttm;
}
