package com.sms.sb.all_module.entity;

import com.sms.sb.all_module.payload.constant.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "students")
public class Student extends BaseEntity{
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(name = "gender", nullable = false)
    @Enumerated
    private Gender gender;

    @Column(name = "email", length = 250)
    private String email;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "department_id", insertable = false, updatable = false)
    private Long departmentId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;
}
