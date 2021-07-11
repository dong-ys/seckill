package com.dys.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dys.seckill.exception.GlobalException;
import com.dys.seckill.mapper.UserMapper;
import com.dys.seckill.pojo.User;
import com.dys.seckill.service.IUserService;
import com.dys.seckill.utils.CookieUtil;
import com.dys.seckill.utils.MD5Util;
import com.dys.seckill.utils.UUIDUtil;
import com.dys.seckill.vo.LoginVo;
import com.dys.seckill.vo.RespBean;
import com.dys.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dys
 * @since 2021-07-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        /*if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        if (!ValidatorUtil.isMobile(mobile)){
            return RespBean.error(RespBeanEnum.MOBILE_ERROR);
        }*/
        User user = userMapper.selectById(mobile);
        if (user == null){
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        if (!MD5Util.fromPassToDBPass(password, user.getSlat()).equals(user.getPassword())){
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        //生成cookie
        String ticket = UUIDUtil.uuid();
        redisTemplate.opsForValue().set("user:" + ticket, user);
        //request.getSession().setAttribute(ticket, user);
        CookieUtil.setCookie(request, response, "userTicket", ticket);
        return RespBean.success(ticket);
    }

    @Override
    public User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(userTicket)){
            return null;
        }
        User user = (User) redisTemplate.opsForValue().get("user:" + userTicket);
        if (user != null){
            CookieUtil.setCookie(request, response, "userTicket", userTicket);
        }
        return user;
    }
}
