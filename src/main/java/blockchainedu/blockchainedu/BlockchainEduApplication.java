package blockchainedu.blockchainedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@SpringBootApplication
public class BlockchainEduApplication {

    //https://hackernoon.com/learn-blockchains-by-building-one-117428612f46

    public static void main(String... args) {
        if (System.getProperty("blockchain.node.id") == null) {
            System.setProperty("blockchain.node.id", UUID.randomUUID().toString().replace("-", ""));
        }
        SpringApplication.run(BlockchainEduApplication.class, args);
    }
}
