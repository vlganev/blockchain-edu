package blockchainedu.blockchainedu.controllers;

import blockchainedu.blockchainedu.dto.BlockchainResponseDto;
import blockchainedu.blockchainedu.helper.HashHelper;
import blockchainedu.blockchainedu.models.Blockchain;
import blockchainedu.blockchainedu.models.Block;
import blockchainedu.blockchainedu.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class BlockController {

    @Autowired
    private Blockchain blockchain;

    @Value("${blockchain.node.id}")
    private String blockchainNodeId;

    @RequestMapping("/mine")
    public Map<String, Object> mine() {
        Block lastBlock = blockchain.getLastBlock();
        long lastNonce = lastBlock.getNonce();

        long nonce = blockchain.proofOfWork(lastNonce);

        blockchain.createTransaction(new Transaction("0", blockchainNodeId, BigDecimal.valueOf(1), "0", "0"));

        Map<String, Object> response = new HashMap<>();
        HashHelper hashHelper = new HashHelper();

        if (!blockchain.syncNode()) {
            Block block = blockchain.createBlock(new Block(nonce, hashHelper.hashBlock(lastBlock)));
            response.put("message", "New Block Forged");
            response.put("index", block.getIndex());
            response.put("transactions", block.getTransactions());
            response.put("nonce", block.getNonce());
            response.put("previous_hash", block.getPreviousHash());
        } else {
            response.put("error", "Another block is created");
        }

        return response;
    }

    @RequestMapping("/get-miner-job")
    public Map<String, Object> getMinerJob() {
        Block lastBlock = blockchain.getLastBlock();
        long index = lastBlock.getIndex()+1;
        String prevBlockHash = lastBlock.getPreviousHash();
        int difficulty = 4;

        Map<String, Object> response = new HashMap<>();
        response.put("index", index);
        response.put("prevBlockHash", prevBlockHash);
        response.put("difficulty", difficulty);
        return response;
    }

    @RequestMapping("/blocks")
    public BlockchainResponseDto getBlocks() {
        return new BlockchainResponseDto(blockchain.getChain());
    }

    @RequestMapping(value = "/block/{id}")
    public @ResponseBody Block block(@PathVariable("id") int id) {
        Block block = blockchain.getBlock(id);
        return block;
    }
}
