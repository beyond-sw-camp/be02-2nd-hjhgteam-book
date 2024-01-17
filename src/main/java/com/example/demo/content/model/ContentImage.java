package com.example.demo.content.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class ContentImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filename;

    @OneToOne
//    @JsonIgnore
    @JoinColumn(name = "Content_id")
    private Content content;
}
