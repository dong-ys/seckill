package com.dys.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dys.seckill.pojo.Goods;
import com.dys.seckill.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dys
 * @since 2021-07-06
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    List<GoodsVo> findGoods();

    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
