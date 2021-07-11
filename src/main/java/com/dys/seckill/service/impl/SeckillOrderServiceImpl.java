package com.dys.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dys.seckill.mapper.SeckillOrderMapper;
import com.dys.seckill.pojo.SeckillOrder;
import com.dys.seckill.pojo.User;
import com.dys.seckill.service.ISeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dys
 * @since 2021-07-06
 */
@Service
public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder> implements ISeckillOrderService {

    @Autowired
    SeckillOrderMapper seckillOrderMapper;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     *
     * @param user
     * @param goodsId
     * @return orderId（1：成功，-1：秒杀失败，0：排队中）
     */
    @Override
    public Long getResult(User user, Long goodsId) {
        SeckillOrder seckillOrder = seckillOrderMapper.selectOne(new QueryWrapper<SeckillOrder>().eq("user_id", user.getId()).eq("goods_id", goodsId));
        if (seckillOrder != null){
            return seckillOrder.getOrderId();
        }
        else if (redisTemplate.hasKey("isStockEmpty:" + goodsId)){
            return -1L;
        } else {
            return 0L;
        }
    }
}
