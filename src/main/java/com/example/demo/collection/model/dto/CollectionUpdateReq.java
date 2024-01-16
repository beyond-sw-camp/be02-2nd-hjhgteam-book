package com.example.demo.collection.model.dto;


import com.example.demo.content.model.Content;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CollectionUpdateReq {
    Long id;
    String collectionTitle;
}
