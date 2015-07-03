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
package com.ruihu.easyshop.category.service;

import com.ruihu.easyshop.category.dao.CategoryDao;
import com.ruihu.easyshop.category.domain.Category;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rui Hu
 */
public class CategoryService {
    private CategoryDao categoryDao= new CategoryDao();
    /**
     * search all category
     * @return 
     */
    public List<Category> findAll()
    {
        try {
           return categoryDao.findAll();
                    } catch (SQLException ex) {
             throw new RuntimeException(ex);
        }
    }
    
}
