package com.dys.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dys.seckill.pojo.Goods;
import com.dys.seckill.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dys
 * @since 2021-07-06
 */
public interface IGoodsService extends IService<Goods> {

    /**
     * 获取商品列表
     * @return
     */
    List<GoodsVo> findGoodsVo();

    /**
     * 获取商品详情
     * @param goodsId
     * @return
     */
    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
