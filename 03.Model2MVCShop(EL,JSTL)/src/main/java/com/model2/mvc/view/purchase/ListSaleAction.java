package com.model2.mvc.view.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;


public class ListSaleAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		Search searchVO=new Search();
		
		int currentPage = 1;
		System.out.println("a: "+request.getParameter("currentPage"));
		if(request.getParameter("currentPage") != null)
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		
		searchVO.setCurrentPage(currentPage);
		searchVO.setSearchCondition(request.getParameter("searchCondition"));
		searchVO.setSearchKeyword(request.getParameter("searchKeyword"));
		
		// web.xml meta-data 로 부터 상수 추출
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));
		int pageUnit=Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		searchVO.setPageSize(pageSize);
		
		// Business logic 수행
		PurchaseService service=new PurchaseServiceImpl();
		Map<String,Object> map=service.getSaleList(searchVO);

		Page resultPage = new Page(currentPage, ((Integer) map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListPurchaseAction::" + resultPage);
		
		// Model 과 View 연결
		request.setAttribute("list", map.get("list"));
		System.out.println("map.get(\"list\") :" + map.get("list"));
		request.setAttribute("resultPage", resultPage);
		System.out.println("resultPage : " + resultPage);
		request.setAttribute("search", searchVO);
		System.out.println("search : " + searchVO);
			
		return "forward:/purchase/listSale.jsp";
	}
}