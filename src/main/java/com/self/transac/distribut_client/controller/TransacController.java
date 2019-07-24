package com.self.transac.distribut_client.controller;


import com.self.transac.distribut_client.client.TransactionClient;
import com.self.transac.distribut_client.service.DisTestService;
import com.self.transac.distribut_client.service.TransacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/dis")
@Controller
public class TransacController {


    @Autowired
    private DisTestService disTestService;
    @Autowired
    private TransacService transacService;


    @ResponseBody
    @RequestMapping( value="/transac" ,method= RequestMethod.GET )
    public String goHttpChat( HttpServletRequest request ){

        disTestService.updateDisInfo();
        return "chat";
    }

    /**
     * 与服务端建立连接
     * @return
     */
    @ResponseBody
    @RequestMapping( value="/initclient" ,method= RequestMethod.GET )
    public String initNettyClient() throws Exception {
        try {

            System.out.println( " RPC get 请求，线程是:" + Thread.currentThread().getName() );
            transacService.rpcUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception( e );
        }
        return "success";
    }





}
