package kr.co._29cm.homework;

import kr.co._29cm.homework.helper.EnvironmentHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@RequiredArgsConstructor
public class HomeworkApplication implements CommandLineRunner {

    private final ApplicationContext applicationContext;
    private final EnvironmentHelper environmentHelper;

    public static void main(String[] args) {
        SpringApplication.run(HomeworkApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if (!environmentHelper.isTestProfiles()) {
            FrontEnd frontEnd = new FrontEnd();
            frontEnd.start();
            SpringApplication.exit(applicationContext);
        }
    }
}
