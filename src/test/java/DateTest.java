import com.tide.utils.DateUtils;
import org.junit.Test;

import java.util.Date;

/**
 * Created by wengliemiao on 16/1/25.
 */
public class DateTest extends BaseSpring {

    @Test
    public void getDaysBetween() {
        Integer leftdays = DateUtils.getDaysBetween(new Date(), "2016-02-02 18:00:00");
        System.out.println(leftdays);
    }

    @Test
    public void getHoursBetween() {
        Integer leftHours = DateUtils.getMinutesBetween("2016-03-10 15:51:00", new Date());
        System.out.println(leftHours);
    }
}
