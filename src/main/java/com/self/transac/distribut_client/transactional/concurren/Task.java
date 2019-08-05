package com.self.transac.distribut_client.transactional.concurren;

import com.self.transac.distribut_client.transactional.DistributTransaction;

import java.sql.Connection;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Task {

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();



    public void waitTask( ){

        try {
            lock.lock();
            condition.await();
            long begin = System.currentTimeMillis();
            System.out.println( "解锁--------------:" + ( System.currentTimeMillis() - begin)/1000  );

        }catch (Exception e ){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }

    }
    public void signalTask( ){
        lock.lock();
        condition.signal();
        lock.unlock();

    }










}
