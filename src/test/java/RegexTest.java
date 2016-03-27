import com.tide.utils.HttpUtils;
import org.junit.Test;

/**
 * Created by wengliemiao on 16/1/16.
 */
public class RegexTest extends BaseSpring {
    @Test
    public void regexEmoji() {
        String nickname = "\\xF0\\x9F\\x92\\xA1Lark_Ho";
//        String nickname = "";

//        System.out.println(Integer.parseInt("\tU+1F601", 16));
        System.out.println(HttpUtils.filterEmoji(nickname));

//        System.out.println(filterEmoji(nickname));

//        nickname.replace(u'[^\\u0000-\\uD7FF\\uE000-\\uFFFF]', "");
//        System.out.println(nickname);
    }

}
