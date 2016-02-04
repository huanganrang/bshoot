package job.task;

import component.redis.model.CounterType;
import jb.service.BshootCollectServiceI;
import jb.service.BshootCommentServiceI;
import jb.service.BshootPraiseServiceI;
import jb.service.BshootServiceI;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * Created by Zhou Yibing on 2016/2/2.
 */
public class CounterTask extends AbstractTask{
    @Autowired
    private BshootPraiseServiceI bshootPraiseService;
    @Autowired
    private BshootCommentServiceI bshootCommentService;
    @Autowired
    private BshootCollectServiceI bshootCollectService;
    @Autowired
    private BshootServiceI bshootService;

    public void execute(Date begin,Date endDate){
            Map<String, Integer> praiseCount = bshootPraiseService.countGroup(begin, endDate);
            Map<String, Integer> collectCount = bshootCollectService.countGroup(begin, endDate);
            Map<String, Integer> commentCount = bshootCommentService.countGroup(begin, endDate);
            changeCount(praiseCount, CounterType.PRAISE);
            changeCount(collectCount, CounterType.COLLECT);
            changeCount(commentCount, CounterType.COMMENT);
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
