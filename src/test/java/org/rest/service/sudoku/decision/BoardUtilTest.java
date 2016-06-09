package org.rest.service.sudoku.decision;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.service.sudoku.config.SudokuConfiguration;
import org.rest.service.sudoku.utils.BoardUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Arrays;


/**
 * Created by zlobina on 09.06.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= SudokuConfiguration.class, loader=AnnotationConfigContextLoader.class)
public class BoardUtilTest {
    @Autowired
    BoardUtil boardUtil;

    @Test
    public void getRandomBord(){
        int[][] board = boardUtil.getRandomBord();

        boardUtil.print(9, 3, board);
        Assert.assertNotNull(board);

        Assert.assertTrue(checkHasNotNulls(board));
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

    static
    {
        Logger rootLogger = Logger.getRootLogger();
        rootLogger.setLevel(Level.INFO);
        rootLogger.addAppender(new ConsoleAppender(
                new PatternLayout("%-6r [%p] %c - %m%n")));
    }

}
