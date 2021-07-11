package com.dys.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dys.seckill.pojo.Order;
import com.dys.seckill.pojo.User;
import com.dys.seckill.vo.GoodsVo;
import com.dys.seckill.vo.OrderDetailVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dys
 * @since 2021-07-06
 */
public interface IOrderService extends IService<Order> {

    /**
     * 秒杀
     * @param user
     * @param goods
     * @return
     */
    Order seckill(User user, GoodsVo goods);

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    OrderDetailVo detail(Long orderId);

    /**
     * 获取秒杀地址
     * @param user
     * @param goodsId
     * @return
     */
    String createPath(User user, Long goodsId);

    /**
     * 检验秒杀地址
     * @param user
     * @param goodsId
     * @return
     */
    boolean checkPath(User user, Long goodsId, String path);

    /**
     * 校验验证码
     * @param user
     * @param goodsId
     * @param captcha
     * @return
     */
    boolean checkCaptcha(User user, Long goodsId, String captcha);
}
