package blockchainedu.blockchainedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
@SpringBootApplication
public class BlockchainEduApplication {
    public static void main(String... args) {
//        System.getProperties().put( "server.port", 1234 );
        System.setProperty("blockchain.node.id", "node at 1234");
        if (System.getProperty("blockchain.node.id") == null) {
            System.setProperty("blockchain.node.id", UUID.randomUUID().toString().replace("-", ""));
        }
        SpringApplication.run(BlockchainEduApplication.class, args);
    }
}
