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
package com.ruihu.easyshop.book.dao;

import com.ruihu.easyshop.book.domain.Book;
import com.ruihu.easyshop.category.domain.Category;
import com.ruihu.easyshop.pager.Expression;
import com.ruihu.easyshop.pager.PageConstants;
import com.ruihu.easyshop.pager.PageBean;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.jsp.PageContext;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import ruihu.commons.CommonUtils;
import ruihu.jdbc.TxQueryRunner;

/**
 *
 * @author Rui Hu
 */
public class BookDao {
     private QueryRunner qr = new TxQueryRunner();
     /**
      * Search by Bid
      * @param bid
      * @return
      * @throws SQLException 
      */
     public Book findByBid(String bid) throws SQLException{
         String sql="select * from t_book where bid=?";
         Map<String,Object> map= qr.query(sql, new MapHandler(),bid);
         Book book = CommonUtils.toBean(map, Book.class);
         Category category= CommonUtils.toBean(map, Category.class);
         book.setCategory(category);
         return book;
     }
     /**
      * 
      * @param cid
      * @return
      * @throws SQLException 
      */
     	public int findCountByCategory(String cid) throws SQLException {
		String sql = "select count(1) from t_book where cid=?";
		Number cnt = (Number)qr.query(sql, new ScalarHandler(), cid);
		return cnt == null ? 0 : cnt.intValue();
	}
     
     /**
      * regular search function
      * @param exprList
      * @param pc
      * @return 
      */
     private PageBean<Book> findByCriteria(List<Expression> exprList,int pc) throws SQLException{
         //1 get every record from page ps
         //2 get totol record tr
         //3 get beanList
         //4 get PageBean return
         /**
          * 1 get ps
          */
         int ps=PageConstants.BOOK_PAGE_SIZE;
         /**
          * 2 total record number
          */
         StringBuilder whereSql=new StringBuilder(" where 1=1");
         List<Object> params= new ArrayList<Object>(); //replace ? in the sql query
         for (Expression expr : exprList) {
             whereSql.append(" and ").append(expr.getName()).append(" ").append(expr.getOperator()).append(" ");
             // where 1=1 and bid = 
             if(!expr.getOperator().equals("is null"))
             {
                 // where 1=1 and bid = ?
                 whereSql.append("?");
                 //parameter
                 params.add(expr.getValue());
             }
         }
         String sql="select count(*) from t_book"+whereSql;
         Number number= (Number) qr.query(sql, new ScalarHandler(),params.toArray());
         int tr= number.intValue();
         
         /**
          * get beanList means current record
          */
         sql="select * from t_book" + whereSql + " order by orderBy limit ?,?";
         params.add((pc-1)*ps); //current page first index
         params.add(ps); //how many record in a page
         
         List<Book> beanList = qr.query(sql, new BeanListHandler<Book>(Book.class),params.toArray());
         
         /**
          * create PageBean and set parameter
          */
         PageBean<Book> pb= new PageBean<Book>();
         //withou PageBean url , it can be set by servlet
         pb.setBeanList(beanList);
         pb.setPc(pc);
         pb.setPs(ps);
         pb.setTr(tr);
         
       
        
         return pb;
         
     }
     /**
      * find book by Category
      * @param cid
      * @param pc
      * @return
      * @throws SQLException 
      */
         public PageBean<Book> findByCategory(String cid, int pc) throws SQLException
         {
             List<Expression> exprList= new ArrayList<Expression>();
             exprList.add(new Expression("cid", "=", cid));
             return findByCriteria(exprList, pc);
             
          }
         /**
          * using bookname searching
          * @param bname
          * @param pc
          * @return
          * @throws SQLException 
          */
         public PageBean<Book> findByName(String bname, int pc) throws SQLException
         {
             List<Expression> exprList= new ArrayList<Expression>();
             exprList.add(new Expression("bname", "like","%"+ bname + "%"));
             return findByCriteria(exprList, pc);
             
          }
         /**
          * searching by author
          * @param author
          * @param pc
          * @return
          * @throws SQLException 
          */
           public PageBean<Book> findByAuthor(String author, int pc) throws SQLException
         {
             List<Expression> exprList= new ArrayList<Expression>();
             exprList.add(new Expression("author", "like","%"+ author + "%"));
             return findByCriteria(exprList, pc);
             
          }
           /**
            * searching by press
            * @param press
            * @param pc
            * @return
            * @throws SQLException 
            */
              public PageBean<Book> findByPress(String press, int pc) throws SQLException
         {
             List<Expression> exprList= new ArrayList<Expression>();
             exprList.add(new Expression("press", "like","%"+ press + "%"));
             return findByCriteria(exprList, pc);
             
          }
                /**
                 * multiply conbination searching
                 * @param Combination
                 * @param pc
                 * @return
                 * @throws SQLException 
                 */ 
              public PageBean<Book> findByCombination(Book criteria, int pc) throws SQLException
         {
             List<Expression> exprList= new ArrayList<Expression>();
             exprList.add(new Expression("bname", "like","%"+ criteria.getBname() + "%"));
             exprList.add(new Expression("author", "like","%"+ criteria.getAuthor()+ "%"));
             exprList.add(new Expression("press", "like","%"+ criteria.getPress()+ "%"));
             return findByCriteria(exprList, pc);
             
          }
              
 
}
