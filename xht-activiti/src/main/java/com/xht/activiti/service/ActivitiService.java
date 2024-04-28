package com.xht.activiti.service;

import com.xht.model.dto.activiti.ApplyDto;
import com.xht.model.dto.activiti.CompleteTaskDto;
import com.xht.model.vo.activiti.DeployVo;
import com.xht.model.vo.activiti.TaskVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author : YIYUANYUAN
 * @date: 2024/4/23  22:54
 */
public interface ActivitiService {
    void deploy(MultipartFile file);

    List<DeployVo> deployList();

    void apply(ApplyDto applyDto);



    List<TaskVo> getCurTaskById(Integer userId);

    List<TaskVo> getHiTaskById(Integer userId);

    void completeTask(CompleteTaskDto completeTaskDto);
}
