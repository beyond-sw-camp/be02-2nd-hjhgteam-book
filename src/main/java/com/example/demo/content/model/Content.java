package com.example.demo.content.model;

import com.example.demo.comment.model.Comment;
import com.example.demo.livetalk.model.Livetalk;
import com.example.demo.manageCategory.model.ManageCategory;
import com.example.demo.manageContent.model.ManageContent;
import com.example.demo.own.model.Own;
import com.example.demo.rate.model.Rate;
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
    private List<Livetalk> livetalkList = new ArrayList<>();

    @OneToMany(mappedBy = "content")
    private List<ManageContent> manageContents = new ArrayList<>();

    @OneToMany(mappedBy = "content")
    private List<ManageCategory> manageCategories = new ArrayList<>();

    @OneToMany(mappedBy = "content")
    private List<Own> owns = new ArrayList<>();

    @OneToMany(mappedBy = "content")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "content")
    private List<Rate> rates = new ArrayList<>();
}
