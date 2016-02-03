package job.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Zhou Yibing on 2016/2/3.
 */
public abstract class AbstractTask {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected Date begin;
    protected long step;

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public long getStep() {
        return step;
    }

    public void setStep(long step) {
        this.step = step;
    }

    public abstract void execute();
}
