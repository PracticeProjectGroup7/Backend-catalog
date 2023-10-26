package org.teamseven.hms.backend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "sec-env-conf", ignoreUnknownFields = false)
public class SecurityEnvConfig {
    private boolean enableLoginGuard = false;
}
