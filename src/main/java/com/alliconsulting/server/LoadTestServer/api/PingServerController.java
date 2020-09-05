package com.alliconsulting.server.LoadTestServer.api;

import com.alliconsulting.server.LoadTestServer.service.PingServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingServerController {

    private final PingServerService pingServerService;

    @Autowired
    public PingServerController(PingServerService pingServerService){
        this.pingServerService=pingServerService;
    }

    @PostMapping
    public String pingServer(){
        return pingServerService.ping();
    }

}
