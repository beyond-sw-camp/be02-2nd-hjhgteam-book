package com.example.demo.emailcert.adapter.out.persistence;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "EMAIL_AUTHENTICATION")
public class EmailCertJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String email;
    @Column(length = 50)
    private String uuid;
}
