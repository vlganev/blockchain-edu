package blockchainedu.blockchainedu.controllers;

import blockchainedu.blockchainedu.models.Blockchain;
import blockchainedu.blockchainedu.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TransactionController {

    @Autowired
    private Blockchain blockchain;

    @Value("${blockchain.node.id}")
    private String blockchainNodeId;

    @RequestMapping(value = "/transaction", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> createTransaction(@RequestBody Transaction transaction) {
        String transactionHash = blockchain.createTransaction(transaction);

        long index = blockchain.getChainSize() + 1;
        Map<String, Object> response = new HashMap<>();
        response.put("message", String.format("Transaction will be added to Block {%d}", index));
        response.put("transactionHash", transactionHash);

        return response;
    }

    @RequestMapping(value = "/transaction/{hash}")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Transaction getTransactionInfo(@PathVariable("hash") String hash) {
        Transaction transaction = blockchain.getTransactionInfo(hash);
        return transaction;
    }

    @RequestMapping(value = "/transaction/pending")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody List<Transaction> getPendingTransaction() {
        List<Transaction> transactions = blockchain.getPendingTransaction();
        return transactions;
    }
}
