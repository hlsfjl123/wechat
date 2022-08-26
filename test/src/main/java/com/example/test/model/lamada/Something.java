package com.example.test.model.lamada;

import org.springframework.util.StringUtils;

/**
 * @Author: User-XH251
 * @Date: 2022/8/26 17:13
 */
public class Something {

    public String startwhith(String params){
      return StringUtils.hasText(params)?params.substring(0,1):"";
    }
}
