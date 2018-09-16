package com.qpf.springbootquick.listeners;

import org.springframework.boot.CommandLineRunner;

import java.util.Arrays;

public class HelloCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("CommandLineRunner----run: " + Arrays.asList(args));
    }
}
