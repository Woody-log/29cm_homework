package kr.co._29cm.homework.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnvironmentHelper {

    private final Environment environment;

    public Boolean isTestProfiles() {
        return "test".equals(environment.getActiveProfiles()[0]);
    }
}
