import com.tide.bean.Label;
import com.tide.service.LabelService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wengliemiao on 15/12/12.
 */
public class LabelTest extends BaseSpring {

    @Autowired
    private LabelService labelService;

    @Test
    public void addLabel() {
        Label label = new Label();
        label.setLabelname("Java");
        label.setType(0);
        label.setStatus(0);
        this.labelService.insert(label);
    }

}
