package com.xht.activiti.service;

import com.xht.model.vo.activiti.DeployVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author : YIYUANYUAN
 * @date: 2024/4/23  22:54
 */
public interface ActivitiService {
    void deploy(MultipartFile file);

    List<DeployVo> deployList();
}
