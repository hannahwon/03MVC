package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;


public class ProductDAO {

	public ProductDAO() {
		
	}
	public void insertProduct(Product productVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql = "INSERT INTO product VALUES(seq_product_prod_no.nextval,?,?,?,?,?,SYSDATE)";
				
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate().replaceAll("-", ""));
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
	
		stmt.executeUpdate();
		
		System.out.println("insert :"+stmt+"완료");
		System.out.println(con);
		
		stmt.close();
		con.close();
		
	}
	
	public Product findProduct(int prodNo) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM PRODUCT WHERE PROD_NO =?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);
		
		ResultSet rs = stmt.executeQuery();
		
		Product productVO = null;
		
		while(rs.next()) {
			productVO = new Product();
			productVO.setProdNo(rs.getInt("PROD_NO"));
			productVO.setProdName(rs.getString("PROD_NAME"));
			productVO.setProdDetail(rs.getString("PROD_DETAIL"));
			productVO.setManuDate(rs.getString("MANUFACTURE_DAY"));
			productVO.setPrice(rs.getInt("PRICE"));
			productVO.setFileName(rs.getString("IMAGE_FILE"));
			productVO.setRegDate(rs.getDate("REG_DATE"));
			
			
		}
		rs.close();
		con.close();
		stmt.close();
		return productVO;
			
	}
		
	
	public Map<String,Object> getProductList(Search searchVO) throws Exception {
		
		Map<String , Object>  map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		// Original Query 구성
		String sql = "SELECT * FROM PRODUCT";
		
		if (searchVO.getSearchCondition() != null) {
			if(searchVO.getSearchCondition().equals("0") && !searchVO.getSearchKeyword().equals("")) {
				sql += " WHERE PROD_NO LIKE '%"+ searchVO.getSearchKeyword()+ "%'";
			} else if(searchVO.getSearchCondition().equals("1") && !searchVO.getSearchKeyword().equals("")) {
				sql += " WHERE PROD_NAME LIKE '%"+ searchVO.getSearchKeyword()+ "%'";
			} else if(searchVO.getSearchCondition().equals("2") && !searchVO.getSearchKeyword().equals("")) {
				sql += " WHERE PRICE LIKE '%"+ searchVO.getSearchKeyword()+ "%'";
	
			}
		}
		sql += " order by PROD_NO";

		System.out.println("ProductDAO::Original SQL ::"+sql);
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("ProductDAO :: totalCount  :: " + totalCount);
		
		//==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, searchVO);
		System.out.println("currentpage query");
		PreparedStatement stmt = con.prepareStatement(	sql,
														ResultSet.TYPE_SCROLL_INSENSITIVE,
														ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stmt.executeQuery();
		
		System.out.println(searchVO);
		
		List<Product> list = new ArrayList<Product>();
		
		
		while(rs.next()){
			Product productVO = new Product();
			productVO.setProdNo(rs.getInt("PROD_NO"));
			productVO.setProdName(rs.getString("PROD_NAME"));
			productVO.setProdDetail(rs.getString("PROD_DETAIL"));
			productVO.setManuDate(rs.getString("MANUFACTURE_DAY"));
			productVO.setPrice(rs.getInt("PRICE"));
			productVO.setFileName(rs.getString("IMAGE_FILE"));
			productVO.setRegDate(rs.getDate("REG_DATE"));
			list.add(productVO);
		}
		
		//==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount));
		//==> currentPage 의 게시물 정보 갖는 List 저장
		map.put("list", list);

		rs.close();
		stmt.close();
		con.close();
		
		return map;
	}
	
	public void updateProduct(Product productVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		System.out.println("커넥션 실행완료");
		String sql = "UPDATE PRODUCT set PROD_NAME=?,PROD_DETAIL=?,MANUFACTURE_DAY=?,PRICE=?,IMAGE_FILE=? where PROD_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate().replaceAll("-", ""));
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		stmt.setInt(6, productVO.getProdNo());
		stmt.executeUpdate();
		System.out.println("업데이트실행완료");
		
		stmt.close();
		con.close();
	}
	// 게시판 Page 처리를 위한 전체 Row(totalCount)  return
	private int getTotalCount(String sql) throws Exception {
			
		sql = "SELECT COUNT(*) "+
			       "FROM ( " +sql+ ") countTable";
			
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
			
		int totalCount = 0;
		if( rs.next() ){
				totalCount = rs.getInt(1);
		}
			
		pStmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}
	
	// 게시판 currentPage Row 만  return 
	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
									" 	FROM (	"+sql+" ) inner_table "+
									"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println("ProductDAO :: make SQL :: "+ sql);	
		
		return sql;
	}
}
