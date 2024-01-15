package com.example.demo.member.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "EMAIL_AUTHENTICATION")
public class EmailAuthentication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String email;
    @Column(length = 50)
    private String uuid;
    @Column(length = 200)
    private String jwt;

}
