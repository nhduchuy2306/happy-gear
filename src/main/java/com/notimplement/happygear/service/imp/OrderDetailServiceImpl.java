package com.notimplement.happygear.service.imp;

import com.notimplement.happygear.entities.OrderDetail;
import com.notimplement.happygear.entities.Product;
import com.notimplement.happygear.model.dto.CartItemDto;
import com.notimplement.happygear.model.dto.OrderDetailDto;
import com.notimplement.happygear.model.mapper.Mapper;
import com.notimplement.happygear.repositories.OrderDetailRepository;
import com.notimplement.happygear.repositories.OrderRepository;
import com.notimplement.happygear.repositories.ProductRepository;
import com.notimplement.happygear.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderDetailDto create(OrderDetailDto orderDetailDto) {
        if (orderDetailDto != null) {
            OrderDetail orderDetail = toOrderDetail(orderDetailDto);
            orderDetailRepository.save(orderDetail);
            return Mapper.toOrderDetailDto(orderDetail);
        }
        return null;
    }

    @Override
    public Double getCartAmount(List<CartItemDto> list) {
        Double totalCartAmount = 0d;
        Double singleCartAmount = 0d;
        int availableQuantity = 0;
        for(var o : list){
            int productId = o.getProductId();
            Product product = productRepository.findByProductId(productId);
            if(product!=null){
                if(product.getQuantity() < o.getQuantity()){
                    log.info("Dont have enough quantity of product");
                    return 0d;
                }
                else{
                    singleCartAmount = o.getQuantity() * product.getPrice();
                    availableQuantity = product.getQuantity() - o.getQuantity();
                }
                totalCartAmount += singleCartAmount;
                product.setQuantity(availableQuantity);
                productRepository.save(product);
            }
        }
        return totalCartAmount;
    }

    @Override
    public List<OrderDetail> getByOrderId(Integer id) {
        return orderDetailRepository.findAllByOrderId(id);
    }

    private OrderDetail toOrderDetail(OrderDetailDto orderDetailDto) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId(orderDetailDto.getDetailId());
        orderDetail.setPrice(orderDetailDto.getPrice());
        orderDetail.setQuantity(orderDetailDto.getQuantity());
        orderDetail.setStatus(orderDetailDto.getStatus());
        orderDetail.setOrder(orderRepository.findByOrderId(orderDetailDto.getOrderId()));
        orderDetail.setProduct(productRepository.findByProductId(orderDetailDto.getProductId()));
        return orderDetail;
    }
}
