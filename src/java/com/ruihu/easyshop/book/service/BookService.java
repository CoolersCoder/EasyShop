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
package com.ruihu.easyshop.book.service;
 
import com.ruihu.easyshop.book.dao.BookDao;
import com.ruihu.easyshop.book.domain.Book;
import com.ruihu.easyshop.pager.PageBean;
import java.sql.SQLException;

/**
 *
 * @author Rui Hu
 */
public class BookService {
    private BookDao bookDao = new BookDao();
    
    /**
     * get book id and return book ojbcet
     * @param bid
     * @return Book
     */
    public Book load(String bid)
    {
        try {
            return bookDao.findByBid(bid);
        } catch (SQLException ex) {
           throw new RuntimeException(ex);
        }
                
    }
    
    
    
    /**
     * find category by specifical page
     * @param cid
     * @param pc
     * @return PageBean
     */
    public PageBean<Book> findByCategory(String cid, int pc){
        try {
            return bookDao.findByCategory(cid, pc);
                    } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    
    /**
     * find bookName by specifical page
     * @param bname
     * @param pc
     * @return PageBean
     */
    public PageBean<Book> findByName(String bname, int pc)
    {
          try {
            return bookDao.findByName(bname, pc);
                    } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    /**
     * find authord by specifical page
     * @param author
     * @param pc
     * @return PageBean
     */
      public PageBean<Book> findByAuthor(String author, int pc)
    {
          try {
            return bookDao.findByAuthor(author, pc);
                    } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
      /**
       * find press by specifical page
       * @param press
       * @param pc
       * @return PageBean
       */
         public PageBean<Book> findByPress(String press, int pc)
    {
          try {
            return bookDao.findByPress(press, pc);
                    } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
         
         /**
          * searching by mutiply condition such as book name
          * @param criteria
          * @param pc
          * @return PageBean
          */
           public PageBean<Book> findByCombination(Book criteria, int pc)
    {
          try {
            return bookDao.findByCombination(criteria, pc);
                    } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
         
}
