package org.rest.service.sudoku.session;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * Created by zlobina on 09.06.16.
 */
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class UserSession {

    public int[][] board;

    public int[][] userBoard;

}
