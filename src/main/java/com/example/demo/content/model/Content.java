package com.example.demo.content.model;

import com.example.demo.comment.model.Comment;
import com.example.demo.livetalk.model.Livetalk;
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

    private byte classify;

    @OneToMany(mappedBy = "content")
    @JsonManagedReference
    private List<Livetalk> livetalkList = new ArrayList<>();



    @OneToMany(mappedBy = "content")
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();

}
