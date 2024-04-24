package com.xht.activiti.controller;

import com.xht.activiti.service.ActivitiService;
import com.xht.model.vo.activiti.DeployVo;
import com.xht.model.vo.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.zip.ZipInputStream;

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
}
