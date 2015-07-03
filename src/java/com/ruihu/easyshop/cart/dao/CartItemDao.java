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
package com.ruihu.easyshop.cart.dao;

import com.ruihu.easyshop.book.domain.Book;
import com.ruihu.easyshop.cart.domain.CartItem;
import com.ruihu.easyshop.user.domain.User;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import ruihu.commons.CommonUtils;
import ruihu.jdbc.TxQueryRunner;

/**
 *
 * @author Rui Hu
 */
public class CartItemDao {
    private QueryRunner qr= new TxQueryRunner();
    
    	public List<CartItem> loadCartItems(String cartItemIds) throws SQLException {
		 
		Object[] cartItemIdArray = cartItemIds.split(",");
		 
		String whereSql = toWhereSql(cartItemIdArray.length);
		 
		String sql = "select * from t_cartitem c, t_book b where c.bid=b.bid and " + whereSql;
	 
		return toCartItemList(qr.query(sql, new MapListHandler(), cartItemIdArray));
	}
    
    
    private String toWhereSql(int len)
    {
        StringBuilder sb= new StringBuilder("cartItemId in(");
        for (int i = 0; i < len; i++) {
            sb.append("?");
            if(i<len-1){
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }
    
     public CartItem findByCartItemId(String cartItemId) throws SQLException
     {
         String sql= "select * from t_cartItem c, t_book b where c.bid=b.bid and c.cartItemId=?";
         Map<String,Object> map=qr.query(sql, new MapHandler(),cartItemId);
         return toCartItem(map);
     }
    
    public void batchDelete(String cartItemIds) throws SQLException
    {
        Object[] cartItemIdArray = cartItemIds.split(",");
		String whereSql = toWhereSql(cartItemIdArray.length);
		String sql = "delete from t_cartitem where " + whereSql;
		qr.update(sql, cartItemIdArray);
      
    }
    
    
    private CartItem toCartItem(Map<String,Object> map)
    {
        /**
         * maping a cartItem
         */
        if(map==null||map.size()==0){
            return null;
        }
        CartItem cartItem= CommonUtils.toBean(map, CartItem.class);
        Book book= CommonUtils.toBean(map, Book.class);
        User user = CommonUtils.toBean(map, User.class);
        cartItem.setBook(book);
        cartItem.setUser(user);
        return cartItem;
    }
    
    
    private List<CartItem> toCartItemList(List<Map<String,Object>> mapList)
    {
        List<CartItem> cartItemList =  new ArrayList<CartItem>();
        
        for (Map<String, Object> map : mapList) {
            CartItem cartItem= toCartItem(map);
            cartItemList.add(cartItem);
            
        }
        return cartItemList;
    }
    
    public List<CartItem> findByUser(String uid) throws SQLException
    {
        String sql="select *from t_cartitem c, t_book b where c.bid= b.bid and uid=? order by c.orderBy";
        List<Map<String,Object>> mapList= qr.query(sql, new MapListHandler(),uid);
        return toCartItemList(mapList);
                
    }
    
    
    public CartItem findByUidAndBid(String uid, String bid) throws SQLException
    {
        String sql="select * from t_cartitem where uid=? and bid=?";
        Map<String,Object>map= qr.query(sql, new MapHandler(),uid,bid);
        CartItem cartItem = toCartItem(map);
        return cartItem;
        
    }
    /**
     * modify exactly item quantity
     * @param cartItemId
     * @param quantity 
     */
    public void updateQuantity(String cartItemId, int quantity) throws SQLException
    {
        String sql="update t_cartitem set quantity=? where cartItemId=?";
        qr.update(sql, quantity, cartItemId);
    }
    
    public void addCartItem(CartItem cartItem) throws SQLException
    {
        String sql ="insert into t_cartitem(cartItemId, quantity, bid, uid)"+"values(?,?,?,?)";
        Object[] params = {cartItem.getCartItemId(),cartItem.getQuantity(),cartItem.getBook().getBid(),cartItem.getUser().getUid()};
        qr.update(sql,params);   
    }
}
