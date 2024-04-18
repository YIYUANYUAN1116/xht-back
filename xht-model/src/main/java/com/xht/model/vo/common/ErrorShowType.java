package com.xht.model.vo.common;

/**
 * @author : YIYUANYUAN
 * @date: 2024/3/28  21:11
 */
public enum ErrorShowType {
    SILENT (0),
    WARN_MESSAGE (1),
    ERROR_MESSAGE (2),
    NOTIFICATION(3),
    REDIRECT (9);

    private Integer showType;


    private ErrorShowType(Integer showType){
        this.showType = showType;
    }
}
