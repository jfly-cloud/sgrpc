package com.sgrpc.order.controller;


import com.sgrpc.order.entity.Order;
import com.sgrpc.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ning
 * @since 2021-03-11
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @PostMapping("/create/v1")
    public void create(@RequestBody Order order) {
        orderService.create(order);
    }

    @PostMapping("/update/v1")
    public void update(@RequestBody Order order) {
        orderService.update(order.getUserId(), order.getMoney(), order.getStatus());
    }

}

