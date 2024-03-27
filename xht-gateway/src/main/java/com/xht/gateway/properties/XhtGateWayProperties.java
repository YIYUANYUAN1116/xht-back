package com.xht.gateway.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author : YIYUANYUAN
 * @date: 2024/3/26  21:27
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
@ConfigurationProperties(prefix = "xht")
public class XhtGateWayProperties {
    private List<String> ignoreUrl;
}
