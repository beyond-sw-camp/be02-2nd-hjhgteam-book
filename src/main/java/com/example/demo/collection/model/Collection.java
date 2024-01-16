package com.example.demo.collection.model;

import com.example.demo.manageContent.model.ManageContent;
import com.example.demo.member.model.Member;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.sql.Timestamp;
import java.util.Date;


import java.util.ArrayList;
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


    @Column(nullable = true, length = 200)
    private String collectionTitle;



    @Column(updatable = false, nullable = false)
    private Date createdAt;
    private Date updatedAt;


    @PrePersist
    void createdAt() {
        this.createdAt = Timestamp.from(Instant.now());
        this.updatedAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Member_id")
    private Member member;

    @OneToMany(mappedBy = "collection", fetch = FetchType.LAZY)
    private List<ManageContent> manageContentList = new ArrayList<>();
}
