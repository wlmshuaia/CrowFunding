package com.tide.service.impl;

import com.tide.bean.*;
import com.tide.dao.*;
import com.tide.service.CateService;
import com.tide.service.ProductService;
import com.tide.utils.DataUtils;
import com.tide.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wengliemiao on 15/12/7.
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductColorMapper productColorMapper;

    @Autowired
    private ProductSizetableMapper productSizetableMapper;

    @Autowired
    private ProductSizeMapper productSizeMapper ;

    @Autowired
    private ProductInventoryMapper productInventoryMapper;

    @Autowired
    private CateService cateService;

    @Override
    public int insertProductSizetable(ProductSizetable pst) {
        return this.productSizetableMapper.insert(pst);
    }

    @Override
    public Object getObjColorInfo(Integer id) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<ProductColor> pcList = this.getColorByProId(id);

        Product product = this.getObjById(id);

        map.put("pcList", pcList);
        map.put("product", product);
        return map;
    }

    @Override
    public Map<String, Object> getProductInfo(Integer id) {
        Map<String, Object> map = new HashMap<>();

        Product p = this.getObjById(id);
        List<ProductColor> pcList = this.getColorByProId(id);
        List<ProductSize> psList = this.productSizeMapper.getSizeById(id);
        Cate cate = this.cateService.getCateById(p.getCateid());
        List<ProductSizetable> pstList = this.productSizetableMapper.getObjSizetables(id);

        map.put("cate", cate.getCatename());
        map.put("product", p);
        map.put("pcList", pcList);
        map.put("psList", psList);
        map.put("pstList", pstList);
        return map;
    }

    @Override
    public Map<String, Object> getObjSizeInfo(Integer id) {
        Map<String, Object> pMap = new HashMap<>();
        Product product = this.getObjById(id);
        List<ProductSize> psList = this.productSizeMapper.getSizeById(id);
        pMap.put("product", product);
        pMap.put("psList", psList);
        return pMap;
    }

    @Override
    public Product getObjById(Integer id) {
        return this.productMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ProductColor> getColorByProId(Integer id) {
        return this.productColorMapper.getProductColorByProductId(id);
    }

    @Autowired
    private ProductDetailimgMapper productDetailimgMapper;

    @Override
    public int insertProductDetailimg(ProductDetailimg pd) {
        return this.productDetailimgMapper.insert(pd);
    }

    @Override
    public int insertProductSize(ProductSize ps) {
        return this.productSizeMapper.insert(ps);
    }

    @Override
    public List<Product> selectAll() {
        return this.productMapper.selectAll();
    }

    @Override
    public int insert(Product product) {
        return this.productMapper.insert(product);
    }

    @Override
    public int insertProductColor(ProductColor pc) {
        return this.productColorMapper.insert(pc);
    }

    public boolean saveFile() {
        return false;
    }

    @Override
    public List<ProductDetailimg> getProductDetailimg(Integer productid) {
        return this.productDetailimgMapper.getProductDetailimgByProductId(productid);
    }

    @Override
    public List<ProductSizetable> getProductSizetables(Integer productid) {
        return this.productSizetableMapper.getObjSizetables(productid);
    }

    @Override
    public int delete(Integer id, HttpSession session) {
        Product product = this.productMapper.selectByPrimaryKey(id);
        Integer proid = product.getId();

        // 删除 product_color 规格主图
        List<ProductColor> pcList = this.productColorMapper.getProductColorByProductId(proid);
        for (ProductColor pc : pcList) {
            FileUtils.deleteFile(FileUtils.getFileRealPath(session, pc.getColorimg()));
        }
        // 删除 product_color
        this.productColorMapper.deleteByProductId(proid);

        // 删除 product_detailimg 详图图片
        List<ProductDetailimg> pdList = this.productDetailimgMapper.getProductDetailimgByProductId(proid);
        for (ProductDetailimg pd : pdList) {
            FileUtils.deleteFile(FileUtils.getFileRealPath(session, pd.getProimgurl()));
        }
        // 删除 product_detailimg
        this.productDetailimgMapper.deleteByProductId(proid);

        // 删除 product_inventory
        this.productInventoryMapper.deleteByProductId(proid);

        // 删除 product_size
        this.productSizeMapper.deleteByProductId(proid);

        // 删除 product_sizetable 尺码表图片
        List<ProductSizetable> pstList = this.productSizetableMapper.getObjSizetables(proid);
        for (ProductSizetable pst : pstList) {
            FileUtils.deleteFile(FileUtils.getFileRealPath(session, pst.getSizetable()));
        }
        // 删除 product_sizetable
        this.productSizetableMapper.deleteByProductId(proid);

        // 删除 product 主图图片
        FileUtils.deleteFile(FileUtils.getFileRealPath(session, product.getMainimg()));
        // 删除 product
        this.productMapper.deleteByPrimaryKey(id);

        return 1;
    }

    @Override
    public List<Product> selectOnSale() {
        return this.productMapper.getProductByStatus(DataUtils.PRO_ONSALE_I);
    }

    @Override
    public int setProductStatus(Integer id, Integer status) {
        return this.productMapper.setProductStatus(id, status);
    }

    @Override
    public List<Product> getProductByStatus(String status) {
        List<Product> pList;

        if(DataUtils.PRO_ONSALE_S.equals(status)) { // 在售中
            pList = this.productMapper.getProductByStatus(DataUtils.PRO_ONSALE_I);
        } else if(DataUtils.PRO_INWAREHOUSE_S.equals(status)) { // 在仓库
            pList = this.productMapper.getProductByStatus(DataUtils.PRO_INWAREHOUSE_I);
        } else { // 全部商品
            pList = this.productMapper.selectAll();
        }

        return pList;
    }

    @Override
    public int getCount() {
        return this.productMapper.getCount();
    }

    @Override
    public List<Object> getProductMap(HttpServletRequest request, Integer status) {
        List<Object> resPList = new ArrayList<>();
        List<Product> pList = this.productMapper.getProductByStatus(status);

        String sIpAddr = "http://" +request.getServerName()+"/";

        System.out.println(sIpAddr);

        for (int i = 0; i < pList.size(); i ++) {
            Product p = pList.get(i);

            p.setMainimg(sIpAddr + p.getMainimg());

            resPList.add(p);
        }
        return resPList;
    }
}
