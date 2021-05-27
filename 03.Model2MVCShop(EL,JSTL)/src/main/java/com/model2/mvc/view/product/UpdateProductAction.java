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
		System.out.println("updateProductAction.java setting 완료");
		
		ProductService service=new ProductServiceImpl();
		service.updateProduct(productVO);
		
		
		/*HttpSession session=request.getSession();
		System.out.println("�� ���������ؤ���");
		int sessionId=((ProductVO)session.getAttribute("product")).getProdNo();
		System.out.println("sessionId�� �� ������ ����"+sessionId);
		
		if(sessionId==prodNo){
			session.setAttribute("product", productVO);
			return "redirect:/product/listProduct.jsp";
	
		
		}
		*/
		return "redirect:/getProduct.do?prodNo="+prodNo+"&menu=search";
	
	}
}