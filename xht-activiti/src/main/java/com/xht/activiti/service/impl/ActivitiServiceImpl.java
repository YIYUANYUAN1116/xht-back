package com.xht.activiti.service.impl;

import com.xht.activiti.service.ActivitiService;

import com.xht.model.dto.activiti.ApplyDto;
import com.xht.model.vo.activiti.DeployVo;
import com.xht.model.vo.activiti.TaskVo;
import com.xht.model.vo.common.Result;
import com.xht.model.vo.common.ResultCodeEnum;
import com.xht.security.common.service.exception.XhtException;
import jakarta.persistence.Access;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * @author : YIYUANYUAN
 * @date: 2024/4/23  22:55
 */
@Service
public class ActivitiServiceImpl implements ActivitiService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;



    @Override
    public void deploy(MultipartFile file) {
        try {
            DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
            ZipInputStream zipInputStream =  new ZipInputStream(file.getInputStream());
            deploymentBuilder.addZipInputStream(zipInputStream);
            deploymentBuilder.name("请假审批");
            deploymentBuilder.deploy();
        } catch (IOException e) {
            throw new XhtException(e.getMessage(),ResultCodeEnum.SYSTEM_ERROR2.getCode());
        }
    }

    @Override
    public List<DeployVo> deployList() {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();
        return list.stream().map(processDefinition -> {
            DeployVo deployVo = new DeployVo();
            deployVo.setDeployId(processDefinition.getDeploymentId());
            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(processDefinition.getDeploymentId()).singleResult();
            if (deployment != null) {
                deployVo.setDeployTime(deployment.getDeploymentTime());
                deployVo.setVersion(deployment.getVersion());
            }
            deployVo.setProcDefId(processDefinition.getId());
            deployVo.setProcDefName(processDefinition.getName());
            deployVo.setResourceName(processDefinition.getResourceName());
            deployVo.setDescription(processDefinition.getDescription());
            return deployVo;
        }).toList();
    }


    @Override
    public void apply(ApplyDto applyDto) {
        Assert.notNull(applyDto,"applyDto is null");
        Assert.notNull(applyDto.getProcDefId(),"applyDto.getProcDefId() is null");
        runtimeService.startProcessInstanceById(applyDto.getProcDefId(),applyDto.getParamMap());
    }

    @Override
    public List<TaskVo> getCurTaskById(Integer userId) {
        List<Task> list = taskService.createTaskQuery().taskCandidateOrAssigned(String.valueOf(userId)).list();
        return list.stream().map(task -> {
            TaskVo taskVo = new TaskVo();
            taskVo.setTaskId(task.getId());
            taskVo.setTaskName(task.getName());
            taskVo.setDescription(task.getDescription());
            taskVo.setProcInsId(task.getProcessInstanceId());
            taskVo.setCreatTime(task.getCreateTime());
            return taskVo;
        }).toList();

    }

    @Override
    public List<TaskVo> getHiTaskById(Integer userId) {
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().taskAssignee(String.valueOf(userId)).finished().list();
        List<TaskVo> taskVos = new ArrayList<>();

//        list.stream().map(historyService->{
//            historyService.get
//        })

        return taskVos;
    }


}
