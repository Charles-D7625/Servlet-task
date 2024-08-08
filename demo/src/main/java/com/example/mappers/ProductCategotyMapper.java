package com.example.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.classes.ProductCategory;
import com.example.dto.ProductCategoryDTO;

@Mapper
public interface ProductCategotyMapper {

    ProductCategotyMapper INSTANCE = Mappers.getMapper(ProductCategotyMapper.class);

    ProductCategoryDTO productCategoryToProductCategoryDTO(ProductCategory productCategory);
    ProductCategory productCategoryDTOToProductCategory(ProductCategoryDTO productCategoryDTO);

}
