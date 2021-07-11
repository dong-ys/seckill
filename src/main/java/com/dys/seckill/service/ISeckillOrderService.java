package com.dys.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dys.seckill.pojo.SeckillOrder;
import com.dys.seckill.pojo.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dys
 * @since 2021-07-06
 */
public interface ISeckillOrderService extends IService<SeckillOrder> {

    /**
     * 获取秒杀结果
     * @param user
     * @param goodsId
     * @return
     */
    Long getResult(User user, Long goodsId);
}
