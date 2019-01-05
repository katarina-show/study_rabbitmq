package com.sjw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 库存系统controller
 */
@Controller
public class DepotController {

    @RequestMapping("/depotIndex")
    public String userReg(){
        return "index";
    }
}
