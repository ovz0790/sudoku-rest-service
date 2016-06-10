package tests.org.rest.service.sudoku.utils;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.service.sudoku.utils.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zlobina on 09.06.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ValidateUtilTest {

    @Autowired
    ValidateUtil validateUtil;

    @Test
    public void validateBoardElement() throws ValidationException {
        List<Integer> params = new ArrayList<>(Arrays.asList(1,2,3));

        validateUtil.validateBoardElement(params);
    }

    @Test(expected = ValidationException.class)
    public void validateIncorrectBoardElement() throws ValidationException {
        List<Integer> params = new ArrayList<>(Arrays.asList(1,2,13));

        validateUtil.validateBoardElement(params);
    }

    @Configuration
    @PropertySource({
            "classpath:config/base.properties",
    })
    public static class Config {

        @Bean
        public ValidateUtil validateUtil() {
            return new ValidateUtil();
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
