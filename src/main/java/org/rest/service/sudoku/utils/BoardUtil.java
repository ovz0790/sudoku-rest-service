package org.rest.service.sudoku.utils;

import org.rest.service.sudoku.decision.Sudoku;
import org.rest.service.sudoku.decision.SudokuImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Created by zlobina on 09.06.16.
 */
public class BoardUtil {
    private static final Logger logger = LoggerFactory.getLogger(BoardUtil.class);

    public int[][] getRandomBord(){
        int[][] brd = new int[9][9];
        final Random randomGenerator = new Random();
        Sudoku s = new SudokuImpl();
        s.solve(brd);

        for (int i=0; i<9; i++){
            for (int j=0;j<9;j++){
                if (randomGenerator.nextBoolean() || randomGenerator.nextBoolean()){
                    brd[i][j] = 0;
                }
            }
        }
        print(0,3,brd);
        return brd;
    }

    public void print(int  mBoardSize, int mBoxSize, int[][]  mBoard){
        StringBuilder sb = new StringBuilder("\n");
        for(int i = 0; i < mBoardSize; i++) {
            if(i % mBoxSize == 0) {
                //System.out.println(" -----------------------");
                sb.append(" -----------------------\n");
            }
            for(int j = 0; j < mBoardSize; j++) {
                if(j % mBoxSize == 0) {
                    //System.out.print("| ");
                    sb.append("| ");
                }
                //System.out.print(mBoard[i][j] != 0 ? ((Object) (Integer.valueOf(mBoard[i][j]))) : " ");
                sb.append(mBoard[i][j] != 0 ? ((Object) (Integer.valueOf(mBoard[i][j]))) : " ");

                //System.out.print(' ');
                sb.append(' ');
            }

            //System.out.println("|");
            sb.append("|\n");
        }

        //System.out.println(" -----------------------");
        sb.append(" -----------------------\n");
        logger.info(sb.toString());
    }
}
