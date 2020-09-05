package com.alliconsulting.server.LoadTestServer.service;

import org.springframework.stereotype.Service;

@Service
public class PingServerService {

    public String ping(){
        return this.toString();
    }

}
