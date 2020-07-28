package com.imooc.o2o.web.local;

import com.imooc.o2o.dto.LocalAuthExecution;
import com.imooc.o2o.entity.LocalAuth;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.enums.LocalAuthStateEnum;
import com.imooc.o2o.exceptions.LocalAuthOperationException;
import com.imooc.o2o.service.LocalAuthService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/local")
public class LocalAuthController {

    @Autowired
    private LocalAuthService localAuthService;

    //登入
    @RequestMapping(value = "/logincheck", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> loginCheck(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("redirect", false);
            modelMap.put("errMsg", "err kaptcha");
            return modelMap;
        } else {
            String username = HttpServletRequestUtil.getString(request, "userName");
            String password = HttpServletRequestUtil.getString(request, "password");
            LocalAuth localAuth = localAuthService.login(username, password);
            if (username != null && password != null) {
                if (localAuth != null) {
                    request.getSession().setAttribute("user", localAuth.getPersonInfo());
                    modelMap.put("success",true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "帳號密碼錯誤");
                }
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "請輸入帳號密碼");
            }

        }
        return modelMap;
    }

    //修改密碼
    @RequestMapping(value = "/changelocalpwd", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> changelocalpwd(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "驗證碼錯誤");
            return modelMap;
        }
        String username = HttpServletRequestUtil.getString(request, "userName");
        String password = HttpServletRequestUtil.getString(request, "password");
        String newPassword = HttpServletRequestUtil.getString(request, "newPassword");
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        if (user != null && user.getUserId() != null && username != null && password != null && newPassword != null
                && !password.equals(newPassword)) {
            try {
                //查看原先帳號，看與輸入的是否一致，否則為非法操作
                LocalAuth localAuth = localAuthService.queryLocalByUserId(user.getUserId());
                if (localAuth == null || user.getName().equals(localAuth.getUsername())) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "輸入的帳號非本地帳號");
                }
                //修改密碼
                LocalAuthExecution le = localAuthService.modifyLocalAuth(user.getUserId()
                        , username, password, newPassword);
                if (le.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", le.getStateInfo());
                }
            } catch (LocalAuthOperationException e) {
                modelMap.put("redirect", false);
                modelMap.put("errMsg", e.getMessage());
            }
        } else {
            modelMap.put("redirect", false);
            modelMap.put("errMsg", "輸入資訊有誤");
        }
        return modelMap;
    }
    //登出
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> logOut(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        request.getSession().setAttribute("user",null);
        modelMap.put("success",true);
        return modelMap;

    }
//
}
