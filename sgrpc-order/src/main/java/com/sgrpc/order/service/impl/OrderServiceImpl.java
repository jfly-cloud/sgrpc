package com.sgrpc.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sgrpc.order.entity.Order;
import com.sgrpc.order.grpc.AccountApi;
import com.sgrpc.order.grpc.StorageApi;
import com.sgrpc.order.mapper.OrderMapper;
import com.sgrpc.order.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ning
 * @since 2021-03-11
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private StorageApi storageApi;
    @Autowired
    private AccountApi accountApi;

    /**
     * 创建订单
     * @param order
     * @return
     * 测试结果：
     * 1.添加本地事务：仅仅扣减库存
     * 2.不添加本地事务：创建订单，扣减库存
     */
    @Override
    @GlobalTransactional
    public void create(Order order) {
        LOGGER.info("------->交易开始");
        //本地方法
        baseMapper.insert(order);
        //远程方法 扣减库存
        storageApi.sdecrease(order.getProductId(),order.getCount());
        //远程方法 扣减账户余额
        LOGGER.info("------->扣减账户开始order中");
        accountApi.adecrease(order.getUserId(),order.getMoney());
        LOGGER.info("------->扣减账户结束order中");
        LOGGER.info("------->交易结束");
    }

    /**
     * 修改订单状态
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long userId,BigDecimal money,Integer status) {
        LOGGER.info("修改订单状态，入参为：userId={},money={},status={}",userId,money,status);
        Order order=new Order();
        order.setUserId(userId);
        order.setMoney(money);
        order.setStatus(status);
        baseMapper.update(order,new QueryWrapper<Order>().eq("user_id",userId));
        //int i=1/0;
    }
}
