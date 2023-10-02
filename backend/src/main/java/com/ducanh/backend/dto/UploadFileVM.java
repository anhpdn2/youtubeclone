package com.ducanh.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileVM {
    @NotNull(message = "Tệp không được trống")
    private MultipartFile[] files;
}
