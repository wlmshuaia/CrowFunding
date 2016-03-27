package com.tide.dao;

import com.tide.bean.ProductSize;
import java.util.List;

public interface ProductSizeMapper {
    int deleteByPrimaryKey(Integer id);
    int deleteByProductId(Integer productid);

    int insert(ProductSize record);

    ProductSize selectByPrimaryKey(Integer id);

    List<ProductSize> selectAll();
    List<ProductSize> getSizeById(Integer productid);

    int updateByPrimaryKey(ProductSize record);
}