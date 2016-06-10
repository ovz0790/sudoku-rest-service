package tests.org.rest.service.sudoku.utils;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.service.sudoku.utils.BoardUtil;
import org.rest.service.sudoku.utils.CommonUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.io.IOException;
import java.util.Arrays;


/**
 * Created by zlobina on 09.06.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class BoardUtilTest {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(BoardUtilTest.class);
    @Autowired
    BoardUtil boardUtil;

    @Autowired
    CommonUtils commonUtils;


    @Test
    public void getRandomBord(){
        int[][] board = boardUtil.getRandomBord();
        logger.info("_____________________RANDOM BOARD___________________________");
        boardUtil.print(9, 3, board);
        Assert.assertNotNull(board);

        Assert.assertTrue(checkHasNotNulls(board));
    }

    @Test
    public void isSolved() throws IOException {
        int[][] board = boardUtil.getRandomBord();
        int[][] solvedBoard = commonUtils.getArrayCopy(board);

        Assert.assertTrue(boardUtil.isSolved(board, solvedBoard));
        solvedBoard[2][2] = solvedBoard[2][2] > 0 ? solvedBoard[2][2] - 1 : solvedBoard[2][2] +1;

        Assert.assertFalse(boardUtil.isSolved(board, solvedBoard));
    }

    private boolean checkHasNotNulls(int[][] board){
        final boolean[] result = new boolean[]{false};
        Arrays.stream(board).forEach((i) -> {
            Arrays.stream(i).forEach((j) ->
            {
                if (j > 0) {
                    result[0] = true;
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
    }
    static
    {
        Logger rootLogger = Logger.getRootLogger();
        rootLogger.setLevel(Level.INFO);
        rootLogger.addAppender(new ConsoleAppender(
                new PatternLayout("%-6r [%p] %c - %m%n")));
    }

}