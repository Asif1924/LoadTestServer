package com.alliconsulting.server.LoadTestServer.api;

import com.alliconsulting.server.LoadTestServer.service.PingServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PingServerController {

    private final PingServerService pingServerService;

    @Autowired
    public PingServerController(PingServerService pingServerService){
        this.pingServerService=pingServerService;
    }

    @PostMapping(path="/ping")
    public String pingServer(){
        return "ping";
    }

    @GetMapping(path = "/myrequestheaders", produces = {MediaType.APPLICATION_JSON_VALUE} )
    public ResponseEntity<Map<String, Object>> getMyRequestHeaders(HttpServletRequest request)
    {
        Map<String, Object> returnValue = new HashMap<>();

        Enumeration<String> hearderNames = request.getHeaderNames();
        while(hearderNames.hasMoreElements())
        {
            String headerName = hearderNames.nextElement();
            returnValue.put(headerName, request.getHeader(headerName));
        }

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

}
