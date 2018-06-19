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
    public static void main(String... args) {
//        System.getProperties().put( "server.port", 1234 );
        System.setProperty("blockchain.node.id", "your address generated from the wallet");
        if (System.getProperty("blockchain.node.id") == null) {
            System.setProperty("blockchain.node.id", UUID.randomUUID().toString().replace("-", ""));
        }
        SpringApplication.run(BlockchainEduApplication.class, args);
    }
}
