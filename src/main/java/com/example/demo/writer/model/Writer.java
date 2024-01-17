package com.example.demo.writer.model;

import com.example.demo.content.model.Content;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "WRITER")
public class Writer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @OneToMany(mappedBy = "writerId", orphanRemoval = true)
//    @JsonManagedReference
    private List<Content> contentList = new ArrayList<>();



}
