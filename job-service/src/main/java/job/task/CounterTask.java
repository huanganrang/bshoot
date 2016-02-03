package job.task;

import component.redis.model.CounterType;
import component.redis.service.CounterServiceI;
import jb.service.BshootCollectServiceI;
import jb.service.BshootCommentServiceI;
import jb.service.BshootPraiseServiceI;
import jb.service.BshootServiceI;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Zhou Yibing on 2016/2/2.
 */
public class CounterTask{
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BshootPraiseServiceI bshootPraiseService;
    @Autowired
    private BshootCommentServiceI bshootCommentService;
    @Autowired
    private BshootCollectServiceI bshootCollectService;
    @Autowired
    private BshootServiceI bshootService;
    @Autowired
    private CounterServiceI counterService;
    private Date begin;
    private long step;
    private AtomicBoolean isWorking=new AtomicBoolean(false);

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

    public void execute() throws JobExecutionException {
        Date now = new Date();
        logger.info("task fire at "+now);
        if(isWorking.get()){
            logger.info("the task has already working,ingnore this executed.");
            return;
        }
        if(!isWorking.compareAndSet(false,true)) return;
        try {

            long nowTime = now.getTime();
            if(begin==null)
                begin=now;
            long endTime = begin.getTime()+step*1000;
            Date endDate = null;
            while(endTime<nowTime) {
                endDate = new Date(endTime);
                Map<String, Integer> praiseCount = bshootPraiseService.countGroup(begin, endDate);
                Map<String, Integer> collectCount = bshootCollectService.countGroup(begin, endDate);
                Map<String, Integer> commentCount = bshootCommentService.countGroup(begin, endDate);
                changeCount(praiseCount, CounterType.PRAISE);
                changeCount(collectCount, CounterType.COLLECT);
                changeCount(commentCount, CounterType.COMMENT);
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

    private void changeCount(Map<String,Integer> count,CounterType counterType){
        if(null==count||count.isEmpty()) return;
        for(Map.Entry<String,Integer> entry:count.entrySet()) {
            String bshootId = entry.getKey();
            Integer c = entry.getValue();
            bshootService.updateCountByType(bshootId, c,counterType);
            counterService.setCount(bshootId,counterType,c,24*60*60L);
        }
    }
}
