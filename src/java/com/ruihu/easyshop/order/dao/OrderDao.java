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
package com.ruihu.easyshop.order.dao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import ruihu.commons.CommonUtils;
import com.ruihu.easyshop.book.domain.Book;
import com.ruihu.easyshop.order.domain.Order;
import com.ruihu.easyshop.order.domain.OrderItem;
import com.ruihu.easyshop.pager.Expression;
import com.ruihu.easyshop.pager.PageBean;
import com.ruihu.easyshop.pager.PageConstants;
import org.apache.commons.dbutils.handlers.BeanHandler;
import ruihu.jdbc.TxQueryRunner;

public class OrderDao {
	private QueryRunner qr = new TxQueryRunner();
	
        public int findStatus(String oid) throws SQLException {
		String sql = "select status from t_order where oid=?";
		Number number = (Number)qr.query(sql, new ScalarHandler(), oid);
		return number.intValue();
	}
        
        public void updateStatus(String oid, int status) throws SQLException {
		String sql = "update t_order set status=? where oid=?";
		qr.update(sql, status, oid);
	}
        
        public Order load(String oid) throws SQLException {
		String sql = "select * from t_order where oid=?";
		Order order = qr.query(sql, new BeanHandler<Order>(Order.class), oid);
		loadOrderItem(order);//load all order items
		return order;
	}
        
        
        public void add(Order order) throws SQLException {
		/*
		 * 1. insert order item
		 */
		String sql = "insert into t_order values(?,?,?,?,?,?)";
		Object[] params = {order.getOid(), order.getOrdertime(),
				order.getTotal(),order.getStatus(),order.getAddress(),
				order.getOwner().getUid()};
		qr.update(sql, params);
		
		/*
		 * 2. 
                 * loop every order in the item, and make sure every item has a Oject[]
		 */
		sql = "insert into t_orderitem values(?,?,?,?,?,?,?,?)";
		int len = order.getOrderItemList().size();
		Object[][] objs = new Object[len][];
		for(int i = 0; i < len; i++){
			OrderItem item = order.getOrderItemList().get(i);
			objs[i] = new Object[]{item.getOrderItemId(),item.getQuantity(),
					item.getSubtotal(),item.getBook().getBid(),
					item.getBook().getBname(),item.getBook().getCurrPrice(),
					item.getBook().getImage_b(),order.getOid()};
		}
		qr.batch(sql, objs);
	}
        
        
	/**
	 * serching order list by user
	 * @param uid
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Order> findByUser(String uid, int pc) throws SQLException {
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("uid", "=", uid));
		return findByCriteria(exprList, pc);
	}
	
	private PageBean<Order> findByCriteria(List<Expression> exprList, int pc) throws SQLException {
		 
		int ps = PageConstants.ORDER_PAGE_SIZE;//get records in a page
		/*
		 * 2. where by using exprList
		 */
		StringBuilder whereSql = new StringBuilder(" where 1=1"); 
		List<Object> params = new ArrayList<Object>();
		for(Expression expr : exprList) {
			whereSql.append(" and ").append(expr.getName())
				.append(" ").append(expr.getOperator()).append(" ");
			// where 1=1 and bid = ?
			if(!expr.getOperator().equals("is null")) {
				whereSql.append("?");
				params.add(expr.getValue());
			}
		}

		/*
		 * 3. totally records
		 */
		String sql = "select count(*) from t_order" + whereSql;
		Number number = (Number)qr.query(sql, new ScalarHandler(), params.toArray());
		int tr = number.intValue();//get all totally records
		/*
		 * 4.get current records through beanlist
		 */
		sql = "select * from t_order" + whereSql + " order by ordertime desc limit ?,?";
		params.add((pc-1) * ps);// index of current page
		params.add(ps);//search how many records in a page
		
		List<Order> beanList = qr.query(sql, new BeanListHandler<Order>(Order.class), 
				params.toArray());
		// load every items in each order list
		for(Order order : beanList) {
			loadOrderItem(order);
		}
		
		/*
		 * 5. create PageBean
		 */
		PageBean<Order> pb = new PageBean<Order>();
		pb.setBeanList(beanList);
		pb.setPc(pc);
		pb.setPs(ps);
		pb.setTr(tr);
		
		return pb;
	}

	/*
	 * assign order into all OrderItem
	 */
	private void loadOrderItem(Order order) throws SQLException {
		/*
		 * 1. select * from t_orderitem where oid=?
		 * 2. get List<OrderItem>
		 * 3. set Order Object
		 */
		String sql = "select * from t_orderitem where oid=?";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(), order.getOid());
		List<OrderItem> orderItemList = toOrderItemList(mapList);
		
		order.setOrderItemList(orderItemList);
	}

	/**
	 * convert mutiply map into a OrderItem
	 * @param mapList
	 * @return
	 */
	private List<OrderItem> toOrderItemList(List<Map<String, Object>> mapList) {
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		for(Map<String,Object> map : mapList) {
			OrderItem orderItem = toOrderItem(map);
			orderItemList.add(orderItem);
		}
		return orderItemList;
	}

	/*
	 * convert a map into a OrderItem
	 */
	private OrderItem toOrderItem(Map<String, Object> map) {
		OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
		Book book = CommonUtils.toBean(map, Book.class);
		orderItem.setBook(book);
		return orderItem;
	}
}
