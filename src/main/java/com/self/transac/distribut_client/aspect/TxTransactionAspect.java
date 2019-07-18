package com.self.transac.distribut_client.aspect;



import com.self.transac.distribut_client.transactional.DistributTransaction;
import com.self.transac.distribut_client.transactional.DistributTransactionManager;
import com.self.transac.distribut_client.transactional.TransactionType;
import com.self.transac.distribut_client.transactional.annotation.DistributTransactional;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class TxTransactionAspect implements Ordered {


    @Around("@annotation(com.self.transac.distribut_client.transactional.annotation.DistributTransactional)")
    public void invoke(ProceedingJoinPoint point ){

        MethodSignature signature =(MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        DistributTransactional txTransactional = method.getAnnotation(DistributTransactional.class);

        System.out.println( "Aspect:"+ txTransactional.isStart() );

        String groupId = "";
        if( txTransactional.isStart() ){
            groupId = DistributTransactionManager.createTxTransactionGroup();
        }
        DistributTransaction txTransaction = DistributTransactionManager.createLbTransaction( groupId );

        try {
            // 走spring的逻辑 ，比spring优先级低
            point.proceed();

            DistributTransactionManager.addLbTransaction( txTransaction , txTransactional.isEnd() , TransactionType.comit);
        } catch (Throwable throwable) {
            DistributTransactionManager.addLbTransaction( txTransaction , txTransactional.isEnd() ,TransactionType.rollback );
            throwable.printStackTrace();
        }

    }


    @Override
    public int getOrder() {
        return 10000;
    }
}
