package com.ducanh.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(value = "file")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileManagement {
    @Id
    private String id;
    private String name;
    private Long size;
    private String path;
    private String fileExt;
    private String contentType;
    private String createdBy;
    private Date createdDate;
    private String updateBy;
    private String updateDate;
    private String viewLink;
    private boolean isDeleted;
}
