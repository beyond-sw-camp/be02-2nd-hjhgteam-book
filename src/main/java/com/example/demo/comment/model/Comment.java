package com.example.demo.comment.model;

import com.example.demo.content.model.Content;
import com.example.demo.member.model.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "COMMENT")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String comment;

    @Column(length = 5)
    private Integer rate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;

    @ManyToOne
    @JoinColumn(name = "content_id")
    @JsonBackReference
    private Content content;
}
