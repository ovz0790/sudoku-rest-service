package org.rest.service.sudoku.utils;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by zlobina on 10.06.16.
 */
public class CommonUtils {
    @Autowired
    ObjectMapper mapper;

    public int[][] getArrayCopy(int[][] array) throws IOException {
        String ubJson = mapper.writeValueAsString(array);
        return mapper.readValue(ubJson, int[][].class);
    }
}
