package com.sms.sb.all_module.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subjects")
public class Subject extends BaseEntity {
    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "code", length = 100, unique = true)
    private String code;

    @Column(name = "department_id", insertable = false, updatable = false)
    private Long departmentId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Subject)) return false;
        return Objects.nonNull(this.getId()) && Objects.equals(this.getId(), (((Subject) object).getId()));
    }

    @Override
    public int hashCode() {
        if (Objects.isNull(this.getId())) {
            return this.getClass().hashCode();
        }
        return this.getId().hashCode();
    }
}
