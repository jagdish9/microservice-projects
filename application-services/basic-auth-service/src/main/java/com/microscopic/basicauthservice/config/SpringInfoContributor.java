package com.microscopic.basicauthservice.config;

import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component //Not using this class, we have used controller instead of this
public class SpringInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("spring-version", SpringBootVersion.getVersion());
    }
}
