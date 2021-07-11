package com.dys.seckill.controller;


import com.dys.seckill.pojo.User;
import com.dys.seckill.service.IOrderService;
import com.dys.seckill.vo.OrderDetailVo;
import com.dys.seckill.vo.RespBean;
import com.dys.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dys
 * @since 2021-07-06
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @RequestMapping("/detail")
    @ResponseBody
    public RespBean detail(User user, Long orderId){
        if (user == null){
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        OrderDetailVo orderDetail = orderService.detail(orderId);
        return RespBean.success(orderDetail);
    }
}
