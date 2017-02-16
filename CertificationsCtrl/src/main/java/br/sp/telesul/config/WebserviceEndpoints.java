/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sp.telesul.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 *
 * @author lmohan
 */
@Configuration
@ImportResource({"classpath:META-INF/cxf/cxf.xml"})
public class WebserviceEndpoints {

//    @Autowired
//    Bus cxfBus;
//
//    @Bean
//    public Endpoint weather() {
//        EndpointImpl endpoint = new EndpointImpl(cxfBus, new WeatherServiceImpl());
//        endpoint.setAddress("/weather");
//        endpoint.publish();
//        return endpoint;
//    }
//
//    @Bean
//    public Endpoint fireevent() {
//        EndpointImpl endpoint = new EndpointImpl(cxfBus, new ReportIvrEventServiceImpl());
//        endpoint.setAddress("/fireevent");
//        endpoint.publish();
//        return endpoint;
//    }
}
