package com.model2.mvc.view.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.User;

public class ListPurchaseAction extends Action {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Search searchVO = new Search();

		int currentPage = 1;
		if (request.getParameter("currentPage") != null)
			currentPage = Integer.parseInt(request.getParameter("currentPage"));

		searchVO.setCurrentPage(currentPage);
		searchVO.setSearchCondition(request.getParameter("searchCondition"));
		searchVO.setSearchKeyword(request.getParameter("searchKeyword"));

		// web.xml meta-data 로 부터 상수 추출
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		searchVO.setPageSize(pageSize);

		// Business logic 수행
		HttpSession session = request.getSession();
		User userVO = (User) session.getAttribute("user");
		System.out.println("ListPurchaseAction.jave에서 userVO:" + userVO);
		
		PurchaseService service = new PurchaseServiceImpl();
		String buyerId = userVO.getUserId();
		System.out.println("buyerId:" + buyerId);
		Map<String, Object> map = service.getPurchaseList(searchVO, buyerId);
				
		Page resultPage = new Page(currentPage, ((Integer) map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListPurchaseAction::" + resultPage);

		// Model 과 View 연결
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("search", searchVO);

		System.out.println("map.get(\"list\") :" + map.get("list"));
		System.out.println("resultPage : " + resultPage);
		System.out.println("search : " + searchVO);
		
		return "forward:/purchase/listPurchase.jsp";
	}

}
