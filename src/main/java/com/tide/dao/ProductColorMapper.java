package com.tide.dao;

import com.tide.bean.ProductColor;
import java.util.List;

public interface ProductColorMapper {
    int deleteByPrimaryKey(Integer id);
    int deleteByProductId(Integer productid);

    int insert(ProductColor record);

    ProductColor selectByPrimaryKey(Integer id);

    List<ProductColor> selectAll();
    List<ProductColor> getProductColorByProductId(Integer productid);

    int updateByPrimaryKey(ProductColor record);
}