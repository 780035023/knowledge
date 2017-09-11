package com.ixinnuo.financial.controller;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ixinnuo.financial.framework.LoginInfo;
import com.ixinnuo.financial.framework.ReturnData;
import com.ixinnuo.financial.framework.SessionKey;
import com.ixinnuo.financial.util.httpclient.HttpClientUtils;
import com.ixinnuo.financial.util.json.JsonUtil;

@Controller
@RequestMapping("/uumsLogin")
public class UUMSLoginController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${UUMS_USER_URL}")
    public String UUMS_USER_URL;

    @RequestMapping("/callback")
    public String callback(HttpServletRequest request, HttpServletResponse response) {
        /* 测试用 */
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            String parameterValue = request.getParameter(parameterName);
            logger.info("{}:{}", parameterName, parameterValue);
        }
        String code = request.getParameter("code");
        String state = request.getParameter("state");

        if (StringUtils.isBlank(code)) {
            // 登录失败,回首页
            String error = request.getParameter("error");
            return "redirect:/html/index.html?error=" + error;
        }
        StringBuffer strbuf = new StringBuffer(UUMS_USER_URL);
        strbuf.append("&code=").append(code);
        String requestByGet = HttpClientUtils.requestByGet(strbuf.toString(), 15*1000);
        JsonUtil jsonUtil = new JsonUtil();
        jsonUtil.setFAIL_ON_UNKNOWN_PROPERTIES(false);
        LoginInfo loginInfo = jsonUtil.jsonStr2Object(requestByGet, LoginInfo.class);
        if (null == loginInfo || null == loginInfo.dataBody) {
            // 登录失败,回首页
            return "redirect:/html/index.html?error=";
        }
        // 登录成功，session加入登录用户信息
        request.getSession().setAttribute(SessionKey.LOGIN_INFO.name(), loginInfo);
        if ("index".equals(state)) {
            return "redirect:/html/index.html";
        }
        return null;
    }
}
