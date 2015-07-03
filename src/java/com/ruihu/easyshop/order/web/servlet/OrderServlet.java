/*
 * Copyright (C) 2015 Rui Hu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.ruihu.easyshop.order.web.servlet;
import com.ruihu.easyshop.cart.domain.CartItem;
import com.ruihu.easyshop.cart.service.CartItemService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ruihu.easyshop.order.domain.Order;
import com.ruihu.easyshop.order.domain.OrderItem;
import com.ruihu.easyshop.order.service.OrderService;
import com.ruihu.easyshop.pager.PageBean;
import com.ruihu.easyshop.user.domain.User;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ruihu.commons.CommonUtils;
import ruihu.servlet.BaseServlet;

public class OrderServlet extends BaseServlet {
	private OrderService orderService = new OrderService();
        private CartItemService cartItemService = new CartItemService();
        
        public String paymentPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("order", orderService.load(req.getParameter("oid")));
		return "f:/jsps/order/pay.jsp";
	}
        
        public String load(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String oid = req.getParameter("oid");
		Order order = orderService.load(oid);
		req.setAttribute("order", order);
		String btn = req.getParameter("btn");//which btn click and call a function
		req.setAttribute("btn", btn);
		return "/jsps/order/desc.jsp";
	}
        	public String confirm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String oid = req.getParameter("oid");
		/*
		 * Oder Status
		 */
		int status = orderService.findStatus(oid);
		if(status != 3) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", "Status error！");
			return "f:/jsps/msg.jsp";
		}
		orderService.updateStatus(oid, 4);//set status as a suceess
		req.setAttribute("code", "success");
		req.setAttribute("msg", "Sucess，Thank you for buying from EeasyShoping！");
		return "f:/jsps/msg.jsp";		
	}
        
	public String cancel(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String oid = req.getParameter("oid");
		/*
		 * Check order status
		 */
		int status = orderService.findStatus(oid);
		if(status != 1) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", "Status error！");
			return "f:/jsps/msg.jsp";
		}
		orderService.updateStatus(oid, 5);//cancel status
		req.setAttribute("code", "success");
		req.setAttribute("msg", "Do you really want to cancel your ordering！");
		return "f:/jsps/msg.jsp";		
	}
	/**
	 * Get current page number
	 * @param req
	 * @return
	 */
	private int getPc(HttpServletRequest req) {
		int pc = 1;
		String param = req.getParameter("pc");
		if(param != null && !param.trim().isEmpty()) {
			try {
				pc = Integer.parseInt(param);
			} catch(RuntimeException e) {}
		}
		return pc;
	}
	
	/**
	 * get usrl for divide page navigation
	 * @param req
	 * @return
	 */
	/*
	 * http://localhost:8080/goods/BookServlet?methed=findByCategory&cid=xxx&pc=3
	 * /EasyShop/BookServlet + methed=findByCategory&cid=xxx&pc=3
	 */
	private String getUrl(HttpServletRequest req) {
		String url = req.getRequestURI() + "?" + req.getQueryString();
		int index = url.lastIndexOf("&pc=");
		if(index != -1) {
			url = url.substring(0, index);
		}
		return url;
	}
	
	/**
	 * My Order
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String myOrders(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. get current page：if there is a parameter，we will use it. Default value is 1.
		 */
		int pc = getPc(req);
		/*
		 * 2. get url：...
		 */
		String url = getUrl(req);
		/*
		 * 3. get User from session
		 */
		User user = (User)req.getSession().getAttribute("sessionUser");
		
		/*
		 * 4. Using current page and cid to service#findByCategory and then get PageBean
		 */
		PageBean<Order> pb = orderService.myOrders(user.getUid(), pc);
		/*
		 * 5. set url for pageBean，sevae PageBean，forword to /jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/jsps/order/list.jsp";
	}
        public String createOrder(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. get every id in a cart item and then search it
		 */
		String cartItemIds = req.getParameter("cartItemIds");
		List<CartItem> cartItemList = cartItemService.loadCartItems(cartItemIds);
		/*
		 * 2. create Order
		 */
		Order order = new Order();
		order.setOid(CommonUtils.uuid());
		order.setOrdertime(String.format("%tF %<tT", new Date()));//order status
		order.setStatus(1);//set status，1 means not pay 
		order.setAddress(req.getParameter("address"));//set address
		User owner = (User)req.getSession().getAttribute("sessionUser");
		order.setOwner(owner);//set order owner
		
		BigDecimal total = new BigDecimal("0");
		for(CartItem cartItem : cartItemList) {
			total = total.add(new BigDecimal(cartItem.getSubtotal() + ""));
		}
		order.setTotal(total.doubleValue());//set totally
		
		/*
		 * 3. create List<OrderItem>
		 * every OrderItem user one CartItem
		 */
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		for(CartItem cartItem : cartItemList) {
			OrderItem orderItem = new OrderItem();
			orderItem.setOrderItemId(CommonUtils.uuid());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setSubtotal(cartItem.getSubtotal());
			orderItem.setBook(cartItem.getBook());
			orderItem.setOrder(order);
			orderItemList.add(orderItem);
		}
		order.setOrderItemList(orderItemList);
		
		/*
		 * 4. Call service to do operation
		 */
		orderService.createOrder(order);
		
		// delete cart item
		cartItemService.batchDelete(cartItemIds);
		/*
		 * 5. save order ，forward ordersucc.jsp
		 */
		req.setAttribute("order", order);
		return "f:/jsps/order/ordersucc.jsp";
	}
}