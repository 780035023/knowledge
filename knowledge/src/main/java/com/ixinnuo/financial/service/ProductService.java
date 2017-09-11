package com.ixinnuo.financial.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ixinnuo.financial.dao.mysql.ProductMapper;
import com.ixinnuo.financial.model.Product;

@Service
@Transactional
public class ProductService {
	
	@Autowired
	private ProductMapper productMapper;
	
	/**
	 * 返回所有Product列表
	 * @return
	 */
	public List<Product> selectAllProduct(){
		return productMapper.selectAll();
	}
	
	/**
	 * 返回根据条件筛选的Product列表
	 * @param product
	 * @return
	 */
	public List<Product> selectAllProductByCondition(Product product){
		return productMapper.select(product);
	}
	
	/**
	 * 根据id返回对应Product
	 * @param id
	 * @return
	 */
	public Product selectProductById(int id){
		return productMapper.selectByPrimaryKey(id);
	}
	

}
