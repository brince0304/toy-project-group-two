package com.toyboardproject.utils;

import com.toyboardproject.domain.BoardType;

public class BoardUtil {
    public static String getBoardTypeName(BoardType boardType) {
        return switch (boardType) {
            case NOTICE -> BoardType.NOTICE.getValue();
            case FREE -> BoardType.FREE.getValue();
            case FAQ_USE -> BoardType.FAQ_USE.getValue();
            case FAQ_PAYMENT -> BoardType.FAQ_PAYMENT.getValue();
            case FAQ_OTHER -> BoardType.FAQ_OTHER.getValue();
            default -> "게시판";
        };
    }
}
