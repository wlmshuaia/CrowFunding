import com.tide.bean.Admin;
import com.tide.service.AdminService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by wengliemiao on 15/11/29.
 */
public class AdminTest extends BaseSpring {

    @Autowired
    private AdminService adminService;

    @Test
    public void adminTest() {
        List<Admin> aList = this.adminService.selectAll() ;
        for (Admin a : aList) {
            System.out.println(a.getAdminname());
        }
    }

    @Test
    public void getObjByNPTest() {
        Admin a = this.adminService.getObjByNP("admin", "admin");

        System.out.println(a.getAdminname());
    }
}

