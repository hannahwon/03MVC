package com.model2.mvc.service.purchase.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;


public class PurchaseServiceImpl implements PurchaseService {

	private ProductDAO prodDAO;
	private PurchaseDAO dao;
	
	public PurchaseServiceImpl() {
		dao = new PurchaseDAO();
	}
	@Override
	public void addPurchase(Purchase purchaseVO) throws Exception {
		dao.insertPurchase(purchaseVO);

	}

	@Override
	public Purchase getPurchase(int tranNo) throws Exception {
		
		return dao.findPurchase(tranNo);
	}
	
	@Override
	public Purchase getPurchase2(int prodNo) throws Exception {
		
		return dao.findPurchase2(prodNo);
	}

	@Override
	public Map<String,Object> getPurchaseList(Search searchVO, String buyerId) throws Exception {
		
		return dao.getPurchaseList(searchVO, buyerId);
	}

	@Override
	public Map<String,Object> getSaleList(Search searchVO) throws Exception {
		
		return dao.getSaleList(searchVO);
	}


	@Override
	public void updatePurchase(Purchase purchaseVO) throws Exception {
		
		dao.updatePurchase(purchaseVO);	
	}


	@Override
	public void updateTranCode(Purchase purchaseVO) throws Exception {
		
		dao.updateTranCode(purchaseVO);
	}
	

}
