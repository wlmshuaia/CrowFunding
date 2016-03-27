package com.tide.service;

import com.tide.bean.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 商品管理
 * Created by wengliemiao on 15/12/7.
 */
public interface ProductService extends BaseService {
    // product
    public List<Product> selectAll() ;
    public List<Product> selectOnSale();
    public List<Product> getProductByStatus(String status);
    public int insert(Product product);
    public int setProductStatus(Integer id, Integer status) ;
    public Product getObjById(Integer id);
    // 返回商品信息: 商品 颜色等
    public Object getObjColorInfo(Integer id);
    public Map<String, Object> getObjSizeInfo(Integer id);
    public List<Object> getProductMap(HttpServletRequest request, Integer status);
    public Map<String, Object> getProductInfo(Integer id);

    // product_color
    public int insertProductColor(ProductColor pc) ;
    public List<ProductColor> getColorByProId(Integer id);

    // product_size
    public int insertProductSize(ProductSize ps) ;

    // product_detailimg
    public int insertProductDetailimg(ProductDetailimg pd);
    public List<ProductDetailimg> getProductDetailimg(Integer productid);
    // product_sizetable
    public int insertProductSizetable(ProductSizetable pst);
    public List<ProductSizetable> getProductSizetables(Integer productid);
}
