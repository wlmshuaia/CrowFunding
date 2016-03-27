package com.tide.dao;

import com.tide.bean.ProductDetailimg;
import java.util.List;

public interface ProductDetailimgMapper {
    int deleteByPrimaryKey(Integer id);
    int deleteByProductId(Integer productid);

    int insert(ProductDetailimg record);

    ProductDetailimg selectByPrimaryKey(Integer id);

    List<ProductDetailimg> selectAll();
    List<ProductDetailimg> getProductDetailimgByProductId(Integer productid);

    int updateByPrimaryKey(ProductDetailimg record);
}