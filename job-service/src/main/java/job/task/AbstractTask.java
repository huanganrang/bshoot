package job.task;

import component.redis.service.CounterServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Zhou Yibing on 2016/2/3.
 */
public abstract class AbstractTask {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected Date begin;
    protected long step;
    private String countTime;
    private AtomicBoolean isWorking=new AtomicBoolean(false);
    @Autowired
    protected CounterServiceI counterService;

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

    public String getCountTime() {
        return countTime;
    }

    public void setCountTime(String countTime) {
        this.countTime = countTime;
    }

    public void init(){
        Object ct  = counterService.get(countTime);
        if(null!=ct){
            if(null==begin||begin.getTime()!=ct) begin = new Date(Long.valueOf((String) ct));
        }else{
            if(null==begin) begin = new Date();
        }
    }

    public void work(){
        Date now = new Date();
        logger.info("task fire at "+now);
        if(isWorking.get()){
            logger.info("the task has already working,ingnore this executed.");
            return;
        }
        if(!isWorking.compareAndSet(false,true)) return;
        try {
            long nowTime = now.getTime();
            long endTime = begin.getTime()+step*1000;
            Date endDate;
            while(endTime<nowTime) {
                endDate = new Date(endTime);
                execute(begin,endDate);
                counterService.set(countTime, endTime+"");//在redis中记录计数开始时间，便于在计数器重启后拿去开始时间戳，恢复执行，也可人工修改，动态设定计数开始时间
                begin=endDate;
                endTime = endTime+step*1000;
                Thread.sleep(200);
            }
            for(;;) {
                if (isWorking.compareAndSet(true, false))
                    break;
            }
            logger.info("the task has finished at "+new Date());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public abstract void execute(Date begin,Date end);
}
