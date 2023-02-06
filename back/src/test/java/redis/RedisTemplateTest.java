//package redis;
//
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.SetOperations;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.web.puppylink.config.RedisConfig;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@ContextConfiguration(classes = RedisConfig.class)
//public class RedisTemplateTest {
//
//    @Autowired
//    private RedisTemplate<String, MailDto> redisTemplate;
//
//    private static final String PREFIX = "email:";
//    @Test
//    public void RedisConnectionTest() {
//
//        ValueOperations<String, MailDto> valueOperations =  redisTemplate.opsForValue();
//        String key = "setKey";
//
//        // valueOperations.set(key, new MailDto("test03@naver.com",1231231));
//
//        MailDto value = valueOperations.get(key);
//        if ( value instanceof MailDto ) {
//            System.out.println(value);
//            System.out.println("연결되었습니다.");
//        } else {
//            System.out.println("연결되지 않았습니다.");
//        }
//    }
//
//    @Test
//    public void RedisCountBySet() {
//        SetOperations<String, MailDto> setOperations = redisTemplate.opsForSet();
//        String key = "111dfsdf";
//
//        // setOperations.add("key", new MailDto("test01@naver.com",1234123));
//
//        List<MailDto> list = setOperations.pop(key,5);
//
//        for(MailDto mail : list) {
//            System.out.println(mail);
//        }
//    }
//
//}
