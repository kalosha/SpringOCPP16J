package ge.devel.ocpp;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SpringOcpp16JApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringOcpp16JApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("############################## ---> OCPP 1.6 JSON <--- ##############################");
    }
}
