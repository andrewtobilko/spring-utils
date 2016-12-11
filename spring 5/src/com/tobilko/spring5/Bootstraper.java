package com.tobilko.spring5;

public class Bootstraper {

    public static void main(String[] args) {

        RouterFunction<String> route =
                route(GET("/test"),
                        request -> Response.ok().body(fromObject("Test")));

    }

}
