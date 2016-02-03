package job.task;

import jb.service.BshootPraiseServiceI;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Zhou Yibing on 2016/2/3.
 */
public class TestTask{

  public static void main(String[] args) throws InterruptedException {
    String[] paths = new String[]{"classpath:spring.xml","classpath:spring-druid.xml","classpath:spring-ehcache.xml","classpath:spring-tasks.xml","classpath:spring-hibernate.xml","classpath:spring-redis.xml","classpath:spring-mvc.xml"};
    ApplicationContext context = new ClassPathXmlApplicationContext(paths);
    CounterTask counterTask = (CounterTask) context.getBean("counterTask");
    System.out.println(counterTask.getStep());
    System.out.println(counterTask.getBegin());
    BshootPraiseServiceI bshootPraiseService =  context.getBean(BshootPraiseServiceI.class);
    System.out.println(bshootPraiseService);
  }
}
