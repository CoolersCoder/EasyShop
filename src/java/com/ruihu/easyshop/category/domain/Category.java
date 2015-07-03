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
package com.ruihu.easyshop.category.domain;

import java.util.List;

/**
 *
 * @author Rui Hu
 */
public class Category {
    private String cid;
    private String cname;
    private Category parent;
    private String desc;
    private List<Category> children;

    public String getCid() {
        return cid;
    }

    public String getCname() {
        return cname;
    }

    public Category getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return "Category{" + "cid=" + cid + ", cname=" + cname + ", parent=" + parent + ", desc=" + desc + ", children=" + children + '}';
    }

    public String getDesc() {
        return desc;
    }

    public List<Category> getChildren() {
        return children;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }
    
}
