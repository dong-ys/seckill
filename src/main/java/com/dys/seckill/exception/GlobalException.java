package com.dys.seckill.exception;

import com.dys.seckill.vo.RespBeanEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GlobalException extends RuntimeException {

    private RespBeanEnum respBeanEnum;

}
