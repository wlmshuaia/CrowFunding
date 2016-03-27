package com.tide.controller.admin;

import com.github.pagehelper.PageHelper;
import com.mysql.jdbc.Connection;
import com.tide.bean.*;
import com.tide.controller.BaseAction;
import com.tide.service.AttrService;
import com.tide.service.CateService;
import com.tide.service.ProductService;
import com.tide.utils.DataUtils;
import com.tide.utils.FileUtils;
import com.tide.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wengliemiao on 15/12/7.
 */
@Controller
@RequestMapping(value = "/admin/Product")
public class AProductAction extends BaseAction {

    @Autowired
    private ProductService productService;

    @Autowired
    private AttrService attrService;

    @Autowired
    private CateService cateService ;

    /**
     * 所有商品列表
     * @param model
     * @return
     */
    @RequestMapping(value = "/selectAll")
    public String selectAll(String status, Integer page, Model model) {
        Map<String, Object> pageMap = this.getPageMap(page, "Product", productService, model);
        PageHelper.startPage(Integer.parseInt(pageMap.get("curr").toString()), DataUtils.PAGE_NUM);

        List<Product> pList = this.productService.getProductByStatus(status);
        model.addAttribute("productList", pList);
        return "admin/product/productList";
    }

    /**
     * 返回json格式商品列表
     * @return
     */
    @RequestMapping(value = "/selectAllJson")
    @ResponseBody
    public Object selectAllJson() {
        return this.productService.selectAll();
    }

    /**
     * 添加商品视图
     *
     * @return
     */
    @RequestMapping(value = "/addProduct")
    public String addProduct(Model model) {
        List<Size> sList = this.attrService.selectSize() ;
        List<Color> cList = this.attrService.selectColor() ;

        //  获取所有一级分类
        List<Cate> cateList = this.cateService.selectCateByPid(0) ;

        model.addAttribute("cateList", cateList) ;
        model.addAttribute("colorList", cList) ;
        model.addAttribute("sizeList", sList) ;
        return "admin/product/addProduct";
    }

    /**
     * 删除商品
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteProduct")
    @ResponseBody
    public String deleteProduct(Integer id, HttpSession session) {
        if(this.productService.delete(id, session) != 0) {
            return "success";
        }
        return "删除失败";
    }

    /**
     * 批量删除
     * @param key
     * @return
     */
    @RequestMapping(value = "/batchDelete")
    @ResponseBody
    public String batchDelete(String key, HttpSession session) {
        return this.batchDelete(key, productService, session);
    }

    /**
     * 商品上架
     * @param id
     * @return
     */
    @RequestMapping(value = "/onSale")
    @ResponseBody
    public String onSale(Integer id) {
        if(this.productService.setProductStatus(id, DataUtils.PRO_ONSALE_I) != 0){
            return "success";
        }
        return "上架失败";
    }

    /**
     * 商品下架
     * @param id
     * @return
     */
    @RequestMapping(value = "/offSale")
    @ResponseBody
    public String offSale(Integer id) {
        if(this.productService.setProductStatus(id, DataUtils.PRO_INWAREHOUSE_I) != 0){
            return "success";
        }
        return "下架失败";
    }

