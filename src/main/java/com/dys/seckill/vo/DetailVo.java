package com.dys.seckill.vo;

import com.dys.seckill.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailVo {

    private User user;
    private GoodsVo goodsVo;
    private int seckilStatus;
    private int remainSeconds;

}
