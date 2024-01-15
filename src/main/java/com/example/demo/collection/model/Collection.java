package com.example.demo.collection.model;

import com.example.demo.manageContent.model.ManageContent;
import com.example.demo.member.model.Member;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COLLECTION")
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String title;

    private Date createdAt;
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "collection")
    private List<ManageContent> manageContents = new ArrayList<>();
}
