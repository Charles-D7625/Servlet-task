package com.example.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.classes.OrderDetail;
import com.example.dto.OrderDetailDTO;

@Mapper
public interface OrderDetailMapper {

    OrderDetailMapper INSTANCE = Mappers.getMapper(OrderDetailMapper.class);

    OrderDetailDTO orderDetailToOrderDetailDTO(OrderDetail orderDetail);
    OrderDetail orderDetailDTOToOrderDetail(OrderDetailDTO orderDetailDTO);
}
