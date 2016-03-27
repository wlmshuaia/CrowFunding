import com.tide.bean.*;
import com.tide.service.AttrService;
import com.tide.service.LabelService;
import com.tide.service.ProductService;
import com.tide.utils.DataUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by wengliemiao on 15/12/9.
 */
public class ProductTest extends BaseSpring {

    @Autowired
    public ProductService productService ;

    @Autowired
    private AttrService attrService;

    @Autowired
    private LabelService labelService;

    @Test
    public void onSale() {
        this.productService.setProductStatus(11, DataUtils.PRO_ONSALE_I);
    }

    @Test
    public void iColor() {
        Color c = new Color() ;
        c.setColorname("白白白");
        this.attrService.insertColor(c);
    }

    @Test
    public void iSize() {
        Size s = new Size() ;
        s.setSizename("MMM");
        this.attrService.insertSize(s);
    }

    @Test
    public void cList() {
        List<Color> cList = this.attrService.selectColor() ;
        for(Color c : cList){
            System.out.println(c.getColorname());
        }
    }

    @Test
    public void insertPro() {
        Product p = new Product();
        p.setProname("233");

        System.out.println(p.getId());
        this.productService.insert(p);
        System.out.println(p.getId());

    }

    @Test
    public void selectColorByName() {
        Color c = this.attrService.selectColorByName("001白色");
        System.out.println(c.getColorcard());
    }

    @Test
    public void selectName(){
        System.out.println(this.attrService.getSizename(4));

        System.out.println(this.attrService.getColorname(1));
    }

    @Test
    public void insertProductSize() {
        ProductSize ps = new ProductSize();
        ps.setProductid(1);
        ps.setSize("L");
        ps.setWeight(Float.parseFloat("0.24"));
        this.productService.insertProductSize(ps);
//        System.out.println(Float.parseFloat("0.24"));
    }

    @Test
    public void selectLabelDisplay() {
        List<Label> lList = this.labelService.selectAllDisplay();
        System.out.println(lList.size());
    }
}
