package com.example.demo.manageCategory.model;

import com.example.demo.category.model.Category;
import com.example.demo.content.model.Content;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "MANAGE_CATEGORY")
public class ManageCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;
}
