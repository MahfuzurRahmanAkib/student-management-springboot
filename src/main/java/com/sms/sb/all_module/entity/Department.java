package com.sms.sb.all_module.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "departments")
public class Department extends BaseEntity {
    @Column(name = "code", length = 100, unique = true)
    private String code;
    @Column(name = "name", length = 100)
    private String name;

    @OneToMany(mappedBy = "department", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Teacher> teacherList = new ArrayList<>();

    @OneToMany(mappedBy = "department", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Student> studentList = new ArrayList<>();

    @OneToMany(mappedBy = "department", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Subject> subjectList = new ArrayList<>();

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Department)) return false;
        return Objects.nonNull(this.getId()) && Objects.equals(this.getId(), (((Department) object).getId()));
    }

    @Override
    public int hashCode() {
        if (Objects.isNull(this.getId())) {
            return this.getClass().hashCode();
        }
        return this.getId().hashCode();
    }
}
