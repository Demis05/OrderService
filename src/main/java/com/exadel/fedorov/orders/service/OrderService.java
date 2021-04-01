package com.exadel.fedorov.orders.service;

import com.exadel.fedorov.orders.domain.Order;
import com.exadel.fedorov.orders.domain.OrderDetail;
import com.exadel.fedorov.orders.domain.OrderStatus;
import com.exadel.fedorov.orders.dto.dto_request.ReqOrderItemDTO;
import com.exadel.fedorov.orders.dto.dto_response.RespOrderDTO;
import com.exadel.fedorov.orders.dto.dto_response.RespOrderItemDTO;
import com.exadel.fedorov.orders.repository.OrderDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private static final int ZERO = 0;

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private ModelMapper modelMapper;

    public List<RespOrderDTO> findAll() {
        return convertOrdersToRespOrderDTOs(orderDAO.findAllOrders());
    }

    public Optional<RespOrderDTO> findById(Long id) {
        Order order = orderDAO.findById(id);
        List<OrderDetail> items = orderDAO.findItemsByOrderId(id);
        return Optional.of(fillRespOrderDto(order, items));
    }

    public void update(Long id, String status, String statusDescription) {
        orderDAO.update(id, OrderStatus.valueOf(status), statusDescription);
    }

    public void deleteById(Long id) {
        orderDAO.deleteById(id);
    }

    public void createOrder(List<ReqOrderItemDTO> orderItems, String clientName, String statusDescription) {
        Order order = new Order(clientName, getTotalPrice(orderItems), statusDescription);

        List<OrderDetail> details = convertReqOrderItemDTOsToOrderDetails(orderItems);
        orderDAO.createOrderRecordWithProcedure(order, details);
    }

    private BigDecimal getTotalPrice(List<ReqOrderItemDTO> orderItems) {
        BigDecimal total = new BigDecimal(ZERO);
        for (ReqOrderItemDTO item : orderItems) {
            total = total.add(item.getPositionPrice());
        }
        return total;
    }

    private List<RespOrderDTO> convertOrdersToRespOrderDTOs(List<Order> orders) {
        List<RespOrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orders) {
            List<OrderDetail> details = orderDAO.findItemsByOrderId(order.getId());
            orderDTOS.add(fillRespOrderDto(order, details));
        }
        return orderDTOS;
    }

    private RespOrderDTO fillRespOrderDto(Order order, List<OrderDetail> details) {
        return new RespOrderDTO(
                convertOrderDetailsToRespOrderItemDTOs(details),
                order.getTotalPrice(),
                order.getClientName(),
                order.getStatus().toString(),
                order.getStatusDescription(),
                order.getTime().toLocalDateTime());
    }

    private List<RespOrderItemDTO> convertOrderDetailsToRespOrderItemDTOs(List<OrderDetail> details) {
        return details.stream()
                .map(orderDetail -> modelMapper.map(orderDetail, RespOrderItemDTO.class))
                .collect(Collectors.toList());
    }

    private List<OrderDetail> convertReqOrderItemDTOsToOrderDetails(List<ReqOrderItemDTO> dtoList) {
        return dtoList.stream()
                .map(reqOrderItemDTO -> modelMapper.map(reqOrderItemDTO, OrderDetail.class))
                .collect(Collectors.toList());
    }

}