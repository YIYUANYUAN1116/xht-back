package com.xht.activiti.service.impl;

import com.xht.activiti.service.ActivitiService;
import com.xht.common.service.exception.XhtException;
import com.xht.model.vo.activiti.DeployVo;
import com.xht.model.vo.common.Result;
import com.xht.model.vo.common.ResultCodeEnum;
import jakarta.persistence.Access;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        return null;
    }
}
