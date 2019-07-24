package com.self.transac.distribut_client.intercept;

import com.self.transac.distribut_client.transactional.DistributTransactionManager;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 作用同Dubbo ，主要是对各个实例之间 相同组的事务进行传递groupid
 *
 *
 */
@Component
public class RequestInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(@Nullable HttpServletRequest request , @Nullable HttpServletResponse response , @Nullable Object Handler ){

        String groupId = request.getHeader( "groupId" );
        String transactionCount = request.getHeader( "transactionCount" );
        //TODO 设置上个系统产生的事务个数，在此基础上继续累加
        if( null != groupId ){
            DistributTransactionManager.setCurrentGroupId( groupId );
        }
        if( null != transactionCount ){
            DistributTransactionManager.setTransactionCount( Integer.valueOf(
                    null != transactionCount ?transactionCount : "0" ));
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request , HttpServletResponse response , Object Handler,
                           ModelAndView modelAndView ){

    }
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
    }















}
