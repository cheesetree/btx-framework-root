package top.cheesetree.btx.common.util.time.nlp;

import com.xkzhangsan.time.constants.Constant;
import com.xkzhangsan.time.formatter.DateTimeFormatterUtil;
import com.xkzhangsan.time.nlp.TextAnalysis;
import com.xkzhangsan.time.nlp.TextPreprocess;
import com.xkzhangsan.time.nlp.TimeContext;
import com.xkzhangsan.time.utils.CollectionUtil;
import com.xkzhangsan.time.utils.GlobalThreadPool;
import com.xkzhangsan.time.utils.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author van
 * @date 2023/5/23 09:05
 * @description TODO
 */
public class BtxTimeNlpUtil {
    private BtxTimeNlpUtil() {
    }

    /**
     * 时间自然语言分析
     *
     * @param text 待分析文本
     * @return 结果列表
     */
    public static List<BtxTimeNlp> parse(String text) {
        return parse(text, null);
    }

    /**
     * 时间自然语言分析，使用线程池并发执行
     *
     * @param text 待分析文本
     * @return 结果列表
     * @throws InterruptedException 被中止异常
     * @throws ExecutionException   执行异常
     * @throws TimeoutException     超时异常
     */
    public static List<BtxTimeNlp> parseConcurrent(String text)
            throws InterruptedException, ExecutionException, TimeoutException {
        return parse(text, null, 0, null, null);
    }

    /**
     * 时间自然语言分析，使用线程池并发执行，默认3秒超时
     *
     * @param text 待分析文本
     * @return 结果列表
     * @throws InterruptedException 被中止异常
     * @throws ExecutionException   执行异常
     * @throws TimeoutException     超时异常
     */
    public static List<BtxTimeNlp> parseConcurrentDefaultTime(String text)
            throws InterruptedException, ExecutionException, TimeoutException {
        return parse(text, null, Constant.TIME_NLP_TIMEOUT, TimeUnit.MILLISECONDS, null);
    }

    /**
     * 时间自然语言分析，使用线程池并发执行，设置超时时间和单位
     *
     * @param text    待分析文本
     * @param timeout 超时时间
     * @param unit    超时时间单位
     * @return 结果列表
     * @throws InterruptedException 被中止异常
     * @throws ExecutionException   执行异常
     * @throws TimeoutException     超时异常
     */
    public static List<BtxTimeNlp> parseConcurrent(String text, long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        return parse(text, null, timeout, unit, null);
    }

    /**
     * 时间自然语言分析，使用线程池并发执行，设置超时时间和单位，使用自定义线程池
     *
     * @param text            待分析文本
     * @param timeout         超时时间
     * @param unit            超时时间单位
     * @param executorService 自定义线程池
     * @return 结果列表
     * @throws InterruptedException 被中止异常
     * @throws ExecutionException   执行异常
     * @throws TimeoutException     超时异常
     */
    public static List<BtxTimeNlp> parseConcurrent(String text, long timeout, TimeUnit unit,
                                                   ExecutorService executorService) throws InterruptedException,
            ExecutionException, TimeoutException {
        return parse(text, null, timeout, unit, executorService);
    }

    /**
     * 时间自然语言分析
     *
     * @param text     待分析文本
     * @param timeBase 指定时间
     * @return 结果列表
     */
    public static List<BtxTimeNlp> parse(String text, String timeBase) {
        // 文本预处理
        if (StringUtil.isEmpty(text)) {
            return null;
        }
        text = TextPreprocess.preprocess(text);
        if (StringUtil.isEmpty(text)) {
            return null;
        }

        //时间名词匹配分析
        List<String> timeStrs = TextAnalysis.getInstance().analysis(text);
        if (CollectionUtil.isEmpty(timeStrs)) {
            return null;
        }

        //解析
        List<BtxTimeNlp> timeNLPList = new ArrayList<>(timeStrs.size());
        /**时间上下文： 前一个识别出来的时间会是下一个时间的上下文，用于处理：周六3点到5点这样的多个时间的识别，第二个5点应识别到是周六的。*/
        TimeContext timeContext = new TimeContext();
        if (StringUtil.isEmpty(timeBase)) {
            timeBase = DateTimeFormatterUtil.format(new Date(), "yyyy-MM-dd-HH-mm-ss");
        }
        String oldTimeBase = timeBase;
        timeContext.setTimeBase(timeBase);
        timeContext.setOldTimeBase(oldTimeBase);
        for (int j = 0; j < timeStrs.size(); j++) {
            if (StringUtil.isEmpty(timeStrs.get(j))) {
                break;
            }
            BtxTimeNlp timeNLP = new BtxTimeNlp(timeStrs.get(j), TextAnalysis.getInstance(), timeContext);
            timeNLPList.add(timeNLP);
            timeContext = timeNLP.getTimeContext();
        }

        /**过滤无法识别的字段*/
        List<BtxTimeNlp> timeNLPListResult = BtxTimeNlp.filterTimeUnit(timeNLPList);
        return timeNLPListResult;
    }

    /**
     * 时间自然语言分析，使用线程池并发执行
     *
     * @param text            待分析文本
     * @param timeBase        指定时间
     * @param timeout         超时时间
     * @param unit            超时时间单位
     * @param executorService 自定义线程池
     * @return 结果列表
     * @throws InterruptedException 被中止异常
     * @throws ExecutionException   执行异常
     * @throws TimeoutException     超时异常
     */
    public static List<BtxTimeNlp> parse(String text, String timeBase, long timeout, TimeUnit unit,
                                         ExecutorService executorService) throws InterruptedException,
            ExecutionException, TimeoutException {
        List<BtxTimeNlp> result = null;
        if (StringUtil.isEmpty(text)) {
            return result;
        }

        if (timeout > 0) {
            if (executorService != null) {
                result = executorService.submit(new BtxTimeNlpCallable(text, timeBase)).get(timeout, unit);
            } else {
                result =
                        GlobalThreadPool.getGlobalThreadPool().submit(new BtxTimeNlpCallable(text, timeBase)).get(timeout,
                        unit);
            }
        } else {
            if (executorService != null) {
                result = executorService.submit(new BtxTimeNlpCallable(text, timeBase)).get();
            } else {
                result = GlobalThreadPool.getGlobalThreadPool().submit(new BtxTimeNlpCallable(text, timeBase)).get();
            }
        }
        return result;
    }
}
