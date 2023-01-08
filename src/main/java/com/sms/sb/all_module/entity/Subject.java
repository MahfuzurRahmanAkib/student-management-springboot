package com.sms.sb.all_module.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subjects")
public class Subject extends BaseEntity {
    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "code", length = 100)
    private String code;
}
