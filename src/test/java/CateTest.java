import com.tide.bean.Cate;
import com.tide.service.CateService;
import com.tide.service.ProductService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by wengliemiao on 15/12/10.
 */
public class CateTest extends BaseSpring {

    @Autowired
    private ProductService productService ;

    @Autowired
    private CateService cateService;

    @Test()
    public void selectCate() {
        List<Cate> cList = this.cateService.selectAll() ;
        System.out.println("size: "+cList.size());
    }

    @Test
    public void selectCateByPid() {
        List<Cate> cList = this.cateService.selectCateByPid(0) ;
        System.out.println("size: "+cList.size());
    }

    @Test
    public void deleteCate() {
        this.cateService.deleteCate(11);
    }

    @Test
    public void insertCate() {
        Cate c =  new Cate();
        c.setCatename("1234");
        c.setPid(2);
        System.out.println("id before: "+c.getId());
        this.cateService.insertCate(c) ;
        System.out.println("id after: "+c.getId());
    }

    @Test
    public void getCateByCateidTest() {
        List<Cate> cList = this.cateService.getCateByCateid(1);
        for (Cate c : cList) {
            System.out.println(c.getCatename());
        }
    }

    @Test
    public void getFirstCate() {
        Cate c = this.cateService.getCateById(87);
        Cate cate = this.cateService.getFirstCateById(c);
        System.out.println(cate.getCatename());
    }
}
