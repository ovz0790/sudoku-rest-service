package org.rest.service.sudoku.controller;

import org.rest.service.sudoku.config.SudokuConfiguration;
import org.rest.service.sudoku.decision.Sudoku;
import org.rest.service.sudoku.session.UserSession;
import org.rest.service.sudoku.utils.BoardUtil;
import org.rest.service.sudoku.utils.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.QueryParam;
import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by zlobina on 09.06.16.
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@Configuration
@RequestMapping(SudokuController.ROOT_URL)
@Import({
        SudokuConfiguration.class,
})
public class SudokuController {
    private static final Logger logger = LoggerFactory.getLogger(SudokuController.class);
    public final static String ROOT_URL = "demo/sudoku";
    @Autowired
    BoardUtil boardUtil;

    @Autowired
    Sudoku sudoku;

    @Autowired
    UserSession userSession;

    @Autowired
    ValidateUtil validateUtil;

    @RequestMapping(value = "/show_board", method = RequestMethod.GET)
    @ResponseBody
    public int[][] getBoard(){
        return boardUtil.getRandomBord();
    }

    @RequestMapping(value = "/init_board", method = RequestMethod.GET)
    @ResponseBody
    public int[][] initBoard(){
        int[][] board = boardUtil.getRandomBord();
        userSession.board = board;
        userSession.userBoard = Arrays.copyOf(board, board.length);
        return board;
    }

    @RequestMapping(value = "/change_element", method = RequestMethod.POST)
    @ResponseBody
    public void changeElement(@QueryParam("xCoord") Integer xCoord,
                                 @QueryParam("yCoord") Integer yCoord,
                                 @QueryParam("val") Integer val) throws ValidationException {

        validateUtil.validateBoardElement(new ArrayList<>(Arrays.asList(xCoord,
                yCoord,
                val)));

        validateBoard();

        userSession.userBoard[xCoord][yCoord] = val;
    }

    @RequestMapping(value = "/submit", method = RequestMethod.GET)
    @ResponseBody
    public boolean changeElement(@QueryParam("clear") Boolean clear) throws ValidationException {
        validateBoard();

        boolean _clear = clear != null && clear;

        int[][] userBoard =  userSession.userBoard;
        int[][] solvedBoard =  Arrays.copyOf(userBoard, userBoard.length);
        sudoku.solve(solvedBoard);

        logger.info("User board _________________________________________________________{}", userBoard);
        boardUtil.print(9, 3, userBoard);

        logger.info("Solved board _________________________________________________________{}", solvedBoard);
        boardUtil.print(9, 3, solvedBoard);

        boolean isSolved = boardUtil.isSolved(userBoard, solvedBoard);

        if (_clear){
            userSession.userBoard = null;
            userSession.board = null;
        }

        return isSolved;

    }

    public final static class ServiceConfiguration {
        @Bean
        public EmbeddedServletContainerFactory servletContainer() {

            JettyEmbeddedServletContainerFactory factory = new JettyEmbeddedServletContainerFactory(9001);
            return factory;

        }
    }

    private void validateBoard() throws ValidationException {
        if (userSession.userBoard == null)
            throw  new ValidationException("Board is not initialized!");
    }
}
