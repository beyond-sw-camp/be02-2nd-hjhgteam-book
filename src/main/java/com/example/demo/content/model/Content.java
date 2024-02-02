package com.example.demo.content.model;

import com.example.demo.category.model.Category;
import com.example.demo.collection.model.Collection;
import com.example.demo.comment.model.Comment;
import com.example.demo.writer.model.Writer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CONTENT")
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String name;

    //Boolean으로 수정
    private Boolean classify;


    @OneToMany(mappedBy = "content")
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "Category_id")
    private Category categoryId;

    @ManyToOne
    @JoinColumn(name = "Writer_id")
    private Writer writerId;

    @OneToOne(mappedBy = "content", fetch = FetchType.EAGER)
    private ContentImage contentImages;


    // TODO 확인
    //  manage_content 삭제로 collection과 직접 OneToMany추가
    @OneToMany(mappedBy = "contentInCollect", orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private List<Collection> collectionList = new ArrayList<>();
}
