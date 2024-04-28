package com.xht.activiti.controller;

import com.xht.activiti.service.ActivitiService;
import com.xht.model.dto.activiti.ApplyDto;
import com.xht.model.dto.activiti.CompleteTaskDto;
import com.xht.model.vo.activiti.DeployVo;
import com.xht.model.vo.activiti.TaskVo;
import com.xht.model.vo.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * @author : YIYUANYUAN
 * @date: 2024/4/23  22:54
 */
@RestController
@Tag(name = "工作流接口")
@RequestMapping("/activiti")
public class ActivitiController {

    @Autowired
    ActivitiService activitiService;

    @PostMapping("/deploy")
    @Operation(summary = "部署")
    public Result deploy(@RequestPart("file") MultipartFile file){
        activitiService.deploy(file);
        return Result.buildSuccess();
    }

    @GetMapping("/deployList")
    @Operation(summary = "流程定义信息")
    public Result deployList(){
        List<DeployVo> deployVoList = activitiService.deployList();
        return Result.buildSuccess(deployVoList);
    }

    @PostMapping("/apply")
    @Operation(summary = "开启一个流程")
    public Result apply(@RequestBody ApplyDto applyDto){
        activitiService.apply(applyDto);
        return Result.buildSuccess();
    }

    @GetMapping("/getCurTaskById")
    @Operation(summary = "获取用户待处理task")
    public Result getCurTaskById(@RequestParam Integer  userId){
        List<TaskVo> list = activitiService.getCurTaskById(userId);
        return Result.buildSuccess(list);
    }

    @GetMapping("/getHiTaskById")
    @Operation(summary = "获取用户历史task")
    public Result getHiTaskById(@RequestParam Integer  userId){
        List<TaskVo> list = activitiService.getHiTaskById(userId);
        return Result.buildSuccess(list);
    }


    @GetMapping("/completeTask")
    @Operation(summary = "完成任务")
    public Result completeTask(@RequestBody CompleteTaskDto completeTaskDto){
        activitiService.completeTask(completeTaskDto);
        return Result.buildSuccess();
    }

}
