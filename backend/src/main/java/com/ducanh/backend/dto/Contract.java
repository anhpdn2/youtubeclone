package com.ducanh.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contract {
    private String contractCode;
    private String citizenId;
    private String citizenOld;
}
