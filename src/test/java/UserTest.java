import com.tide.bean.FocusUserProject;
import com.tide.bean.User;
import com.tide.dao.FocusUserProjectMapper;
import com.tide.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by wengliemiao on 15/12/7.
 */
public class UserTest extends BaseSpring {

    @Autowired
    private UserService userService ;

    @Autowired
    private FocusUserProjectMapper focusUserProjectMapper;

    @Test
    public void selectAllTest() {
        List<User> uList = this.userService.selectAll();
        for(User u : uList) {
            System.out.println(u.getNickname());
        }
    }

    @Test
    public void userCount() {
        System.out.println(this.userService.getCount());
    }

    @Test
    public void getUserLabels() {
        Integer uid = 1;
        List<String> lnList = this.userService.getUserLabels(uid);
        for (String s : lnList) {
            System.out.println(s);
        }
    }

    @Test
    public void getFocusProject() {
        List<FocusUserProject> fupList = this.focusUserProjectMapper.getFocusProject(1);
        FocusUserProject fup = this.focusUserProjectMapper.getFocusProjectByUP(1, 34);


        System.out.println(fupList.size());
        System.out.println(fup);
    }
}
