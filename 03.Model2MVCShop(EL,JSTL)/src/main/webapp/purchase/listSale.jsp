<%@ page contentType="text/html; charset=euc-kr" pageEncoding="euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
	List<Product> list = (ArrayList<Product>)request.getAttribute("list");
	Page resultPage = (Page)request.getAttribute("resultPage");

	Search searchVO = (Search)request.getAttribute("searchVO");
	//==>null 을 ""(nullString)으로 변경

	String searchCondition = CommonUtil.null2str(searchVO.getSearchCondition());
	String searchKeyword = CommonUtil.null2str(searchVO.getSearchKeyword());
--%>


<html>
<head>
<title>상품 관리</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
<!--
function fncGetList(currentPage){
	document.getElementById("currentPage").value=currentPage;
	document.detailForm.submit();
}
-->
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" method="post" action="/listSale.do?menu=manage">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
				
							상품 관리
									
					</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
	
		<td align="right">
			<select name="searchCondition"  class="ct_input_g" style="width:80px">
		
				<option value="0" ${ search.searchCondition==0 && ! empty search.searchCondition ? "selected" : "" }>상품번호</option>
				<option value="1" ${ search.searchCondition==1 && ! empty search.searchCondition ? "selected" : "" }>상품명</option>
				<option value="2" ${ search.searchCondition==2 && ! empty search.searchCondition ? "selected" : "" }>상품가격</option>
		
			</select>
			<input 	type="text" name="searchKeyword" 
							value="${! empty search.searchKeyword ? search.searchKeyword : ""}"
							class="ct_input_g" style="width:200px; height:19px" >
		</td>
	
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetList(1);">검색</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >전체 ${resultPage.totalCount} 건수, 현재 ${resultPage.currentPage} 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">등록일</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">현재상태</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
	<c:set var="i" value="0"/>
	<c:forEach var="product" items="${list}">
		<c:set var="i" value="${i+1}" />
		<tr class="ct_list_pop">
			<td align="center">${i}</td>
			<td></td>
		<%--	<td align="left"><a href="/getProduct.do?prodNo=${product.prodNo}">${product.prodNo}</a></td> --%>
			<td align="left"><a href="/getProduct.do?prodNo=${product.prodNo}&menu=${param.menu}">${product.prodName}</a></td>
			<td></td>
			
			
	
<%--	<tr class="ct_list_pop">
		<td align="center">${ i+1 }</td>
		<td></td>		
				<td align="left"><a href="/getProduct.do?prodNo=${product.prodNo}&menu=${request.Parameter("menu")}">${product.prodName}</a></td>		
		<td></td> --%>
		
		<td align="left">${product.price}</td>
		<td></td>
		<td align="left">${product.regDate}</td>
		<td></td>
		<td align="left">
		<c:if test="${product.proTranCode==null}">
			판매중 
		</c:if>
		<c:if test="${product.proTranCode.trim().equals('1')}">
			구매완료
			<a href="/updateTranCodeByProd.do?prodNo=${product.prodNo}&tranCode=2&page=${search.currentPage}">배송하기</a>
		</c:if>
		<c:if test="${product.proTranCode.trim().equals('2')}">
			배송중
		</c:if>
		<c:if test="${product.proTranCode.trim().equals('3')}">
			배송완료
		</c:if>
		
		</td>	
	</tr>
	
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>	
	</c:forEach>	
</table>	

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
		<input type="hidden" id="currentPage" name="currentPage" value="1"/>
		<%-- 	<% if( resultPage.getCurrentPage() <= resultPage.getPageUnit() ){ %>
					◀ 이전
			<% }else{ %>
					<a href="javascript:fncGetProductList('<%=resultPage.getCurrentPage()-1%>')">◀ 이전</a>
			<% } %>

			<%	for(int i=resultPage.getBeginUnitPage();i<= resultPage.getEndUnitPage() ;i++){	%>
					<a href="javascript:fncGetProductList('<%=i %>');"><%=i %></a>
			<% 	}  %>
	
			<% if( resultPage.getEndUnitPage() >= resultPage.getMaxPage() ){ %>
					이후 ▶
			<% }else{ %>
					<a href="javascript:fncGetProductList('<%=resultPage.getEndUnitPage()+1%>')">이후 ▶</a>
			<% } %>
		--%>
			<jsp:include page="../common/pageNavigator.jsp"/>
    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->

</form>

</div>
</body>
</html>

