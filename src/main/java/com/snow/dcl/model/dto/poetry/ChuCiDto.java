package com.snow.dcl.model.dto.poetry;

import lombok.Data;

/**
 * 楚辞Dto
 */
@Data
public class ChuCiDto {
    private String title;
    private String section;
    private String author;
    private String[] content;
}
