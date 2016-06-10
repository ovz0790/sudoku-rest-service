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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * Created by zlobina on 10.06.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CommonUtilsTest {
    @Autowired
    CommonUtils commonUtils;

    @Autowired
    BoardUtil boardUtil;

    @Test
    public  void getArrayCopy() throws IOException {
        int[][] array = boardUtil.getRandomBord();
        int[][] copy = commonUtils.getArrayCopy(array);

        Assert.assertTrue(boardUtil.isSolved(array, copy));

        copy[1][1] = copy[1][1] - 1;

        Assert.assertFalse(boardUtil.isSolved(array, copy));

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
