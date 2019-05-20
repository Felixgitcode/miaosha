package com.imooc.miaosha.service;

import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Felix
 * @date 2019/5/19 21:22
 */
@Service
public class MiaoshaService {
    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    //减库存，下订单，写入秒杀订单（事务级别：一个成功其他也成功，反之亦然）
    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        //减库存
        goodsService.reduceStock(goods);
        //下订单 order_info,miaosha_order
        return orderService.createOrder(user,goods);
    }
}
