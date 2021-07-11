package com.dys.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dys.seckill.pojo.User;
import com.dys.seckill.vo.LoginVo;
import com.dys.seckill.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dys
 * @since 2021-07-05
 */
public interface IUserService extends IService<User> {

    RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);

    /**
     * 根据cookie获取用户
     * @param userTicket
     * @return
     */
    User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response);
}
