package com.imooc.o2o.util;

import javax.servlet.http.HttpServletRequest;

public class CodeUtil {

    public static boolean checkVerifyCode(HttpServletRequest request){
        String verifyCodeActual = HttpServletRequestUtil.getString(request, "verifyCodeActual");
        String verifyCodeExpected = (String) request.getSession().getAttribute("KAPTCHA_SESSION_KEY");
        if(verifyCodeActual == null || !verifyCodeActual.equals(verifyCodeExpected)){
            return false;
        }return true;
    }
}
