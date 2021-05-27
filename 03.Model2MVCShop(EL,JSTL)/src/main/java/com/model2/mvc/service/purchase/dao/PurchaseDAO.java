package com.model2.mvc.service.purchase.dao;

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
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.domain.User;

public class PurchaseDAO {

	public PurchaseDAO() {

	}

	public void insertPurchase(Purchase purchaseVO) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "INSERT INTO transaction VALUES (seq_transaction_tran_no.nextval,?,?,?,?,?,?,?,?,SYSDATE,?)";
		System.out.println("sql은" + sql);

		PreparedStatement stmt = con.prepareStatement(sql);
		System.out.println("stmt은" + stmt);

		stmt.setInt(1, purchaseVO.getPurchaseProd().getProdNo());
		stmt.setString(2, purchaseVO.getBuyer().getUserId());
		stmt.setString(3, purchaseVO.getPaymentOption());
		stmt.setString(4, purchaseVO.getReceiverName());
		stmt.setString(5, purchaseVO.getReceiverPhone());
		stmt.setString(6, purchaseVO.getDivyAddr());
		stmt.setString(7, purchaseVO.getDivyRequest());
		stmt.setString(8, purchaseVO.getTranCode());
		stmt.setString(9, purchaseVO.getDivyDate().replaceAll("-", ""));

		stmt.executeUpdate();

		System.out.println(con);

		stmt.close();
		con.close();

