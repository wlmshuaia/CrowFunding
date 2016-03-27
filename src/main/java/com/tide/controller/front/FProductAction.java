package com.tide.controller.front;

import com.tide.bean.Cate;
import com.tide.bean.Product;
import com.tide.bean.ProductDetailimg;
import com.tide.service.CateService;
import com.tide.service.ProductService;
import com.tide.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by wengliemiao on 15/12/17.
 */
@Controller
@RequestMapping(value = "/front/Product")
public class FProductAction {

    @Autowired
    private ProductService productService;

    @Autowired
    private CateService cateService;

    /**
     * 获取所有商品接口
     * @return
     */
    @RequestMapping(value = "/getAllProducts")
    @ResponseBody
    public Object getAllProducts(HttpServletRequest request) {
        return this.productService.getProductMap(request, DataUtils.PRO_ONSALE_I);
    }

    /**
     * 获取商品对象
     * @param id
     * @return
     */
    @RequestMapping(value = "/getObjById")
    @ResponseBody
    public Object getObjById(Integer id){
        return this.productService.getObjById(id);
    }

    /**
     * 获取商品信息: 商品和颜色
     * @param id
     * @return
     */
    @RequestMapping(value = "/getObjColorInfo")
    @ResponseBody
    public Object getObjColorInfo(Integer id) {
        return this.productService.getObjColorInfo(id);
    }

    /**
     * 获取商品信息: 商品和尺码
     * @param id
     * @return
     */
    @RequestMapping(value = "/getObjSizeInfo")
    @ResponseBody
    public Object getObjSizeInfo(Integer id) {
        return this.productService.getObjSizeInfo(id);
    }

    /**
     * 获取商品信息
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/productInfo")
    public String productInfo(Integer id, Model model) {
        List<ProductDetailimg> pdList = this.productService.getProductDetailimg(id);
        model.addAttribute("pdList", pdList);

        Product product = this.productService.getObjById(id);
        Cate cate = this.cateService.getFirstCateById(this.cateService.getCateById(product.getCateid()));
        if(DataUtils.PROJECT_TYPE_CHINESE_COMMONWEAL.equals(cate.getCatename())) {
            model.addAttribute("product", product);
            model.addAttribute("isCloth", "0");
        }

        return "front/product/productInfo";
    }

    /**
     * 获取尺码表
     * @param id
     * @return
     */
    @RequestMapping(value = "/getObjSizetables")
    @ResponseBody
    public Object getObjSizetables(Integer id) {
        return this.productService.getProductSizetables(id);
    }

    /**
     * 获取洗护信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/getObjCare")
    @ResponseBody
    public Object getObjCare(Integer id) {
        Product p = this.productService.getObjById(id);
        return p.getCare();
//        return JsonUtils.getJson("msg", p.getCare());
    }
}
