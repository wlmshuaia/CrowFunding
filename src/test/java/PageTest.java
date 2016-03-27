import com.tide.bean.Page;
import com.tide.service.PageService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by wengliemiao on 15/12/21.
 */
public class PageTest extends BaseSpring {

    @Autowired
    private PageService pageService ;

    @Test
    public void pageListTest() {
        List<Page> pList = this.pageService.selectAllDisplay();
        for (Page p : pList) {
            System.out.println(p.getName());
        }
    }

    @Test
    public void pageNumTest() {
        String s1 = "page=1";
        String s2 = "type=cloth&page=2";
        String s3 = "status=hhh&page=2&type=cloth";

        System.out.println(s1.indexOf("page"));
        System.out.println(s2.indexOf("page"));
        System.out.println(s3.indexOf("page"));

        String[] sss = s3.split("page=.*?&");
        System.out.println(sss[0]);
        System.out.println(sss[1]);
    }

}
