package com.daishuai.webflux.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Daishuai
 * @version 1.0.0
 * @ClassName HelloController.java
 * @Description HelloWorld
 * @createTime 2022年06月02日 10:24:00
 */
@Slf4j
@RestController
public class HelloController {

    @GetMapping(value = "/hello")
    public Mono<Map<String, Object>> hello() {
        long start = System.currentTimeMillis();
        Mono<Map<String, Object>> mono = Mono.fromSupplier(this::getHelloWorld);
        long end = System.currentTimeMillis();
        log.info("耗时: {}", end - start);
        return mono;
    }

    @GetMapping(value = "/flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Object> flux() {
        String[] names = {"javaboy", "itboyhub", "www.javaboy.org", "www.itboyhub.com"};
        Object[] objects = {"1244", 12, new HashMap<>()};
        Flux<Object> flux = Flux.fromArray(objects).publishOn(Schedulers.boundedElastic()).map(name -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                log.error("Thread Sleep Was Interrupted");
            }
            return name;
        });
        return flux;
    }

    private Map<String, Object> getHelloWorld() {
        Map<String, Object> map = new HashMap<>();
        map.put("username", "zhangsan");
        map.put("password", "helloworld");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            log.error("Thread Sleep Was Interrupted");
        }
        return map;
    }
}
