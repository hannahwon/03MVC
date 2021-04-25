package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;



public class GetProductAction extends Action{

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		int prodNo =Integer.parseInt(request.getParameter("prodNo"));
		
		System.out.println("메서드시작");
		//HttpSession session=request.getSession();
		//session.invalidate();
		
		ProductService service=new ProductServiceImpl();
		Product vo=service.getProduct(prodNo);
		
		System.out.println(vo);
		request.setAttribute("vo", vo);
		
		System.out.println("menu는 무엇이냐"+request.getParameter("menu"));
		
	
		
		if(request.getParameter("menu").equals("manage")) {
			return "forward:/updateProductView.do";
		}
		
		return "forward:/product/getProduct.jsp";
	}
}