package com.self.transac.distribut_client.common.modle;

import lombok.Data;
import org.springframework.http.HttpRequest;

import java.io.Serializable;
import java.sql.Connection;

/**
 * @Created by yingwuluohan on 2019/7/21.
 * @Company 北京云知声技术有限公司
 */
@Data
public class TransactionInfo implements Serializable {

    private HttpRequest request;

    private Connection connection;


    private Long id;

    private String info;



}
