package com.model2.mvc.service.product.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.domain.Product;



public class ProductServiceImpl implements ProductService{
	
	//field
	private ProductDAO productDAO;
	
	//constructor
	public ProductServiceImpl() {
		productDAO=new ProductDAO();
	}
	
	//method
	public void addProduct(Product productVO) throws Exception {
		productDAO.insertProduct(productVO);
	}


	public Product getProduct(int ProductVO) throws Exception {
		return productDAO.findProduct(ProductVO);
	}
	

	public Map<String, Object> getProductList(Search searchVO) throws Exception {
		return productDAO.getProductList(searchVO);
	}

	
	public void updateProduct(Product productVO) throws Exception {
		productDAO.updateProduct(productVO);
	}

}