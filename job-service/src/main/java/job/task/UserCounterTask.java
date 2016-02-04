package job.task;

import component.redis.model.CounterType;
import component.redis.service.CounterServiceI;
import jb.service.BshootServiceI;
import jb.service.UserAttentionServiceI;
import jb.service.UserProfileServiceI;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * Created by Zhou Yibing on 2016/2/4.
 * 用户关注数，粉丝数，打赏数定时统计
 */
public class UserCounterTask extends AbstractTask{

    @Autowired
    private UserAttentionServiceI userAttentionService;
    @Autowired
    private BshootServiceI bshootService;
    @Autowired
    private UserProfileServiceI userProfileService;
    @Autowired
    private CounterServiceI counterService;

    @Override
    public void execute(Date begin, Date end) {
        Map<String, Integer> attCount = userAttentionService.attCountGroup(begin,end);
        Map<String, Integer> fansCount = userAttentionService.fansCountGroup(begin,end);
        Map<String, Integer> praiseCount = bshootService.praiseCountGroup(begin,end);
        changeCount(attCount, CounterType.ATT);
        changeCount(fansCount, CounterType.BEATT);
        changeCount(praiseCount, CounterType.PRAISE);
    }

    private void changeCount(Map<String,Integer> count,CounterType counterType){
        if(null==count||count.isEmpty()) return;
        for(Map.Entry<String,Integer> entry:count.entrySet()) {
            String userId = entry.getKey();
            Integer c = entry.getValue();
            userProfileService.updateUserProfileCount(userId, c, counterType);
            counterService.setCount(userId,counterType,c,24*60*60L);
        }
    }
}
