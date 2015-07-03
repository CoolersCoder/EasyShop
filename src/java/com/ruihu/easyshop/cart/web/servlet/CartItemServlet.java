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
package com.ruihu.easyshop.cart.web.servlet;

import com.ruihu.easyshop.book.domain.Book;
import com.ruihu.easyshop.cart.domain.CartItem;
import com.ruihu.easyshop.cart.service.CartItemService;
import com.ruihu.easyshop.user.domain.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ruihu.commons.CommonUtils;
import ruihu.servlet.BaseServlet;

/**
 *
 * @author Rui Hu
 */
public class CartItemServlet extends BaseServlet {

     private CartItemService cartItemService= new CartItemService();

   public String loadCartItems(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		 
		String cartItemIds = req.getParameter("cartItemIds");
		double total = Double.parseDouble(req.getParameter("total"));
		
		List<CartItem> cartItemList = cartItemService.loadCartItems(cartItemIds);
		 
		req.setAttribute("cartItemList", cartItemList);
		req.setAttribute("total", total);
                req.setAttribute("cartItemIds", cartItemIds);
		return "f:/jsps/cart/showitem.jsp";
	}
    public String myCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("sessionUser");
        String uid= user.getUid();
        
        List<CartItem> cartItemList = cartItemService.myCart(uid);
        req.setAttribute("cartItemList", cartItemList);
        return "f:/jsps/cart/list.jsp";
    }

    
    public  String updateQuantity(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cartItemId= req.getParameter("cartItemId");
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        CartItem cartItem=cartItemService.updateQuantity(cartItemId, quantity);
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"quantity\"").append(":").append(cartItem.getQuantity());
        sb.append(",");
        sb.append("\"subtotal\"").append(":").append(cartItem.getSubtotal());
        sb.append("}");
        
        System.out.print(sb);
        resp.getWriter().print(sb);
        
      
        return null;
    }
    
    
    public  String add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       CartItem cartItem= CommonUtils.toBean(req.getParameterMap(), CartItem.class);
       Book book = CommonUtils.toBean(req.getParameterMap(), Book.class);
       User user= (User) req.getSession().getAttribute("sessionUser");
       cartItem.setBook(book);
       cartItem.setUser(user);
       
       cartItemService.add(cartItem);
       return  myCart(req, resp);
    }
     
        public  String batchDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
            String cartItemIds= req.getParameter("cartItemIds");
            cartItemService.batchDelete(cartItemIds);
            return myCart(req, resp);
        }
    
    
    
}
