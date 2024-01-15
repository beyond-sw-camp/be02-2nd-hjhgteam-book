package com.example.demo.livetalk.model;

import com.example.demo.chat.model.Chat;
import com.example.demo.content.model.Content;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "LIVETALK")
public class Livetalk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String contentTitle;

    private String chatFrom;
    private Timestamp time;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;

    @OneToMany(mappedBy = "livetalk")
    private List<Chat> chats = new ArrayList<>();
}
