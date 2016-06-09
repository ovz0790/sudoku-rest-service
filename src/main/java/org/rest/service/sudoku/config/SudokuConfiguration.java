package org.rest.service.sudoku.config;

import org.rest.service.sudoku.decision.Sudoku;
import org.rest.service.sudoku.decision.SudokuImpl;
import org.rest.service.sudoku.utils.BoardUtil;
import org.rest.service.sudoku.utils.ValidateUtil;
import org.springframework.context.annotation.*;

/**
 * Created by zlobina on 09.06.16.
 */
@Configuration
@PropertySource({
        "${config.path:classpath:config/base.properties}",
})
@ComponentScan(basePackages={"org.rest.service.sudoku.session"})
/*@Import({
        ContextConfiguration.class,
})*/
public class SudokuConfiguration {
    @Bean
    public Sudoku sudoku(){
        return new SudokuImpl();
    }

    @Bean
    public BoardUtil boardUtil(){
        return new BoardUtil();
    }

    @Bean
    ValidateUtil validateUtil(){
        return new ValidateUtil();
    }

}
