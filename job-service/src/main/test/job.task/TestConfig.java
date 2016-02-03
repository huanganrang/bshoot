package job.task;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Zhou Yibing on 2015/12/29.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml","classpath:spring-druid.xml","classpath:spring-ehcache.xml","classpath:spring-tasks.xml","classpath:spring-hibernate.xml","classpath:spring-redis.xml","classpath:spring-mvc.xml"})
public class TestConfig {
}
