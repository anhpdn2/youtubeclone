package com.ducanh.backend.controller;

import com.ducanh.backend.dto.*;
import com.ducanh.backend.model.UploadResponseMessage;
import com.ducanh.backend.service.FileService;
import com.ducanh.backend.service.VideoService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/files")
@RequiredArgsConstructor
@CrossOrigin(origins= "http://localhost:4200" )
public class FilesController {
    private final FileService fileService;
    private final VideoService videoService;

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BasicResponseDto<List<FileManagementDto>>> uploadMultipleFile(@ModelAttribute @Valid UploadFileVM uploadFileVM) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(fileService.uploadMultipleFile(uploadFileVM));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UploadResponseMessage> uploadSingleFile(@RequestParam("file") MultipartFile file) throws FileUploadException {
        fileService.save(file);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new UploadResponseMessage("Uploaded the file successfully: " + file.getOriginalFilename()));
    }

    @GetMapping("/view")
    @ResponseStatus(HttpStatus.OK)
    public void viewFile(@RequestParam("filePath") String filePath, HttpServletResponse httpServletResponse) throws IOException {
        fileService.viewFileUpload(filePath, httpServletResponse);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public VideoDto editVideoMetaData(@RequestBody VideoDto videoDto) {
        videoService.editVideo(videoDto);
        return null;
    }

}
