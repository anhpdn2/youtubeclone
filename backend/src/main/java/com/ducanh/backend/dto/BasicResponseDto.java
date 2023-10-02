package com.ducanh.backend.dto;

import com.ducanh.backend.utils.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class BasicResponseDto<T> implements Serializable {
    private int status;
    private String message;
    private T data;

    public BasicResponseDto() {
    }

    public static <T> BasicResponseDto<T> ok(T data) {
        BasicResponseDto<T> basicResponseDto = new BasicResponseDto<>();
        basicResponseDto.setData(data);
        basicResponseDto.setStatus(ErrorCode.SUCC_200);
        basicResponseDto.setMessage("Success");
        return basicResponseDto;
    }

    public static <T> BasicResponseDto<T> ok() {
        BasicResponseDto<T> basicResponseDto = new BasicResponseDto<>();
        basicResponseDto.setStatus(ErrorCode.SUCC_200);
        basicResponseDto.setMessage("Success");
        return basicResponseDto;
    }

    public static <T> BasicResponseDto<T> failed(T data) {
        BasicResponseDto<T> basicResponseDto = new BasicResponseDto<>();
        basicResponseDto.setData(data);
        basicResponseDto.setStatus(ErrorCode.ERR_400);
        basicResponseDto.setMessage("Success");
        return basicResponseDto;
    }

    public static <T> BasicResponseDto<T> failed() {
        BasicResponseDto<T> basicResponseDto = new BasicResponseDto<>();
        basicResponseDto.setStatus(ErrorCode.ERR_400);
        basicResponseDto.setMessage("Error");
        return basicResponseDto;
    }
}
