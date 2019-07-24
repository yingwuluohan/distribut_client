package com.self.transac.distribut_client.service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Created by yingwuluohan on 2019/7/23.
 * @Company 北京云知声技术有限公司
 */
public abstract class BaseService {


    public Map<String,String> requestHeader(String groupId , int transactionCount ){
        Map<String,String> header = new HashMap<>();
        header.put("groupId", groupId);
        header.put("transactionCount", transactionCount+"" );

        return header;
    }
}
