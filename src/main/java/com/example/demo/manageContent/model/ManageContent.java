package com.example.demo.manageContent.model;

import com.example.demo.collection.model.Collection;
import com.example.demo.content.model.Content;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "MANAGE_CONTENT")
public class ManageContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Content_id")
    private Content content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Collection_id")
    private Collection collection;
}
