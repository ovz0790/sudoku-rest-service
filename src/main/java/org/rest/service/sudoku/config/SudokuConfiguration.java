package org.rest.service.sudoku.config;

import org.codehaus.jackson.map.ObjectMapper;
import org.rest.service.sudoku.decision.Sudoku;
import org.rest.service.sudoku.decision.SudokuImpl;
import org.rest.service.sudoku.utils.BoardUtil;
import org.rest.service.sudoku.utils.CommonUtils;
import org.rest.service.sudoku.utils.ValidateUtil;
import org.springframework.context.annotation.*;

/**
 * Created by zlobina on 09.06.16.
 */
@Configuration
@PropertySource({
        "${config.path:classpath:config/base.properties}",
})
@ComponentScan(basePackages={"org.rest.service.sudoku.session",
                             "org.rest.service.sudoku.controller"})
public class SudokuConfiguration {
    @Bean
    public Sudoku sudoku(){
        return new SudokuImpl();
    }

    @Bean
    @Scope(value = "globalSession", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public BoardUtil boardUtil(){
        return new BoardUtil();
    }

    @Bean
    @Scope(value = "globalSession", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ValidateUtil validateUtil(){
        return new ValidateUtil();
    }

    @Bean
    @Scope(value = "globalSession", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }

    @Bean
    @Scope(value = "globalSession", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public CommonUtils commonUtils(){
        return new CommonUtils();
    }
}
