package com.dys.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dys.seckill.exception.GlobalException;
import com.dys.seckill.mapper.OrderMapper;
import com.dys.seckill.pojo.Order;
import com.dys.seckill.pojo.SeckillGoods;
import com.dys.seckill.pojo.SeckillOrder;
import com.dys.seckill.pojo.User;
import com.dys.seckill.service.IGoodsService;
import com.dys.seckill.service.IOrderService;
import com.dys.seckill.service.ISeckillGoodsService;
import com.dys.seckill.service.ISeckillOrderService;
import com.dys.seckill.utils.MD5Util;
import com.dys.seckill.utils.UUIDUtil;
import com.dys.seckill.vo.GoodsVo;
import com.dys.seckill.vo.OrderDetailVo;
import com.dys.seckill.vo.RespBean;
import com.dys.seckill.vo.RespBeanEnum;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dys
 * @since 2021-07-06
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    ISeckillGoodsService seckillGoodsService;

    @Autowired
    ISeckillOrderService seckillOrderService;

    @Autowired
    IGoodsService goodsService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    OrderMapper orderMapper;

    @Override
    @Transactional
    public Order seckill(User user, GoodsVo goods) {
        SeckillGoods seckillGoods = seckillGoodsService.getOne(new QueryWrapper<SeckillGoods>().eq("goods_id", goods.getId()));
        seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
        boolean result = seckillGoodsService.update(new UpdateWrapper<SeckillGoods>().setSql("stock_count = stock_count-1").eq("goods_id", goods.getId()).gt("stock_count", 0));
        if (seckillGoods.getStockCount() < 1){
            //判断是否还有库存
            redisTemplate.opsForValue().set("isStockEmpty:" + goods.getId(), "0");
            return null;
        }
        Order order = new Order();
        order.setUserId(user.getId());
        order.setGoodsId(goods.getId());
        order.setDeliveryAddrId(0L);
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(seckillGoods.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        orderMapper.insert(order);
        //生成秒杀订单
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setGoodsId(goods.getId());
        seckillOrderService.save(seckillOrder);
        redisTemplate.opsForValue().set("order:"+user.getId()+":"+goods.getId(), seckillOrder);
        return order;
    }

    @Override
    public OrderDetailVo detail(Long orderId) {
        if (orderId == null){
            throw new GlobalException(RespBeanEnum.ORDER_NOT_EXIST);
        }
        Order order = orderMapper.selectById(orderId);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(order.getGoodsId());
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setGoodsVo(goodsVo);
        orderDetailVo.setOrder(order);
        return orderDetailVo;
    }

    @Override
    public String createPath(User user, Long goodsId) {
        String str = MD5Util.md5(UUIDUtil.uuid() + "123456");
        redisTemplate.opsForValue().set("seckillPath:" + user.getId() + ":" + goodsId, str, 60, TimeUnit.SECONDS);
        return str;
    }

    @Override
    public boolean checkPath(User user, Long goodsId, String path) {
        if (user == null || StringUtils.isEmpty(path)){
            return false;
        }
        String redisPath = (String) redisTemplate.opsForValue().get("seckillPath:" + user.getId() + ":" + goodsId);
        return path.equals(redisPath);
    }

    @Override
    public boolean checkCaptcha(User user, Long goodsId, String captcha) {
        if (user == null || StringUtils.isEmpty(captcha)){
            return false;
        }
        String redisCaptcha = (String) redisTemplate.opsForValue().get("captcha:" + user.getId() + ":" + goodsId);
        return captcha.equals(redisCaptcha);
    }
}
