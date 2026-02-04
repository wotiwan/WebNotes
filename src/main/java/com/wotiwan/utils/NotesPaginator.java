package com.wotiwan.utils;

public class NotesPaginator {

    private final static int NOTES_PER_PAGE = 5;

    public static int getLimit() {
        return NOTES_PER_PAGE;
    }

    public static int getOffset(int currentPage) {
        return NOTES_PER_PAGE * currentPage - NOTES_PER_PAGE;
    }

}
