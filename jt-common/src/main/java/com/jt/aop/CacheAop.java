package com.jt.aop;

import com.jt.annotation.CacheFind;
import com.jt.util.ObjectMapperUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.params.SetParams;

import java.util.Arrays;

/**
 * @Author WL
 * @Date 2020-10-13 20:00
 * @Version 1.0
 * 缓存切入
 */
@Component
@Aspect
public class CacheAop {


    //private Jedis jedis;
//    private JedisSentinelPool jedisSentinelPool;
    @Autowired
    private JedisCluster jedis;


    @Around("@annotation(cacheFind)")
    public Object around(ProceedingJoinPoint jp, CacheFind cacheFind) {

//        Jedis jedis = jedisSentinelPool.getResource();
        String key = cacheFind.key();
        Object[] arrays = jp.getArgs(); // 参数
        key += "::" + Arrays.toString(arrays);

        Object result = null;
        if (jedis.exists(key)) {
            String json = jedis.get(key);
            // 返回方法的全类名
            MethodSignature methodSignature = (MethodSignature) jp.getSignature();
            //  获取返回值类型
            Class returnType = methodSignature.getReturnType();
            result = ObjectMapperUtil.toObject(json, returnType);
            System.out.println("AOP实现查找缓存");
        } else {
            try {
                result = jp.proceed();
                String json = ObjectMapperUtil.toJSON(result);
                if (cacheFind.seconds() > 0) {
                    SetParams setParams = new SetParams();
                    setParams.nx().ex(cacheFind.seconds());
                    jedis.set(key, json, setParams);
                } else {
                    jedis.set(key, json);
                }
                System.out.println("AOP执行数据库操作");
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                throw new RuntimeException(throwable);
            }
        }
        // 池中的对象用完以后返还
//        jedis.close();
        return result;
    }

/*

    // 切入点表达式可以不写,直接写在通知中
    //  Pointcut是切入点表达式的引用，适用于多个通知共用
    @Pointcut("bean(itemCatServiceImpl)")   // bean中传入的是类名的首字母小写
    public void pointCut() {
    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint){
        System.out.println("before-----------");
        Signature signature = joinPoint.getSignature();
        System.out.println("获取目标方法："+signature);
        String methodName=signature.getName();
        System.out.println("获取目标方法名："+methodName);
        String className=signature.getDeclaringTypeName();
        System.out.println("获取类的全类名:"+className);


        System.out.println("方法路径："+className+"."+methodName);
        Class targetClass = joinPoint.getTarget().getClass();
        System.out.println("目标类型:"+targetClass);
        Object[] args = joinPoint.getArgs();
        System.out.println("参数类型："+Arrays.toString(args));



    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) {
        long t1 = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long t2 = System.currentTimeMillis();
            System.out.println("执行时间："+(t2-t1));
            return result;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new RuntimeException("异常");
        }
    }



*/


/*

    @Pointcut("execution(* com.jt.service..*.*(..))")
    public void pointCut() {
    }


    @Before("pointCut()")
    public void before() {
        System.out.println("前置通知");
    }
*/


}
