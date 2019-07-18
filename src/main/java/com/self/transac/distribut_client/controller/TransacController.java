package com.self.transac.distribut_client.controller;


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

    @ResponseBody
    @RequestMapping( value="/transac" ,method= RequestMethod.GET )
    public String goHttpChat( HttpServletRequest request ){

        disTestService.updateDisInfo();
        return "chat";
    }







}
