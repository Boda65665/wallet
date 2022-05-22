package com.example.demo.Converters;

import com.example.demo.DTO.OrderDTO;
import com.example.demo.Entiti.Order;

public class OrderConvert {
    public OrderDTO FromOrderInOrderDTO(Order order){
        OrderDTO order_convert = new OrderDTO();
        order_convert.setDate(order.getDate());
        order_convert.setCode(order.getCode());
        order_convert.setComment(order.getComment());
        order_convert.setFrom_adres(order.getFrom());
        order_convert.setId(order.getId());
        order_convert.setTo_adres(order.getTo());
        order_convert.setSumma(order.getSumma());
        return order_convert;
    }
    public Order FromOrderDTOInOrder(OrderDTO order){
        Order order_convert = new Order();
        order_convert.setDate(order.getDate());

        order_convert.setCode(order.getCode());
        order_convert.setComment(order.getComment());
        order_convert.setFrom(order.getFrom_adres());
        order_convert.setId(order.getId());
        order_convert.setTo(order.getTo_adres());
        order_convert.setSumma(order.getSumma());
        return order_convert;
    }
}
