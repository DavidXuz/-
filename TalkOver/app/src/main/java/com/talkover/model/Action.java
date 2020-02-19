package com.talkover.model;

/**
 * Created by heleninsa on 2019-07-14.
 *
 * @author heleninsa
 */
public class Action {

    public static final int FIND_BY_ID = 0;
    public static final int FIND_BY_TEXT = 1;
    public static final int FIND_BY_ID_AND_CLICK = 2;
    public static final int FIND_BY_TEXT_AND_CLICK = 3;
    public static final int FIND_BY_ID_SET_TEXT = 4;

    private int action;
    private String keyword;

    public Action(int action, String keyword) {
        this.action = action;
        this.keyword = keyword;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
