package top.cheesetree.btx.common.util.comm;

import com.xkzhangsan.time.utils.RegexCache;

import java.util.regex.Pattern;

/**
 * @author wangyq
 * @Date: 2023/2/14
 * @Version:1.0
 * @Description:
 */
public class AllEnums {
    //time_point时间点类型
    public enum TimePointType implements Behaviour {
        TIME_POINT_TYPE_YEAR(1, "年"),
        TIME_POINT_TYPE_SEASON(2, "季"),
        TIME_POINT_TYPE_MONTH(3, "月"),
        TIME_POINT_TYPE_WEEK(4, "周"),
        TIME_POINT_TYPE_DAY(5, "日"),
        TIME_POINT_TYPE_HOUR(6, "时"),
        TIME_POINT_TYPE_MINUTE(7, "分");

        private int value;
        private String name;

        TimePointType(int value, String name) {
            this.value = value;
            this.name = name;
        }

        @Override
        public int getValue() {
            return value;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    // 枚举自定义接口
    public interface Behaviour {
        int getValue();

        String getName();
    }

    public enum TimeNlpRegexEnum implements Behaviour {
        NormMonth("((10)|(11)|(12)|([1-9]))(?=(月|月份))", 0),
        NormCurRelatedDayBeforeAny("^[大]+前天$", 0),
        NormCurRelatedDayAfterAny("^[大]+后天$", 0),

        NormCurRelatedWeekBeforeAny("^[上]+(周|星期)[1-7]?$", 0),
        NormCurRelatedWeekAfterAny("^^[下]+(周|星期)[1-7]?$", 0),
        NormCurRelatedDayAny("[大]", 0),

        NormCurRelatedBeforeAny("[上]", 0),

        NormCurRelatedAfterAny("[下]", 0);

        private String rule;
        private int flags;

        private TimeNlpRegexEnum(String name, int value) {
            this.flags = value;
            this.rule = name;
        }

        @Override
        public int getValue() {
            return flags;
        }

        @Override
        public String getName() {
            return rule;
        }

        public Pattern getPattern() {
            return RegexCache.get(this.rule, this.flags);
        }
    }
}
