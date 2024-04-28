package com.xht.model.constant;

public enum ActTaskStatusEnum {

    APPLY( "申请提出") ,
    TRANS( "任务转交") ,
    UNDO( "未处理") ,
    DID( "已处理") ;

    private ActTaskStatusEnum(String status) {
    }
}
