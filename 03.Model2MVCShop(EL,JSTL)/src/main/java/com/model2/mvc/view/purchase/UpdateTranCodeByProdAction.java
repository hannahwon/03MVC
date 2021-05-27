package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeByProdAction extends Action {

	public UpdateTranCodeByProdAction() {
		
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		System.out.println("UpdateTranCodeByProdAction.java execute() 시작");
		
		PurchaseService service = new PurchaseServiceImpl();
		System.out.println("UpdateTranCodeByProdAction.java service : "+service);
		
		Purchase purchaseVO = service.getPurchase2(Integer.parseInt(request.getParameter("prodNo")));
		System.out.println("UpdateTranCodeByProdAction.java purchaseVO 1:"+purchaseVO);
		purchaseVO.setTranCode(request.getParameter("tranCode"));
		
		System.out.println("UpdateTranCodeByProdAction.java purchaseVO 2:"+purchaseVO);
		
		service.updateTranCode(purchaseVO);
		
		System.out.println("UpdateTranCodeByProdAction.java execute() 완료");
		
		return "redirect:/listSale.do?menu=manage&page="+request.getParameter("page");
	}

}
