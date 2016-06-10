package tests.org.rest.service.sudoku.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.service.sudoku.controller.SudokuController;
import org.rest.service.sudoku.decision.Sudoku;
import org.rest.service.sudoku.decision.SudokuImpl;
import org.rest.service.sudoku.session.UserSession;
import org.rest.service.sudoku.utils.BoardUtil;
import org.rest.service.sudoku.utils.CommonUtils;
import org.rest.service.sudoku.utils.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.util.NestedServletException;
import javax.xml.bind.ValidationException;
import java.io.IOException;

/**
 * Created by zlobina on 09.06.16.
 */
@ContextConfiguration
@EnableWebMvc
@RunWith(SpringJUnit4ClassRunner.class)
public class SudokuControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(SudokuControllerTest.class);

    @Autowired
    SudokuController sudokuController;

    @Before
    public void init() throws ValidationException, IOException {
        mockMvc = MockMvcBuilders
                .standaloneSetup(sudokuController)
                .build();
    }
    private MockMvc mockMvc;

    @Test
    public void getBoardTest() throws Exception {
        sudokuController.initBoard();
        mockMvc.perform(MockMvcRequestBuilders.get("/demo/sudoku/show_task_board"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test(expected = NestedServletException.class)
    public void getNonInitBoardTest() throws Exception {
        try {
            sudokuController.submit(true);
        }catch (Exception e){}

        mockMvc.perform(MockMvcRequestBuilders.get("/demo/sudoku/show_task_board"));

    }

    @Test
    public void getUserBoardTest() throws Exception {
        sudokuController.initBoard();
        mockMvc.perform(MockMvcRequestBuilders.get("/demo/sudoku/show_user_board"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test(expected = NestedServletException.class)
    public void getNonInitUserBoardTest() throws Exception {
        try {
            sudokuController.submit(true);
        }catch (Exception e){}

        mockMvc.perform(MockMvcRequestBuilders.get("/demo/sudoku/show_user_board"));
    }


    @Test
    public void initBoardTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/demo/sudoku/init_board"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void changeElementTest() throws Exception {
        sudokuController.initBoard();
        mockMvc.perform(MockMvcRequestBuilders.post("/demo/sudoku/change_element?xCoord=1&yCoord=2&val=3"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void boardNotInitChangeElementTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/demo/sudoku/change_element?xCoord=1&yCoord=2&val=3"));
    }

    @Test(expected = NestedServletException.class)
    public void invalidParametersChangeElementTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/demo/sudoku/change_element"));
    }



    @Test
    public void submitWithoutCleanTest()
            throws Exception {
        sudokuController.initBoard();
        mockMvc.perform(MockMvcRequestBuilders.post("/demo/sudoku/submit"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Assert.assertNotNull(sudokuController.getBoard());
    }


    @Test(expected = ValidationException.class)
    public void submitWithCleanTest() throws Exception {
        sudokuController.initBoard();
        mockMvc.perform(MockMvcRequestBuilders.post("/demo/sudoku/submit?clear=true"));
        Assert.assertNull(sudokuController.getBoard());
    }

    @Test(expected = NestedServletException.class)
    public void boardNotInitSubmitTest() throws Exception {
        try {
            sudokuController.submit(true);
        }catch (Exception e){}
        mockMvc.perform(MockMvcRequestBuilders.post("/demo/sudoku/submit"));
    }


    @Configuration
    @PropertySource({
            "classpath:config/base.properties",
    })
    @ComponentScan(basePackages={"org.rest.service.sudoku.controller"})
    public static class Config {
        @Bean
        public UserSession userSession() {
            return new UserSession();
        }

        @Bean
        public Sudoku sudoku() {
            return new SudokuImpl();
        }

        @Bean
        public BoardUtil boardUtil() {
            return new BoardUtil();
        }

        @Bean
        public ValidateUtil validateUtil() throws ValidationException {
            return new ValidateUtil();
        }

        @Bean
        public ObjectMapper mapper() {
            return new ObjectMapper();
        }

        @Bean
        public CommonUtils commonUtils() throws IOException {
            return new CommonUtils();

        }
    }

}
