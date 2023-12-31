package com.ducanh.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class FileManagementDto implements Serializable {
    private String id;
    private String name;
    private String viewLink;
}

