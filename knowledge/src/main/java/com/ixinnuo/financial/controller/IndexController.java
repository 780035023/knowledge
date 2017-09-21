package com.ixinnuo.financial.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ixinnuo.financial.framework.Code;
import com.ixinnuo.financial.framework.ReturnData;

@Controller
@RequestMapping("/index")
public class IndexController {

    @Value("${UUMS_LOGIN_URL}")
    public String UUMS_LOGIN_URL;
    
    //静态资源加载配置文件的值
    public static String staticProperty;
    
    @Value("${staticProperty}")
    public void setStaticProperty(String staticProperty) {
		IndexController.staticProperty = staticProperty;
	}



	@RequestMapping("/home")
    @ResponseBody
    public ReturnData home(){
        //获取uums登录地址
        ReturnData returnData = new ReturnData(Code.OK);
        //标识state为index
        returnData.getDataBody().put("url", UUMS_LOGIN_URL + "&state=index");
        return returnData;
    }
}
