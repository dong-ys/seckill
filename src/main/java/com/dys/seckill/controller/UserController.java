package com.dys.seckill.controller;


import com.dys.seckill.pojo.User;
import com.dys.seckill.rabbitmq.MQSender;
import com.dys.seckill.vo.RespBean;
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
 * @since 2021-07-05
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    MQSender mqSender;

    @RequestMapping("/info")
    @ResponseBody
    public RespBean info(User user){
        return RespBean.success(user);
    }

    /*@RequestMapping("/mq")
    @ResponseBody
    public void mq(){
        mqSender.send("hello");
    }

    @RequestMapping("/mq/fanout")
    @ResponseBody
    public void mq01(){
        mqSender.send("hello");
    }

    @RequestMapping("/mq/direct01")
    @ResponseBody
    public void mq02(){
        mqSender.send01("hello, red");
    }

    @RequestMapping("/mq/direct02")
    @ResponseBody
    public void mq03(){
        mqSender.send02("hello, green");
    }
*/
}