    /**
     * 添加商品表单处理
     *  1.保存图片: 商品主图
     *  2.保存 Product 对象
     *  3.保存颜色信息 和 规格主图
     *  4.保存尺码信息
     *  5.保存图片: 详图
     *  6.保存图片: 尺码表
     *
     * @return
     */
    @RequestMapping(value = "/addProductHandle", method = RequestMethod.POST)
    public String addProductHandle(@ModelAttribute("product") Product product, // 商品基本信息
                                   String[] color,
                                   String[] size,
                                   @RequestParam("mainimgFile") MultipartFile mainimgFile, // 主图
                                   @RequestParam("specifyimgs") MultipartFile[] specifyimgs, // 规格主图
                                   @RequestParam("detailimgs") MultipartFile[] detailimgs,  // 详图
                                   @RequestParam("sizetables") MultipartFile[] sizetables,  // 尺码表
                                   HttpServletRequest request,
                                   Model model,
                                   HttpSession session) {
        // 1.保存图片: 商品主图
        if(!mainimgFile.isEmpty()) {
            // 存储图片
            String imgUrl = FileUtils.saveFile(mainimgFile, session);
            // 保存主图地址
            product.setMainimg(imgUrl);
        } else {
            System.out.println("mainimg is null...");
        }

        // 2.保存 Product 对象
        if(product != null) {
            product.setStatus(0); // 状态: 0-下架, 1-上架
            this.productService.insert(product);
        } else {
            model.addAttribute("商品基本信息数据为空!");
            return "admin/error";
        }

        // 3.保存颜色信息 和 规格主图
        if(color != null) {
            for (int i = 0; i < color.length; i++) {
                ProductColor pc = new ProductColor();

                // 对应商品id
                pc.setProductid(product.getId());

                // 颜色名称
                String colorname = this.attrService.getColorname(Integer.parseInt(color[i]));
                pc.setColor(colorname);

                // 色卡
                Color col = this.attrService.selectColorById(Integer.parseInt(color[i]));
                pc.setColorcard(col.getColorcard());

                // 规格图片地址
                if(!specifyimgs[i].isEmpty()) {
                    // 存储规格图片
                    String savePath = FileUtils.saveFile(specifyimgs[i], session);
                    pc.setColorimg(savePath);
                } else {
                    System.out.println("specifyimg is null...");
                }

                // 保存 product_color 对象
                this.productService.insertProductColor(pc);
            }
        }

        Connection conn = null ;

        // 4.保存尺码信息
        if(size != null) {
            for(String s : size) {
                ProductSize ps = new ProductSize() ;

                // 商品id
                ps.setProductid(product.getId());

                // 尺码名称
                String sizename = this.attrService.getSizename(Integer.parseInt(s));
                ps.setSize(sizename);

                // 重量
                Object weight = request.getParameter("weight-"+s);
                ps.setWeight(Float.parseFloat(weight.toString()));

                // 保存 product_size 对象
                this.productService.insertProductSize(ps);
            }
        }

        // 5.保存图片: 详图
        if(detailimgs != null) {
            for(MultipartFile detailimg : detailimgs) {
                if(!detailimg.isEmpty()) {
                    ProductDetailimg pd = new ProductDetailimg() ;

                    // 商品id
                    pd.setProductid(product.getId());

                    // 上传文件原名
                    String sName = detailimg.getOriginalFilename();
                    pd.setImgname(sName);

                    // 存储图片
                    // 生成新的图片名称: 日期+从小到大顺序(从1开始)
                    String saveDirectoryPath = FileUtils.saveFile(detailimg, session);
                    pd.setProimgurl(saveDirectoryPath);

                    // 图片大小
                    pd.setSize(String.valueOf(detailimg.getSize()));

                    // 上传图片时间
                    pd.setTime(String.valueOf(new Date().getTime()));

                    // 保存 product_detailimg
                    this.productService.insertProductDetailimg(pd);

                } else {
                    System.out.println("detailimg is null...");
                }
            }
        }


        // 6.保存图片: 尺码表
        if(sizetables != null) {
            for(MultipartFile sizetable : sizetables) {
                if(!sizetable.isEmpty()) {
                    ProductSizetable pst = new ProductSizetable();

                    // 商品id
                    pst.setProductid(product.getId());

                    // 存储尺码表图片
                    String saveSizePath = FileUtils.saveFile(sizetable, session);
                    pst.setSizetable(saveSizePath);

                    // 保存 product_sizetable
                    this.productService.insertProductSizetable(pst);

                } else {
                    System.out.println("sizetable is null...");
                }
            }
        }

        return "redirect:/admin/Product/selectAll.do";
    }

