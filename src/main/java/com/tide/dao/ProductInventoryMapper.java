package com.tide.dao;

import com.tide.bean.ProductInventory;
import java.util.List;

public interface ProductInventoryMapper {
    int deleteByPrimaryKey(Integer id);
    int deleteByProductId(Integer productid);

    int insert(ProductInventory record);

    ProductInventory selectByPrimaryKey(Integer id);

    List<ProductInventory> selectAll();

    int updateByPrimaryKey(ProductInventory record);
}