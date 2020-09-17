package com.alliconsulting.server.LoadTestServer.api;

import com.alliconsulting.server.LoadTestServer.service.PingServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class PingServerController {

    private final PingServerService pingServerService;

    @Autowired
    public PingServerController(PingServerService pingServerService){
        this.pingServerService=pingServerService;
    }

    @GetMapping(path="/getserverip")
    public String getServerIP(){
        InetAddress ip;
        String server = "";
        try {
            ip = InetAddress.getLocalHost();
            server="Server IP address : " + ip.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return server;
    }

    @GetMapping(path="/getip")
    public String getIP(){
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest();
        String ip = Optional.ofNullable(request.getHeader("X-FORWARDED-FOR")).orElse(request.getRemoteAddr());
        if (ip.equals("0:0:0:0:0:0:0:1")) ip = "127.0.0.1";
        Assert.isTrue(ip.chars().filter($ -> $ == '.').count() == 3, "Illegal IP: " + ip);
        return "Caller IP Address: " + ip;
    }

    @GetMapping(path="/ping")
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
