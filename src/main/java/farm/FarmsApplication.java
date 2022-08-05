package farm;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"farm", "application"})
@AllArgsConstructor
public class FarmsApplication implements CommandLineRunner {

    private final Starter starter;

    public static void main(String[] args) {
        SpringApplication.run(FarmsApplication.class, args);
    }

    @Override
    public void run(String... args) {
        starter.run(args);
    }
}
