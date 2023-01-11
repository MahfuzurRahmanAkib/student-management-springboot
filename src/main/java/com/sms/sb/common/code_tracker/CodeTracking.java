package com.sms.sb.common.code_tracker;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "code_tracking")
public class CodeTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private CodeType codeType;
    @Column(columnDefinition = "bigint default 0", nullable = false)
    private Long codeLastSequence;
}
