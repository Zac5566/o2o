package com.imooc.o2o.interceptor.shopadmin;

import com.imooc.o2o.entity.PersonInfo;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

//店家管理系統的登入攔截器
public class ShopLoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object objUser = request.getSession().getAttribute("user");
        if (objUser != null) {
            PersonInfo user = (PersonInfo) objUser;
            if (user != null && user.getUserId() != null && user.getUserId() > 0
                    && user.getEnableStatus() == 1) {
                return true;
            }
        }
        //若不滿足條件則跳轉到登入頁面
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<script>");
        out.println("window.open('"+request.getContextPath()+"/local/login?usertype=2','_self')");
        out.println("</script>");
        out.println("</html>");
        return false;
    }
}
