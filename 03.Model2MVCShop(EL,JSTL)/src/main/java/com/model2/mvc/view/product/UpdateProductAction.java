package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;



public class UpdateProductAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		int prodNo=Integer.parseInt(request.getParameter("prodNo"));
		System.out.println("prodNo");
		Product productVO=new Product();
		productVO.setProdNo(prodNo);
		productVO.setProdName(request.getParameter("prodName"));
		productVO.setProdDetail(request.getParameter("prodDetail"));
		productVO.setManuDate(request.getParameter("manuDate"));
		productVO.setPrice(Integer.parseInt(request.getParameter("price")));
		productVO.setFileName(request.getParameter("fileName"));
		System.out.println("시작해봐라");
		
		ProductService service=new ProductServiceImpl();
		service.updateProduct(productVO);
		
		
		/*HttpSession session=request.getSession();
		System.out.println("요 시작해해해ㅐ해");
		int sessionId=((ProductVO)session.getAttribute("product")).getProdNo();
		System.out.println("sessionId가 잘 들어갔으면 실행"+sessionId);
		
		if(sessionId==prodNo){
			session.setAttribute("product", productVO);
			return "redirect:/product/listProduct.jsp";
	
		
		}
		*/
		return "redirect:/getProduct.do?prodNo="+prodNo+"&menu=search";
	
	}
}