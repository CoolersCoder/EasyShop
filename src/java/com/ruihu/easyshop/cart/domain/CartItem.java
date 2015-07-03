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
package com.ruihu.easyshop.cart.domain;

import com.ruihu.easyshop.book.domain.Book;
import com.ruihu.easyshop.user.domain.User;
import java.math.BigDecimal;

/**
 *
 * @author Rui Hu
 */
public class CartItem {
    private String cartItemId;
    private int quantity;
    private Book book;
    private User user;

    
    //add subtotel 
    /**
     * must using String type construction.
     * @return 
     */
    public double getSubtotal()
    {
        BigDecimal b1=new BigDecimal(book.getCurrPrice()+"");
        BigDecimal b2=new BigDecimal(quantity +"");
        BigDecimal b3= b1.multiply(b2);
        return b3.doubleValue();
    }
    
    public String getCartItemId() {
        return cartItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "CartItem{" + "cartItemId=" + cartItemId + ", quantity=" + quantity + ", book=" + book + ", user=" + user + '}';
    }

    public void setCartItemId(String cartItemId) {
        this.cartItemId = cartItemId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setUser(User user) {
        this.user = user;
    }
   
}
