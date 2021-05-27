package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;


public class UpdatePurchaseViewAction extends Action{

	public UpdatePurchaseViewAction() {
		
	}
	
	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		
		System.out.println("updatePurchaseViewActione 실행");
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		System.out.println("UpdatePurchaseAction에서 tranNo : "+tranNo);
		
		PurchaseService service = new PurchaseServiceImpl();
		Purchase purchase = service.getPurchase(tranNo);
		
		System.out.println("UpdatePurchaseAction에서 purchaseVO : "+purchase);
		request.setAttribute("purchase", purchase);
		
		return "forward:/purchase/updatePurchase.jsp";
	}
}
