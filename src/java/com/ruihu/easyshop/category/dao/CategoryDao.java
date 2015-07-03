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
package com.ruihu.easyshop.category.dao;

import com.ruihu.easyshop.category.domain.Category;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import ruihu.commons.CommonUtils;
import ruihu.jdbc.TxQueryRunner;

/**
 *
 * @author Rui Hu
 */
public class CategoryDao {
    private QueryRunner qr= new TxQueryRunner();
    
    /**
     * return all category
     */
    
    public List<Category> findAll() throws SQLException
    {
        String sql="select * from t_category where pid is null";
        List<Map<String,Object>> mapList=qr.query(sql, new MapListHandler());
        List<Category> parents = toCategoryList(mapList);
        
        for (Category parent : parents) {
            List<Category> children = findByParent(parent.getCid());
            parent.setChildren(children);
            
        }
        
        
        return parents;
    }
    
    /**
     * search secondary category
     * @param pid
     * @return 
     */
    public List<Category> findByParent (String pid) throws SQLException{
       String sql = "select * from t_category where pid=?";
        List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(), pid);
        return toCategoryList(mapList);
    }
    
    private Category toCategory(Map<String,Object> map)
    {
       Category category = CommonUtils.toBean(map, Category.class);
		String pid = (String)map.get("pid");
		if(pid != null) { 
			 
			Category parent = new Category();
			parent.setCid(pid);
			category.setParent(parent);
		}
		return category;
    }
    /**
     * Mapping mulitply Category from Map(list<Map>)
     * @param mapList
     * @return 
     */
    private List<Category> toCategoryList (List<Map<String,Object>>mapList)
    {
        List<Category> categoryList= new ArrayList<Category>();
        for (Map<String,Object>map : mapList) {
            Category c= toCategory(map);
            categoryList.add(c);
        }
        return categoryList;
    }
}
