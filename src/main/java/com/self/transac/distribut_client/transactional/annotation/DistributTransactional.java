package com.self.transac.distribut_client.transactional.annotation;


import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE })
public @interface DistributTransactional {

    /** 事务开始 */
    boolean isStart() default false;
    /** 事务结束 */
    boolean isEnd() default true;


}
