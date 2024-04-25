package com.xht.model.vo.activiti;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskVo {
    private String taskId;
    private String taskName;
    private String description;
    private String procInsId;
    private Date creatTime;
    private Date endTime;

}
