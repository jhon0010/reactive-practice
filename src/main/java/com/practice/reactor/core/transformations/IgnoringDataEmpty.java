package com.practice.reactor.core.transformations;

import org.slf4j.Logger;

import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class IgnoringDataEmpty {

    private static IgnoringDataEmpty instance;

    private IgnoringDataEmpty() {
    }

    private static final Logger LOGGER = getLogger(IgnoringDataEmpty.class);


    public static IgnoringDataEmpty getInstance() {

        instance = instance == null ? new IgnoringDataEmpty() : instance;
        return instance;
    }

    public static void main(String[] args) {

        LOGGER.warn("" + LocalDateTime.now().equals(LocalDateTime.now()));

//        IgnoringDataEmpty instance = IgnoringDataEmpty.getInstance();
//        LOGGER.info("Instance of " + instance);
//
//        IgnoringDataEmpty instance2 = IgnoringDataEmpty.getInstance();
//        LOGGER.info("Instance of 2 " + instance2);
//
//        IgnoringDataEmpty instance3 = IgnoringDataEmpty.getInstance();
//        LOGGER.info("Instance of 3 " + instance3);
    }

}
