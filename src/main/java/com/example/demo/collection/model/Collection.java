package com.example.demo.collection.model;

import com.example.demo.content.model.Content;
import com.example.demo.member.model.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

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
    @JsonIgnore
    private Member member;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Content_id")
    @JsonIgnore
    private Content contentInCollect;

}
