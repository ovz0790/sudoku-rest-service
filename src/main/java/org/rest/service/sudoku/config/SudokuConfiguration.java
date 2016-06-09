package org.rest.service.sudoku.config;

import org.rest.service.sudoku.decision.Sudoku;
import org.rest.service.sudoku.decision.SudokuImpl;
import org.rest.service.sudoku.utils.BoardUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by zlobina on 09.06.16.
 */
@Configuration
@PropertySource({
        "${config.path:classpath:config/base.properties}",
})
public class SudokuConfiguration {
    @Bean
    public Sudoku sudoku(){
        return new SudokuImpl();
    }

    @Bean
    public BoardUtil boardUtil(){
        return new BoardUtil();
    }

}
