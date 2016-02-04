package job.task;

import jb.service.UserProfileServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;


/**
 * Created by Zhou Yibing on 2016/2/3.
 * 前30天打赏量定时统计
 */
public class MonthPraiseTask{
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserProfileServiceI userProfileService;
    private int limit;
    private long step;

    public long getStep() {
        return step;
    }

    public void setStep(long step) {
        this.step = step;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void work(){
        Date now = new Date();
        logger.info("task fire at "+now);
        execute(new Date(now.getTime()-step*1000),now);
    }

    public void execute(Date begin,Date endDate) {
        Long count = userProfileService.getUserProfileCount();
        if(null==count||0==count) return;
        int page =0;
        while (page * limit < count) {
            Map<String, Integer> monthPraise = userProfileService.countMonthPraise(begin, endDate, page * limit, limit);
            ++page;
            if (null == monthPraise || monthPraise.isEmpty()) continue;
            for (Map.Entry<String, Integer> entry : monthPraise.entrySet()) {
                String userId = entry.getKey();
                Integer c = entry.getValue();
                userProfileService.updateMonthPraise(userId, c);
            }
        }
    }
}
