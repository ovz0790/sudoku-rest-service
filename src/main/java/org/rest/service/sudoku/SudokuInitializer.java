package org.rest.service.sudoku;

import org.rest.service.sudoku.controller.SudokuController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

/**
 * Created by zlobina on 09.06.16.
 */
public class SudokuInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(SudokuInitializer.class);
    public static void main(String[] args) throws Throwable {
        LOG.info("Starting Calculator");
        SpringApplication.run(SudokuController.class);
    }
}
