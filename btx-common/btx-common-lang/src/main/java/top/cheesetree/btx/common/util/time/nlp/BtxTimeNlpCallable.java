package top.cheesetree.btx.common.util.time.nlp;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author van
 * @date 2023/5/23 09:12
 * @description TODO
 */
public class BtxTimeNlpCallable implements Callable<List<BtxTimeNlp>> {
    private String text;

    private String timeBase;

    public BtxTimeNlpCallable(String text, String timeBase) {
        super();
        this.text = text;
        this.timeBase = timeBase;
    }

    @Override
    public List<BtxTimeNlp> call() throws Exception {
        List<BtxTimeNlp> timeNLPList = BtxTimeNlpUtil.parse(text, timeBase);
        return timeNLPList;
    }
}
