package kr.co._29cm.homework.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentHelper {
    @Autowired
    private Environment environment;

    public Boolean isTestProfiles() {
        return "test".equals(environment.getActiveProfiles()[0]);
    }
}
