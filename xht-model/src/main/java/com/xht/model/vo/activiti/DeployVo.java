package com.xht.model.vo.activiti;

import lombok.Data;

import java.util.Date;

/**
 * @author : YIYUANYUAN
 * @date: 2024/4/23  23:08
 */

@Data
public class DeployVo {
    private String procDefId;
    private String deployId;
    private String procDefKey;
    private String procDefName;
    private String resourceName;
    private Date deployTime;
    private Integer version;
    private String description;
}
