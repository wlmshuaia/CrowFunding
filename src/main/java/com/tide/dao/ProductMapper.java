package com.tide.dao;

import com.tide.bean.Product;
import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);
    int getCount();

    Product selectByPrimaryKey(Integer id);

    List<Product> selectAll();
    List<Product> getProductByStatus(Integer status);
    List<Integer> getProductIdByCateId(Integer cateid);

    int setProductStatus(Integer id, Integer status) ;

    int updateByPrimaryKey(Product record);
}