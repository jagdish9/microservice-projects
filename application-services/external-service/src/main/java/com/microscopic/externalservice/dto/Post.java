package com.microscopic.externalservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class Post {

    private String userId;
    private String id;
    private String title;
    private String body;
}
