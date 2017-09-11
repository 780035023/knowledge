package com.ixinnuo.financial.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ixinnuo.financial.framework.Code;
import com.ixinnuo.financial.framework.ReturnData;
import com.ixinnuo.financial.model.Hello;
import com.ixinnuo.financial.service.HelloService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 用于测试
 * 
 */
@Controller
@RequestMapping("/test")
public class TestController {
    
    @Autowired
    private HelloService helloService;

    /**
     * 测试controller redirect
     * @param code
     * @return
     * @throws Exception
     */
    @ApiOperation("跳转hello页面")
    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public String redirect() throws Exception {
        return "redirect:/html/hello.html";
    }
    
    /**
     * 测试controller 返回json数据
     * @param name 你偶像名字
     * @return
     */
    @ApiOperation("添加一条数据并返回json")
    @ApiParam(name="name",value="你偶像的名字",required=true,type="String")
    @RequestMapping(value = "/addOneAndSelectAll", method = RequestMethod.GET)
    @ResponseBody
    public ReturnData addOneAndSelectAll(String name) {
        if(StringUtils.isBlank(name)){
            ReturnData returnData = new ReturnData(Code.EMPTY_PARAMETER_ERROR);
            returnData.getDataBody().put("msg", "请说出你偶像的名字...");
            return returnData;
        }
        List<Hello> addOneAndSelectAll = helloService.addOneAndSelectAll(name);
        ReturnData returnData = new ReturnData(Code.OK);
        returnData.getDataBody().put("data", addOneAndSelectAll);
        return returnData;
    }
    /**
     * 测试controller 返回json数据,分页查询
     * @param name 你偶像名字
     * @return
     */
    @ApiOperation("添加一条数据并返回json，分页显示")
    @RequestMapping(value = "/addOneAndSelectByPage", method = RequestMethod.GET)
    @ResponseBody
    public ReturnData addOneAndSelectByPage(String name1,Integer pageNum,Integer pageSize) {
        if(StringUtils.isBlank(name1)){
            ReturnData returnData = new ReturnData(Code.EMPTY_PARAMETER_ERROR);
            returnData.getDataBody().put("msg", "请说出你偶像的名字...");
            return returnData;
        }
        List<Hello> addOneAndSelectAll = helloService.addOneAndSelectByPage(name1, pageNum, pageSize);
        ReturnData returnData = new ReturnData(Code.OK);
        returnData.getDataBody().put("data", addOneAndSelectAll);
        return returnData;
    }

}
