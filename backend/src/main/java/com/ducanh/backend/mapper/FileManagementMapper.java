package com.ducanh.backend.mapper;

import com.ducanh.backend.dto.FileManagementDto;
import com.ducanh.backend.model.FileManagement;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FileManagementMapper {
    FileManagementDto toDto(FileManagement fileManagement);
    List<FileManagementDto> toListDto(List<FileManagement> fileManagementList);
}
