package com.dys.seckill.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import com.dys.seckill.pojo.SeckillMessage;
import com.dys.seckill.pojo.SeckillOrder;
import com.dys.seckill.pojo.User;
import com.dys.seckill.service.IGoodsService;
import com.dys.seckill.service.IOrderService;
import com.dys.seckill.service.impl.GoodsServiceImpl;
import com.dys.seckill.utils.JsonUtil;
import com.dys.seckill.vo.GoodsVo;
import com.dys.seckill.vo.RespBean;
import com.dys.seckill.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MQReceiver {

    @Autowired
    IGoodsService goodsService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    IOrderService orderService;

    /**
     * 下单操作
     */
    @RabbitListener(queues = "seckillQueue")
    public void receive(String message){
        log.info("接受的消息: " + message);
        SeckillMessage seckillMessage = JsonUtil.jsonStr2Object(message, SeckillMessage.class);
        Long goodsId = seckillMessage.getGoodsId();
        User user = seckillMessage.getUser();
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        if (goodsVo.getStockCount() < 1){
            return;
        }
        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("order:"+user.getId()+":" + goodsId);
        if (seckillOrder != null){
            return;
        }
        orderService.seckill(user, goodsVo);
    }

    /*@RabbitListener(queues = "queue")
    public void receive(Object msg){
        log.info("接收消息: " + msg);
    }

    @RabbitListener(queues = "queue_fanout01")
    public void receive01(Object msg){
        log.info("QUEUE01接收消息: " + msg);
    }

    @RabbitListener(queues = "queue_fanout02")
    public void receive02(Object msg){
        log.info("QUEUE02接收消息: " + msg);
    }

    @RabbitListener(queues = "queue_direct01")
    public void receive03(Object msg){
        log.info("QUEUE01接收消息: " + msg);
    }

    @RabbitListener(queues = "queue_direct02")
    public void receive04(Object msg){
        log.info("QUEUE02接收消息: " + msg);
    }*/
}
