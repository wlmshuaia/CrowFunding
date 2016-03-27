package com.tide.dao;

import com.tide.bean.ProductSizetable;
import java.util.List;

public interface ProductSizetableMapper {
    int deleteByPrimaryKey(Integer id);
    int deleteByProductId(Integer productid);

    int insert(ProductSizetable record);

    ProductSizetable selectByPrimaryKey(Integer id);

    List<ProductSizetable> selectAll();
    List<ProductSizetable> getObjSizetables(Integer productid);

    int updateByPrimaryKey(ProductSizetable record);
}