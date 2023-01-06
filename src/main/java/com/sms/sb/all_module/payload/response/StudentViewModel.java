package com.sms.sb.all_module.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentViewModel {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
