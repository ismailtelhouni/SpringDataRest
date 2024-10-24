package org.spring.data.rest.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.datasource")
@Data
public class DatasourceConfig {
    private String password;
}