		System.out.println("PurchaseDAO 에서 insert 완료");
	}

	public Purchase findPurchase(int tranNo) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM transaction t, product p, users u" + " WHERE t.prod_no = p.prod_no"
				+ " AND t.buyer_id = u.user_id" + " AND t.tran_no = ? " + " ORDER BY tran_no";

		System.out.println("PurchaseDAO 에서 sql : " + sql);

		PreparedStatement stmt = con.prepareStatement(sql);
		System.out.println("PurchaseDAO 에서 stmt : " + stmt);

		stmt.setInt(1, tranNo);

		ResultSet rs = stmt.executeQuery();
		System.out.println("PurchaseDAO 에서 rs : " + rs);

		User userVO = new User();
		Product productVO = new Product();
		Purchase purchaseVO = new Purchase();

		while (rs.next()) {
			// 컬럼명으로 찾기 FIND

			userVO.setUserId(rs.getString("USER_ID"));
			userVO.setUserName(rs.getString("USER_NAME"));
			userVO.setAddr(rs.getString("ADDR"));
			userVO.setEmail(rs.getString("EMAIL"));
			userVO.setPassword(rs.getString("PASSWORD"));
			userVO.setPhone(rs.getString("CELL_PHONE"));
			userVO.setRegDate(rs.getDate("REG_DATE"));
			userVO.setRole(rs.getString("ROLE"));
			userVO.setSsn(rs.getString("SSN"));
			purchaseVO.setBuyer(userVO);
			System.out.println("setting 1 완료");

			productVO.setProdNo(rs.getInt("PROD_NO"));
			productVO.setProdName(rs.getString("PROD_NAME"));
			productVO.setProdDetail(rs.getString("PROD_DETAIL"));
			productVO.setPrice(rs.getInt("PRICE"));
			productVO.setFileName(rs.getString("IMAGE_FILE"));
			productVO.setRegDate(rs.getDate("REG_DATE"));
			productVO.setManuDate(rs.getString("MANUFACTURE_DAY"));
			purchaseVO.setPurchaseProd(productVO);
			System.out.println("setting 2 완료");

			purchaseVO.setTranNo(rs.getInt("TRAN_NO"));
			purchaseVO.setPaymentOption(rs.getString("PAYMENT_OPTION"));
			purchaseVO.setReceiverName(rs.getString("RECEIVER_NAME"));
			purchaseVO.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			purchaseVO.setDivyAddr(rs.getString("DEMAILADDR"));
			purchaseVO.setDivyRequest(rs.getString("DLVY_REQUEST"));
			purchaseVO.setTranCode(rs.getString("TRAN_STATUS_CODE"));
			purchaseVO.setOrderDate(rs.getDate("ORDER_DATA"));
			purchaseVO.setDivyDate(rs.getString("DLVY_DATE"));
			System.out.println("setting 3 완료");

		}
		stmt.close();
		con.close();

		System.out.println("findPurchase 완료");
		return purchaseVO;

	}

	public Purchase findPurchase2(int prodNo) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM transaction WHERE prod_no = ?";
		System.out.println("PurchaseDAO 에서 findPurchase2 sql : " + sql);

		PreparedStatement stmt = con.prepareStatement(sql);
		System.out.println("PurchaseDAO 에서 findPurchase2 stmt : " + stmt);

		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();
		System.out.println("PurchaseDAO 에서 findPurchase2 rs : " + rs);

		Purchase purchaseVO = null;

		while (rs.next()) {

			purchaseVO = new Purchase();

			purchaseVO.setTranNo(rs.getInt("TRAN_NO"));
			purchaseVO.setDivyAddr(rs.getString("DEMAILADDR"));
			purchaseVO.setDivyDate(rs.getString("DLVY_DATE"));
			purchaseVO.setDivyRequest(rs.getString("DLVY_REQUEST"));
			purchaseVO.setOrderDate(rs.getDate("ORDER_DATA"));
			purchaseVO.setPaymentOption(rs.getString("PAYMENT_OPTION"));
			purchaseVO.setReceiverName(rs.getString("RECEIVER_NAME"));
			purchaseVO.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			purchaseVO.setTranCode(rs.getString("TRAN_STATUS_CODE"));
			System.out.println("setting 완료");

		}
		stmt.close();
		con.close();

		System.out.println("findPurchase2 완료");
		return purchaseVO;

	}

	public Map<String, Object> getPurchaseList(Search searchVO, String buyerId) throws Exception {

		System.out.println("PurchaseDAO에서 getPurchaseList 호출 : " + buyerId);

		Map<String, Object> map = new HashMap<String, Object>();

		Connection con = DBUtil.getConnection();

		// Original Query 구성
		String sql = "SELECT";
		sql += " tran_no, buyer_id, receiver_name, receiver_phone, tran_status_code";
		sql += " FROM transaction";
		sql += " WHERE buyer_id = '"+ buyerId + "'";
		sql += " ORDER BY tran_no";

		System.out.println("PurchaseDAO에서 getPurchaseList의 sql은" + sql);
		
		
		//stmt.setString(1, buyerId);
		
		
		// ==>TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("PurchaseDAO :: totalCount :: " + totalCount);

		// ==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, searchVO);
		System.out.println("currentpage query");

		//188번에서 바뀐 sql의 쿼리를 날리기
		PreparedStatement stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);

		ResultSet rs = stmt.executeQuery();
		System.out.println("sql" + sql);
		

		Purchase purchaseVO = null;
		UserService service = new UserServiceImpl();
		List<Purchase> list = new ArrayList<Purchase>();

		while (rs.next()) {
			purchaseVO = new Purchase();
			purchaseVO.setTranNo(rs.getInt("TRAN_NO"));
			purchaseVO.setBuyer(service.getUser(rs.getString("BUYER_ID")));
			purchaseVO.setReceiverName(rs.getString("RECEIVER_NAME"));
			purchaseVO.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			purchaseVO.setTranCode(rs.getString("TRAN_STATUS_CODE"));
			list.add(purchaseVO);

		}

		// ==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount));
		// ==> currentPage 의 게시물 정보 갖는 List 저장
		map.put("list", list);

		rs.close();
		stmt.close();
		con.close();

		return map;
	}

	public Map<String, Object> getSaleList(Search searchVO) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM product";
		if (searchVO.getSearchCondition() != null) {
			if (searchVO.getSearchCondition().equals("0") && !searchVO.getSearchKeyword().equals("")) {
				sql += " WHERE prod_no LIKE '%" + searchVO.getSearchKeyword() + "%'";
			} else if (searchVO.getSearchCondition().equals("1") && !searchVO.getSearchKeyword().equals("")) {
				sql += " WHERE prod_name LIKE '%" + searchVO.getSearchKeyword() + "%'";
			}
		}
		sql += " ORDER BY prod_no";

		System.out.println("PurchaseDAO에서 getSaleList sql:" + sql);

		// ==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("ProductDAO :: totalCount  :: " + totalCount);

		// ==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, searchVO);
		System.out.println("currentpage query");
		PreparedStatement stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);

		System.out.println("sql" + sql);

		ResultSet rs = stmt.executeQuery();

		System.out.println("PurchaseDAO에서 getSaleList searchVO : " + searchVO);

		List<Product> list = new ArrayList<Product>();

		while (rs.next()) {

			Product vo = new Product();
			vo.setProdNo(rs.getInt("PROD_NO"));
			vo.setProdName(rs.getString("PROD_NAME"));
			vo.setProdDetail(rs.getString("PROD_DETAIL"));
			vo.setManuDate(rs.getString("MANUFACTURE_DAY"));
			vo.setPrice(rs.getInt("PRICE"));
			vo.setFileName(rs.getString("IMAGE_FILE"));
			vo.setRegDate(rs.getDate("REG_DATE"));
			if (this.findPurchase2(vo.getProdNo()) != null) {
				vo.setProTranCode(this.findPurchase2(vo.getProdNo()).getTranCode());
			}
			list.add(vo);
		}
		// ==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount));
		// ==> currentPage 의 게시물 정보 갖는 List 저장
		map.put("list", list);

		rs.close();
		stmt.close();
		con.close();

		return map;

	}

	public void updatePurchase(Purchase purchaseVO) throws Exception {

		Connection con = DBUtil.getConnection();
		System.out.println("purchaseDAO updatePurchase connection 완료" + con);
		String sql = "UPDATE transaction SET payment_option=?, receiver_name=?, receiver_phone=?, demailaddr=?, dlvy_request=?, dlvy_date=? where tran_no=?";
		System.out.println("sql update sql은" + sql);

		PreparedStatement stmt = con.prepareStatement(sql);
		System.out.println("stmt update stmt : " + stmt);

		stmt.setString(1, purchaseVO.getPaymentOption());
		stmt.setString(2, purchaseVO.getReceiverName());
		stmt.setString(3, purchaseVO.getReceiverPhone());
		stmt.setString(4, purchaseVO.getDivyAddr());
		stmt.setString(5, purchaseVO.getDivyRequest());
		stmt.setString(6, purchaseVO.getDivyDate().replaceAll("-", ""));
		stmt.setInt(7, purchaseVO.getTranNo());

		stmt.executeUpdate();

		stmt.close();
		con.close();

		System.out.println("purchase 업데이트 실행완료");
	}

	public void updateTranCode(Purchase purchaseVO) throws Exception {

		System.out.println("PurchaseDAO에서 updateTranCodeByProd() 호출 :" + purchaseVO);

		Connection con = DBUtil.getConnection();

		String sql = "UPDATE transaction SET tran_status_code=? WHERE tran_no=?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchaseVO.getTranCode());
		stmt.setInt(2, purchaseVO.getTranNo());

		stmt.executeUpdate();

		stmt.close();
		con.close();

		System.out.println("updateTranCode 완료");

	}

	// 게시판 Page 처리를 위한 전체 Row(totalCount) return
	private int getTotalCount(String sql) throws Exception {

		sql = "SELECT COUNT(*) " + "FROM ( " + sql + ") countTable";

		Connection con = DBUtil.getConnection();
		System.out.println("PurchaseDAO에서 getTotalCount con : " + con);
		PreparedStatement pStmt = con.prepareStatement(sql);
		System.out.println("PurchaseDAO에서 getTotalCount pStmt : " + pStmt);
		ResultSet rs = pStmt.executeQuery();
		System.out.println("PurchaseDAO에서 getTotalCount rs : " + rs);

		int totalCount = 0;
		if (rs.next()) {
			totalCount = rs.getInt(1);
		}

		pStmt.close();
		con.close();
		rs.close();

		return totalCount;
	}

	// 게시판 currentPage Row 만 return
	private String makeCurrentPageSql(String sql, Search search) {
		sql = "SELECT * " + "FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " + " 	FROM (	" + sql
				+ " ) inner_table " + "	WHERE ROWNUM <=" + search.getCurrentPage() * search.getPageSize() + " ) "
				+ "WHERE row_seq BETWEEN " + ((search.getCurrentPage() - 1) * search.getPageSize() + 1) + " AND "
				+ search.getCurrentPage() * search.getPageSize();

		System.out.println("PurchasesDAO :: make SQL :: " + sql);

		return sql;
	}

}
