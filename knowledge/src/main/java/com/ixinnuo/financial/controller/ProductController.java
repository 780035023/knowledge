package com.ixinnuo.financial.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.ixinnuo.financial.framework.Code;
import com.ixinnuo.financial.framework.ReturnData;
import com.ixinnuo.financial.model.Product;
import com.ixinnuo.financial.service.ProductService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	/**
	 * 查询所有产品列表
	 * @param PageNum
	 * @return
	 */
	@ApiOperation("查询所有产品列表")
    @RequestMapping(value = "/showAllProduct", method = RequestMethod.GET)
	@ResponseBody
    public ReturnData showAllProduct(int PageNum){
		
		PageHelper.startPage(PageNum, 1);
        List<Product> productlist = productService.selectAllProduct();
        
        ReturnData returnData = new ReturnData(Code.OK);
        returnData.getDataBody().put("productlist", productlist);
        return returnData;
    }
	
	/**
	 * 根据条件查询产品列表
	 * @param PageNum
	 * @return
	 */
	@ApiOperation("根据条件查询产品列表")
    @RequestMapping(value = "/showProductByCondition", method = RequestMethod.GET)
	@ResponseBody
    public ReturnData showProductByCondition(int PageNum){
		
		Product product = new Product();
		product.setMaxLoanAmount(123d);
		//product.setRepaymentCode("1231242");
		
		PageHelper.startPage(PageNum, 1);
        List<Product> productlist = productService.selectAllProductByCondition(product);
        
        ReturnData returnData = new ReturnData(Code.OK);
        returnData.getDataBody().put("productlist", productlist);
        return returnData;
    }
	
	/**
	 * 根据Id查询对应产品
	 * @param id
	 * @return
	 */
	@ApiOperation("根据Id查询对应产品")
    @RequestMapping(value = "/showProductById", method = RequestMethod.GET)
	@ResponseBody
	public ReturnData showProductById(int id){
		
		Product product = productService.selectProductById(id);
		
		if(product == null){
			ReturnData returnData = new ReturnData(Code.PRODUCT_NOT_FOUND);
			return returnData;
		}
		
		ReturnData returnData = new ReturnData(Code.OK);
        returnData.getDataBody().put("product", product);
        return returnData;
		
	}

}
