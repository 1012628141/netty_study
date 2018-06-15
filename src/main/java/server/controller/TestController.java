package server.controller;

import server.annonation.RequstMappingTest;
import server.annonation.RestControllerTest;

@RestControllerTest
public class TestController {


    public String test(){
        return "haha";
    }

    @RequstMappingTest("testRequetMapping")
    public String testRequestMapping(){
        return "TestSuccess";
    }



}
