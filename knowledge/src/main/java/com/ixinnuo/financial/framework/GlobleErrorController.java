package com.ixinnuo.financial.framework;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局controller级别的exception处理
 * 同一个级别额exception只能有一个处理了handler
 * @author aisino
 *
 */

@ControllerAdvice
public class GlobleErrorController{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 返回静态页面
	 * @param req
	 * @param e
	 * @return
	 */
	//@ExceptionHandler(Exception.class)
	public String errorHtml(HttpServletRequest req, Exception e){
		logger.error("请求的url：{}，发生异常：{}", req.getRequestURI(),e.getMessage());
		return "redirect:/html/error.html";
	}
	
	/**
	 * 返回json数据
	 * @param req
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ReturnData errorJson(HttpServletRequest req,HttpServletResponse res, Exception e){
		res.setHeader("Content-Type", "application/json");
		logger.error("请求的url：{}，发生异常：{}", req.getRequestURI(),e.getMessage());
		ReturnData returnData = new ReturnData(Code.ERROR);
		returnData.getDataBody().put("errorMsg", e.getMessage());
		return returnData;
	}
	
}
