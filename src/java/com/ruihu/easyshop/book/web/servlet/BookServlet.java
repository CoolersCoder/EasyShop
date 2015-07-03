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
package com.ruihu.easyshop.book.web.servlet;

import com.ruihu.easyshop.book.domain.Book;
import com.ruihu.easyshop.book.service.BookService;
import com.ruihu.easyshop.pager.PageBean;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ruihu.commons.CommonUtils;
import ruihu.servlet.BaseServlet;

/**
 *
 * @author Rui Hu
 */
public class BookServlet extends BaseServlet {
   private BookService bookService = new BookService();
   /**
    * get current page number
    * @param req
    * @return 
    */
   private int getPc(HttpServletRequest req)
   {
       int pc=1;
       String param=req.getParameter("pc");
       if(param!=null && !param.trim().isEmpty())
       {
           try {
             pc= Integer.parseInt(param);
           } catch (Exception e) {
               
           }
 
       }
       return pc;
   }
    private String getUrl(HttpServletRequest req)
    {
        //   ->>> /EasyShop/BookServlet
        String url=req.getRequestURI() + "?" + req.getQueryString();
        /**
         * if url hava pc parmeter , cut it out, if not dont do befor.
         */
        int index= url.lastIndexOf("&pc=");
        if(index != -1)
        {
            url = url.substring(0, index);
        }
        return url;
    }
    public String findByCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    
        /**
         * get pc,get url,get searching conditon,
         */
        int pc= getPc(req);
        //get url
        
        String url=getUrl(req);
         
        String cid= req.getParameter("cid");
        
        PageBean<Book> pb= bookService.findByCategory(cid, pc);
        
        
        pb.setUrl(url);
        req.setAttribute("pb", pb);
        return "f:/jsps/book/list.jsp";
        
         
    }

    /**
     * get current page code
     * @param req
     * @return 
     */
    private int getPageCode(HttpServletRequest req) {
		int pc = 1;//default value is 1
		String str = req.getParameter("pc");
		if(str != null) {
			pc = Integer.parseInt(str);
		}
		return pc;
	}
    
    /**
     * Searching by name
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException 
     */
  public String findByBname(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		 
		int pc = getPageCode(req);
		 
		String bname = req.getParameter("bname");
	 
		PageBean<Book> pb = bookService.findByName(bname, pc);
		 
		pb.setUrl(getUrl(req));
		 
		req.setAttribute("pb", pb);
		return "f:/jsps/book/list.jsp";
	}
	
	/**
	 * Searching by author
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByAuthor(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	 
		int pc = getPageCode(req);
	 
		String author = req.getParameter("author");
	 
		PageBean<Book> pb = bookService.findByAuthor(author, pc);
		 
		pb.setUrl(getUrl(req));
		 
		req.setAttribute("pb", pb);
		return "f:/jsps/book/list.jsp";
	}
	
	/**
	 * Searching by Press
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByPress(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	 
		int pc = getPageCode(req);
	 
		String press = req.getParameter("press");
	 
		PageBean<Book> pb = bookService.findByPress(press, pc);
 
		pb.setUrl(getUrl(req));
	 
		req.setAttribute("pb", pb);
		return "f:/jsps/book/list.jsp";
	}
	
	/**
	 * searching by mutiply condition
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByCombination(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		 
		int pc = getPageCode(req);
	 
		Book book = CommonUtils.toBean(req.getParameterMap(), Book.class);
	 
		PageBean<Book> pb = bookService.findByCombination(book, pc);
 
		pb.setUrl(getUrl(req));
	 
		req.setAttribute("pb", pb);
		return "f:/jsps/book/list.jsp";
	}
	
    public String load(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       String bid= req.getParameter("bid");
       Book book=bookService.load(bid);
       req.setAttribute("book", book);
       return "f:/jsps/book/desc.jsp";
    }
   
    
    
    
    
   
}
