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
package com.ruihu.easyshop.cart.service;

import com.ruihu.easyshop.cart.dao.CartItemDao;
import com.ruihu.easyshop.cart.domain.CartItem;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ruihu.commons.CommonUtils;

/**
 *
 * @author Rui Hu
 */
public class CartItemService {
    private CartItemDao cartItemDao= new CartItemDao();
    
    public List<CartItem> loadCartItems(String cartItemIds) {
        try {
            return cartItemDao.loadCartItems(cartItemIds);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
 
    public List<CartItem> myCart(String uid)
    {
        try {
            return cartItemDao.findByUser(uid);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void add(CartItem cartItem) {
        try {
            CartItem _cartItem=cartItemDao.findByUidAndBid(cartItem.getUser().getUid(), cartItem.getBook().getBid());
            if(_cartItem== null){ //if there was no this item, add it
                cartItem.setCartItemId(CommonUtils.uuid());
                cartItemDao.addCartItem(cartItem);
            }else//if it shas ,modify quantity
            {
                 int quantity = cartItem.getQuantity()+_cartItem.getQuantity();
                 cartItemDao.updateQuantity(_cartItem.getCartItemId(), quantity);
                 
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);   
        }
    }
    
    public void batchDelete(String cartItemIds)
    {
        try {
            cartItemDao.batchDelete(cartItemIds);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        
    }
    
    public CartItem updateQuantity(String cartItemId, int quantity)
    {
        try {
            cartItemDao.updateQuantity(cartItemId, quantity);
            return cartItemDao.findByCartItemId(cartItemId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
       
            
}
 
