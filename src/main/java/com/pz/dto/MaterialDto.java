package com.pz.dto;

import com.pz.model.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Builder
@Setter
@Getter
public class MaterialDto {

    private Long id;

    private String title;

    private String description;

    private Integer price;

    private Long createdBy;

    private Long updatedBy;

    private CategoryDto category;

    private String photo;
}
