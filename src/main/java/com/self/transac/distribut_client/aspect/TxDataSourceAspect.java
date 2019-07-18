package com.self.transac.distribut_client.aspect;



import com.self.transac.distribut_client.transactional.DistributTransaction;
import com.self.transac.distribut_client.transactional.DistributTransactionManager;
import com.self.transac.distribut_client.transactional.connection.DistributConnection;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.sql.Connection;

@Aspect
@Component
public class TxDataSourceAspect {

    @Around( "execution(* javax.sql.DataSource.getConnection(..))")
    public Connection around( ProceedingJoinPoint point){

        try {
            Connection connection = (Connection) point.proceed();
            //LCN 不生 两个方法在同一个事务里，用什么方法进行事务的传递
            DistributTransaction txTransaction = DistributTransactionManager.getCurrent();

            return new DistributConnection( connection ,txTransaction );
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return null;
    }

}
