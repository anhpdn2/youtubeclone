package com.ducanh.backend.service;

import com.ducanh.backend.dto.BasicResponseDto;
import com.ducanh.backend.dto.FileManagementDto;
import com.ducanh.backend.dto.UploadFileVM;
import com.ducanh.backend.mapper.FileManagementMapper;
import com.ducanh.backend.model.FileManagement;
import com.ducanh.backend.repository.FileManagementRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FileService {
    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private final FileManagementRepository fileManagementRepository;

    @Autowired
    private final FileManagementMapper fileManagementMapper;

    public void save(MultipartFile file) throws FileUploadException {
        try {
            Path root = Paths.get(uploadPath);
            Path resolve = root.resolve(file.getOriginalFilename());
            if (resolve.toFile()
                    .exists()) {
                throw new FileUploadException("File already exists: " + file.getOriginalFilename());
            }
            Files.copy(file.getInputStream(), resolve);
        } catch (Exception e) {
            throw new FileUploadException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public FileManagement uploadSingleFile(MultipartFile file) {
        try {
            String ext = FilenameUtils.getExtension(file.getOriginalFilename());
            LocalDateTime now = LocalDateTime.now();
            String subDir = now.getYear() + "/" + now.getMonth() + "/" + now.getDayOfMonth();
            Path parentDir = Paths.get(uploadPath + subDir);
            if (Files.notExists(parentDir))
                Files.createDirectories(parentDir);
            String randomFileName = UUID.randomUUID().toString();
            String filePath = FilenameUtils.separatorsToSystem(parentDir + "/" + randomFileName + "." + ext);
//            String urlViewPublicFile = staticServeUrl + subDir + "/" + randomFileName + "." + ext;
            Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
            FileManagement fileManagement = new FileManagement();
            fileManagement.setFileExt(ext);
            fileManagement.setDeleted(false);
            fileManagement.setContentType(file.getContentType());
            fileManagement.setName(file.getOriginalFilename());
            fileManagement.setPath(filePath);
            fileManagement.setSize(file.getSize());
//            fileManagement.setCreatedBy(principal.getUserId());
            fileManagement.setCreatedDate(new Date());
//            fileManagement.setViewLink(urlViewPublicFile);
            fileManagementRepository.save(fileManagement);
            return fileManagement;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public BasicResponseDto<List<FileManagementDto>> uploadMultipleFile(UploadFileVM uploadFileVM) {
        MultipartFile[] files = uploadFileVM.getFiles();
        List<FileManagementDto> responses = new ArrayList<>();
        for (MultipartFile file : files) {
            FileManagement fileUploaded = uploadSingleFile(file);
            if (fileUploaded != null) {
                responses.add(fileManagementMapper.toDto(fileUploaded));
            }
        }
        return BasicResponseDto.ok(responses);
    }

    @Transactional
    public void viewFileUpload(String fileId, HttpServletResponse httpServletResponse) throws IOException {
        Optional<FileManagement> optionalFileManagement = fileManagementRepository.findById(fileId);
        if (optionalFileManagement.isEmpty()) {
            httpServletResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return;
        } else {
            FileManagement fileManagement = optionalFileManagement.get();
            String filePath = fileManagement.getPath();
            Path path = Paths.get(filePath);
            if (Files.notExists(path)) {
                httpServletResponse.setStatus(HttpStatus.NOT_FOUND.value());
                return;
            }
            httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + fileManagement.getName());
            httpServletResponse.setContentType(fileManagement.getContentType());
            try (InputStream bis = Files.newInputStream(path);
                 OutputStream bos = httpServletResponse.getOutputStream()) {
                byte[] bytes = new byte[1024];
                int byteRead;
                while ((byteRead = (bis.read(bytes))) != -1)
                    bos.write(bytes, 0, byteRead);
            }
        }
    }

}
