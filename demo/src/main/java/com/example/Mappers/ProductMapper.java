package com.example.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.Product;
import com.example.DTO.ProductDTO;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDTO productToProductDTO(Product product);
    Product productDTOTProduct(ProductDTO productDTO);
}
