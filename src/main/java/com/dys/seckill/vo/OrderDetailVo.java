package com.dys.seckill.vo;

import com.dys.seckill.pojo.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailVo {

    private Order order;
    private GoodsVo goodsVo;
}
