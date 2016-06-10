package tests.org.rest.service.sudoku.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

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
import org.springframework.context.annotation.*;
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
 * Created by zlobina on 16.03.16.
 */
@ContextConfiguration
@EnableWebMvc
@RunWith(SpringJUnit4ClassRunner.class)
public class ErrorHandlerTest {
    private static final Logger logger = LoggerFactory.getLogger(ErrorHandlerTest.class);
    private MockMvc mockMvc;

    @Autowired
    SudokuController sudokuController;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void init() throws ValidationException, IOException {
        mockMvc = MockMvcBuilders
                .standaloneSetup(sudokuController)
                .build();
    }
    @Test
    public void sendValidateErrorTest() throws Exception {
        thrown.expect(NestedServletException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/demo/sudoku/change_element"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
     }

    @Test(expected = IOException.class)
    public void sendIOError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/demo/sudoku/init_board"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());

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
            ValidateUtil vu = Mockito.mock(ValidateUtil.class);
            doThrow(new ValidationException("Invalid data"))
                    .when(vu)
                    .validateBoardElement(Mockito.anyList());
            return vu;
        }

        @Bean
        public ObjectMapper mapper() {
            return new ObjectMapper();
        }

        @Bean
        public CommonUtils commonUtils() throws IOException {
            CommonUtils cu = Mockito.mock(CommonUtils.class);
            doThrow(new IOException("CannotCopy"))
                    .when(cu)
                    .getArrayCopy(any(int[][].class));
            return cu;

        }
    }

}
