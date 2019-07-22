package com.self.transac.distribut_client.controller;


import com.self.transac.distribut_client.client.TransactionClient;
import com.self.transac.distribut_client.service.DisTestService;
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
    private TransactionClient transactionClient;

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
    public String initNettyClient(){
        try {
            transactionClient.start( "127.0.0.1" ,8888 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "success";
    }





}
