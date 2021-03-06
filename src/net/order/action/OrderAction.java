package net.order.action;

import java.util.ArrayList;

import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.beans.binding.StringExpression;
import net.coupon.db.CouponDAO;
import net.delivery.db.DeliveryBean;
import net.orderList.db.OrderJoinBean;
import net.orderList.db.OrderListBean;
import net.orderList.db.OrderListDAO;
import net.orderMenu.db.OrderMenuDAO;
import net.review.db.ReviewBean;
import net.review.db.ReviewDAO;
import net.store.db.StoreBean;
import net.store.db.StoreDAO;


public class OrderAction {
	
	public int insertOrderList(HttpServletRequest request, HttpServletResponse response) throws ParseException, JSONException {		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(request.getParameter("deliveryInfo"));
		JSONObject deliveryInfo = (JSONObject) obj;
		OrderListBean oBean = new OrderListBean();
		
		oBean.setCustomerNo((String) request.getSession().getAttribute("customerNo"));
		oBean.setStoreNo(request.getParameter("storeNo"));
		oBean.setRoadAddress((String) deliveryInfo.get("roadAddress")); 
		oBean.setDetailAddress((String) deliveryInfo.get("detailAddress"));
		oBean.setPhone((String) deliveryInfo.get("phone"));
		oBean.setPayment((String) deliveryInfo.get("payment"));
		oBean.setRequest((String) deliveryInfo.get("request"));
		oBean.setCouponNo((String) deliveryInfo.get("couponNo"));
		
		String storeNo = request.getParameter("storeNo");
	
		System.out.println("orderAction 구매한 가게 번호 : " + storeNo );
		
		OrderListDAO odao = new OrderListDAO();
		int orderNo = odao.insertOrderList(oBean);
		if (orderNo != 0) {
			System.out.println("주문 번호 : " + orderNo);
			odao.UpdateOrderCount(storeNo);
		}else{
			System.out.println("insertOrderList메서드로 부터 insert제대로 안됨 결과 : " + orderNo);
		}
		
		if(Integer.parseInt(oBean.getCouponNo())!=-1){
			CouponDAO cdao = new CouponDAO();
			cdao.updateUsedCoupon(Integer.parseInt(oBean.getCouponNo()));		
		}
		
		return orderNo;
		
	}
	
	public void insertOrderMenu(HttpServletRequest req, HttpServletResponse resp, int orderNo) throws ParseException, JSONException{
		JSONArray cart = new JSONArray(req.getParameter("cart"));

		OrderMenuDAO odao = new OrderMenuDAO();
		odao.insertOrderMenu(cart, orderNo);
		
		System.out.println("성공!");
	
	}
	
	
	public  void GetOrderDetail(HttpServletRequest request, HttpServletResponse response, String customerNo) throws Exception{

		OrderListDAO odao = new OrderListDAO();
		


	 	
		List<OrderJoinBean> orderlist = new ArrayList<OrderJoinBean>();

		OrderJoinBean join = new OrderJoinBean(); // 조인해서 결과물 가져오기
		
		orderlist = odao.GetOrderList(customerNo); // orderlist 테이블 
		
		System.out.println("오더 액션 GetOrderDetail 서버 액션 겟스토어 네임 " + join.getStoreName());
		
		System.out.println("오더 액션 전달받은 고객 number : " + customerNo);

		request.setAttribute("orderlist", orderlist);
		System.out.println("join내용 : " + orderlist + "      ");
		

	
		
	}

	
	public  void GetOrderRealDetail(HttpServletRequest request, HttpServletResponse response, String customerNo, String orderNo) throws Exception{

		
		/*레알루 상세하게 보여줌*/
		OrderListDAO odao = new OrderListDAO();
	
	 	
		List<OrderJoinBean> OrderRealDetail = new ArrayList<OrderJoinBean>();

		// OrderJoinBean join = new OrderJoinBean(); // 조인해서 결과물 가져오기
		
		OrderRealDetail = odao.GetOrderRealDetails(customerNo, orderNo ); // orderlist 테이블 
		

		
		System.out.println("오더 액션 전달받은 고객 number : " + customerNo);
		
		request.setAttribute("OrderRealDetail", OrderRealDetail);		
		System.out.println("OrderRealDetail내용 : " + OrderRealDetail + "      ");

	}
	
	

	public  void Getreceipt(HttpServletRequest request, HttpServletResponse response, String customerNo, String orderNo) throws Exception{

		OrderListDAO odao = new OrderListDAO();
		
/*영수증 들어갈때 한번더 정보가 필요해서 넣음 */
		
		StoreBean storereceipt = new StoreBean();
		List<OrderJoinBean> OrderRealDetail = new ArrayList<OrderJoinBean>();
		OrderRealDetail = odao.GetOrderRealDetails(customerNo, orderNo ); // orderlist 테이블 
		

		
		System.out.println("오더 액션 전달받은 고객 number : " + customerNo);
		
		request.setAttribute("OrderRealDetail", OrderRealDetail);	// 영수증에 표시할 전용 에트리뷰트
		
		storereceipt = odao.GetReciptCeoInformation(customerNo, orderNo); // 오버번호 테이블 
	

		request.setAttribute("storereceipt", storereceipt);
		System.out.println("Getreceipt storebean 내용 : " + storereceipt.toString() + "      ");
		

	
		
	}

	
	
	// 리뷰작성 안된 주문목록 가져오기
	public ArrayList<OrderListBean> getUnReviewOrder(HttpServletRequest request, HttpServletResponse response,String customerNo) {
		OrderListDAO oDAO = new OrderListDAO();
		int unReviewPageNo;
		if(request.getParameter("unReviewPageNo")==null){
			unReviewPageNo=1;
		}else{
			unReviewPageNo=Integer.parseInt(request.getParameter("unReviewPageNo"));
		}
		
		return oDAO.getUnReviewOrder(customerNo,unReviewPageNo);
	}

	public int getUnAllReviewCount(HttpServletRequest request, HttpServletResponse response, String customerNo) {
		
		OrderListDAO oDAO = new OrderListDAO();
		return oDAO.getUnAllReviewCount(customerNo);
	}

	public void getOrderListByStoreNo(HttpServletRequest request, HttpServletResponse response, int storeNo) {
		OrderListDAO odao = new OrderListDAO();
		request.setAttribute("orderList", odao.getOrderListByStoreNo(storeNo));
		
	}

	public int customerOrderCancel(int orderNo) {
		
		OrderListDAO oDAO = new OrderListDAO();
		int result =oDAO.customerOrederCancel(orderNo);
		
		return result;
	}

	public DeliveryBean getOrderInfo(String orderNo) {
		
		OrderListDAO oDAO = new OrderListDAO();
		DeliveryBean dbean = new DeliveryBean();
		dbean = oDAO.getOrderInfo(orderNo);
	
		return dbean;
	}

	public void updateDeliveryCheck(String orderNo) {
		
		OrderListDAO oDAO = new OrderListDAO();
		oDAO.updateDeliveryCheck(orderNo);		
		
	}

	public void updateDeliveryCheckA(HttpServletRequest request, HttpServletResponse response) {
	 
		String orderNo=	request.getParameter("orderNo");
		OrderListDAO odao = new OrderListDAO();
		
		odao.updateDeliveryCheckA(orderNo);
		
	}


}
