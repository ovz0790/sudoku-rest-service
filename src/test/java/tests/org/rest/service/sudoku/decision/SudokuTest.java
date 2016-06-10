package tests.org.rest.service.sudoku.decision;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.service.sudoku.config.SudokuConfiguration;
import org.rest.service.sudoku.decision.Sudoku;
import org.rest.service.sudoku.decision.SudokuImpl;
import org.rest.service.sudoku.utils.BoardUtil;
import org.rest.service.sudoku.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by zlobina on 09.06.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SudokuTest {

    @Autowired
    Sudoku sudoku;

    @Autowired
    BoardUtil boardUtil;

    @Test
    public void solveTest() {
        int[][] brd = boardUtil.getRandomBord();

        boardUtil.print(9, 3, brd);
        Assert.assertFalse(checkAllNotNull(brd));

        sudoku.solve(brd);
        boardUtil.print(9, 3, brd);
        Assert.assertTrue(checkAllNotNull(brd));

    }

    private boolean checkAllNotNull(int[][] board){
        final boolean[] result = new boolean[]{true};
        Arrays.stream(board).forEach((i) -> {
            Arrays.stream(i).forEach((j) ->
            {
                if (j == 0) {
                    result[0] = false;
                    return;
                }
            });
        });
        return result[0];
    }


    @Configuration
    @PropertySource({
            "classpath:config/base.properties",
    })
    public static class Config {

        @Bean
        public BoardUtil boardUtil() {
            return new BoardUtil();
        }

        @Bean
        public CommonUtils commonUtils() throws IOException {
            return new CommonUtils();

        }

        @Bean
        public ObjectMapper mapper() {
            return new ObjectMapper();
        }

        @Bean
        public Sudoku sudoku(){
            return new SudokuImpl();
        }
    }
    static
    {
        Logger rootLogger = Logger.getRootLogger();
        rootLogger.setLevel(Level.INFO);
        rootLogger.addAppender(new ConsoleAppender(
                new PatternLayout("%-6r [%p] %c - %m%n")));
    }
    static
    {
        Logger rootLogger = Logger.getRootLogger();
        rootLogger.setLevel(Level.INFO);
        rootLogger.addAppender(new ConsoleAppender(
                new PatternLayout("%-6r [%p] %c - %m%n")));
    }

}
