package job.task;

import component.redis.service.CounterServiceI;
import jb.service.BshootPraiseServiceI;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by Zhou Yibing on 2016/2/3.
 * ǰ30���������ʱͳ��
 */
public class MonthPraiseTask extends AbstractTask{
    @Autowired
    private BshootPraiseServiceI bshootPraiseService;
    @Autowired
    private CounterServiceI counterService;

    public void execute(){

    }
}
