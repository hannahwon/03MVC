package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeAction extends Action {

	public UpdateTranCodeAction() {
		
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		System.out.println("UpdateTranCodeAction.java execute() 시작");
		
		Purchase purchaseVO = new Purchase();
		System.out.println("UpdateTranCodeAction purchase : "+purchaseVO);
		
		purchaseVO.setTranCode(request.getParameter("tranCode"));
		purchaseVO.setTranNo(Integer.parseInt(request.getParameter("tranNo")));
		
		System.out.println("tranCode : "+purchaseVO.getTranCode());
		
		PurchaseService service = new PurchaseServiceImpl();
		service.updateTranCode(purchaseVO);
		
		System.out.println("UpdateTranCodeAction.java execute() 완료");
		
		return "redirect:/listPurchase.do?menu=manage&page="+request.getParameter("page");
	}

}
