package com.example.demo.Services;

import com.example.demo.Converters.OrderConvert;
import com.example.demo.DTO.OrderDTO;
import com.example.demo.Entiti.Order;
import com.example.demo.Repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImp implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    private OrderConvert orderConvert = new OrderConvert();


    @Override
    public void delete() {
        List<Order> orders = orderRepository.findAll();
        for(Order order : orders) {
            Date creation_date = order.getDate();
            Date delete_date = new Date(creation_date.getTime() + 600000);
            Date new_date = new Date();
            System.out.println(delete_date.getTime());
            if(delete_date.getTime()<new_date.getTime()) {
                orderRepository.delete(order);
            }
        }
    }

    @Override
    public void deleteById(int id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void save(OrderDTO orderDTO) {
        Order order = orderConvert.FromOrderDTOInOrder(orderDTO);
        orderRepository.save(order);

    }

    @Override
    public OrderDTO findById(int id) {
        return orderConvert.FromOrderInOrderDTO(orderRepository.findById(id));
    }

    @Override
    public List<OrderDTO> findByFrom(String from) {
        List<Order> orders = orderRepository.findAllByFrom(from);
        List<OrderDTO> dtoList = new ArrayList<>();
        for (Order order : orders){
            dtoList.add(orderConvert.FromOrderInOrderDTO(order));
        }
        return dtoList;
    }
}