    /**
     * 获取商品信息: 商品 颜色
     * @param id
     * @return
     */
    @RequestMapping(value = "/getObjInfo")
    @ResponseBody
    public Object getObjInfo(Integer id) {
        return this.productService.getObjColorInfo(id);
    }


    /**
     * 所有商品属性列表
     * @return
     */
    @RequestMapping(value = "/selectProAttr")
    public String selectProAttr(Model model) {
        List<Color> cList = this.attrService.selectColor() ;
        List<Size> sList = this.attrService.selectSize() ;
        model.addAttribute("colorList", cList) ;
        model.addAttribute("sizeList", sList) ;
        return "admin/product/attrList" ;
    }

    /**
     * 添加颜色表单处理
     * @return
     */
    @RequestMapping(value = "addColorHandle")
    public String addColorHandle(String colorname, String colorcard) {
        Color c = new Color() ;
        c.setColorname(colorname);
        c.setColorcard(colorcard);
        this.attrService.insertColor(c);
        return "redirect:/admin/Product/selectProAttr.do" ;
    }

    /**
     * 添加尺码颜色处理
     * @return
     */
    @RequestMapping(value = "addSizeHandle")
    public String addSizeHandle(String sizename) {
        Size s = new Size() ;
        s.setSizename(sizename);
        this.attrService.insertSize(s);
        return "redirect:/admin/Product/selectProAttr.do" ;
    }

    /**
     * 根据id获取属性信息
     * @param id
     * @param type
     * @return
     */
    @RequestMapping(value = "/getAttrById")
    @ResponseBody
    public Object getAttrById(Integer id, String type) {
        return this.attrService.getAttrById(id, type) ;
    }

    /**
     * 删除属性
     * @param id
     * @param type: color 或 size
     * @return
     */
    @RequestMapping(value = "/deleteAttr")
    @ResponseBody
    public String deleteAttr(Integer id, String type) {
//        System.out.println("type: "+type);

        if(DataUtils.ATTR_COLOR.equals(type)) { // 颜色
            this.attrService.deleteColor(id);
        } else if(DataUtils.ATTR_SIZE.equals(type)) { // 尺码
            this.attrService.deleteSize(id);
        }

        return JsonUtils.getJson("msg", "success") ;
    }

    /**
     * 更新属性
     * @return
     */
    @RequestMapping(value = "/updateAttr")
    @ResponseBody
    public String updateAttr(@RequestBody Map<String, String> map) {
        if(this.attrService.updateAttr(map) != 0) {
            return JsonUtils.getJson("msg", "success");
        }
        return JsonUtils.getJson("msg", "error");
    }

    /**
     * 分类列表
     * @return
     */
    @RequestMapping(value = "/cateList")
    public String cateList(Model model) {
        List<Cate> cList = this.cateService.selectCateByPid(0);
        model.addAttribute("cateList", cList);
        return "admin/product/cateList" ;
    }

    /**
     * 根据 pid 获取分类列表
     * @param pid
     * @return
     */
    @RequestMapping(value = "/getCateByPid")
    @ResponseBody
    public Object getCateByPid(Integer pid) {
        return this.cateService.selectCateByPid(pid) ;
    }

    /**
     * 添加分类
     * @param cate
     * @return
     */
    @RequestMapping(value = "/addCate")
    @ResponseBody
    public String addCate(@RequestBody Cate cate) {
        this.cateService.insertCate(cate);
        return cate.getId().toString();
//        return JsonUtils.getJson("msg", cate.getId().toString());
    }

    /**
     * 删除分类
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteCate")
    @ResponseBody
    public String deleteCate(Integer id) {
        this.cateService.deleteCate(id);
        return "success";
//        return JsonUtils.getJson("msg", "success") ;
    }

    /**
     * 获取商品信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/getProductById")
    @ResponseBody
    public Object getProductById(Integer id) {
        return this.productService.getProductInfo(id);
    }

}
