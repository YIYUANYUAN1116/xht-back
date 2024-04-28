package com.xht.model.dto.activiti;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompleteTaskDto {
    private String taskId;
    private String operation;
    private Map<String,Object> paramMap = new HashMap<>();}
