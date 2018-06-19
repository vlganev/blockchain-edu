package blockchainedu.blockchainedu.models;

import blockchainedu.blockchainedu.dto.BlockchainResponseDto;
import blockchainedu.blockchainedu.dto.TransactionsResponseDto;
import blockchainedu.blockchainedu.helper.HashHelper;
import blockchainedu.blockchainedu.models.Account;
import com.google.common.io.BaseEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Blockchain implements Serializable {
    private HashHelper hashHelper;
    private List<Transaction> currentTransactions;
    private List<Block> chain;
    private Set<URL> nodes;
    private RestTemplate restTemplate;
    private HashMap<String, BigDecimal> accounts;
    private int mappedBlocksIndex;

    @Autowired
    public Blockchain(HashHelper hashHelper) {
        this.currentTransactions = Collections.synchronizedList(new LinkedList<>());
        this.chain = Collections.synchronizedList(new LinkedList<>());
        this.nodes = Collections.synchronizedSet(new HashSet<>());
        this.hashHelper = hashHelper;
        this.restTemplate = new RestTemplate();
        this.mappedBlocksIndex = 0;
        this.accounts = new HashMap<String, BigDecimal>();

        createBlock(new Block(100, "1"));
    }

    public Block createBlock(Block block) {
        block.setIndex(chain.size() + 1);
        if (block.getNonce() == 100 && block.getPreviousHash() == "1") {
            block.setTimestamp(0);
        } else {
            block.setTimestamp(System.currentTimeMillis());
        }
        this.currentTransactions.forEach(t -> t.didTransaction(chain.size() + 1));
        block.setTransactions(this.currentTransactions);
        if (block.getPreviousHash() == null) {
            block.setPreviousHash(hashHelper.hashBlock(block));
        }

        this.currentTransactions = Collections.synchronizedList(new LinkedList<>());
        this.chain.add(block);
        updateAccounts();

        return block;
    }

    public void updateAccounts() {
        for (int index=mappedBlocksIndex; index < getChainSize(); index++) {
            for (Transaction transaction: this.chain.get(index).getTransactions()) {
                BigDecimal amountRecipient = accounts.get(transaction.getRecipient());
                if (amountRecipient == null) {
                    accounts.put(transaction.getRecipient(), transaction.getAmount());
                } else {
                    BigDecimal currAmount = amountRecipient.add(transaction.getAmount());
                    accounts.put(transaction.getRecipient(), currAmount);
                }

                BigDecimal amountSender = accounts.get(transaction.getSender());
                if (amountSender == null) {
                    accounts.put(transaction.getSender(), transaction.getAmount().negate());
                } else {
                    BigDecimal currAmount = amountSender.subtract(transaction.getAmount());
                    accounts.put(transaction.getSender(), currAmount);
                }
            }
        }
        mappedBlocksIndex = this.chain.size();
    }

    public Map<String, BigDecimal> getAddressAmounts() {
        return accounts;
    }

    public Block getLastBlock() {
        return this.chain.get(this.chain.size() - 1);
    }

    public String createTransaction(Transaction transaction) {
        this.currentTransactions.add(transaction);
        String transactionHash = currentTransactions.get(currentTransactions.size() -1).generateTransactionHash();
        return transactionHash;
    }

    public long proofOfWork(long lastNonce) {
        long nonce = 0;
        while(!validProof(lastNonce, nonce)) {
            nonce++;
        }
        return nonce;
    }

    public boolean validProof(long lastNonce, long nonce) {
        String guess = BaseEncoding.base64().encode(String.format("{%d}{%d}", lastNonce, nonce).getBytes(Charset.forName("UTF8")));
        String guessHash = hashHelper.hash(guess);
        return guessHash.substring(0, 4).equals("0000");
    }

    public List<Block> getChain() {
        return Collections.unmodifiableList(this.chain);
    }

    public Block getBlock(int id) {
        return this.chain.get(id);
    }

    public void registerNode(URL url) {
        this.nodes.add(url);
    }

    public boolean validChain(List<Block> chain) {
        Block lastBlock = chain.get(0);
        Block block = null;
        int currentIndex = 0;
        int length = Math.min(chain.size(), this.chain.size());

        while (currentIndex < length) {
            block = chain.get(currentIndex);

            if (!chain.get(currentIndex).getPreviousHash().equals(this.chain.get(currentIndex).getPreviousHash())) {
                return false;
            }
            System.out.printf(Integer.toString(currentIndex));
            System.out.printf("this " +this.chain.get(currentIndex).getNonce());
            System.out.printf("chain " + chain.get(currentIndex).getNonce());
            if (currentIndex > 0) {
                if (!validProof(chain.get(currentIndex - 1).getNonce(), chain.get(currentIndex).getNonce())) {
                    return false;
                }
            }

            currentIndex++;
        }

        return true;
    }

    public boolean syncNode() {
        Set<URL> neighbours = this.nodes;
        List<Block> newChain = null;

        int maxLength = chain.size();

        // sync blocks
        for (URL node:neighbours) {
            BlockchainResponseDto response = restTemplate.getForObject(node.toString() + "/blocks", BlockchainResponseDto.class);

            int length = response.getLength();
            List<Block> chain = response.getChain();

            if (length > maxLength && validChain(chain)) {
                maxLength = length;
                newChain = chain;
            }
        }

        if (newChain != null) {
            this.chain = newChain;
            return true;
        }

        // sync pending transaction, one sender can do transactions only on 1 node
        // if there are transactions of the the sender on the other chains
        // his transactions will be dropped. On the next version will be fixed ;)
        syncPendingTransactions();

        return false;
    }

    public void syncPendingTransactions() {
        Set<URL> neighbours = this.nodes;

        for (URL node:neighbours) {
            TransactionsResponseDto response = restTemplate.getForObject(node.toString() + "/transaction/pending", TransactionsResponseDto.class);

            int length = response.getLength();
            List<Transaction> remoteTransactions = response.getTransactions();

            for (Transaction remoteTransaction : remoteTransactions) {
                currentTransactions.removeIf(t -> t.getSender().equals(remoteTransaction.getSender()));
            }
        }
    }

    public Transaction getTransactionInfo(String hash) {
        if (currentTransactions.stream().filter(t -> t.getTransactionHash().equals(hash)).findFirst().isPresent()) {
            return currentTransactions.stream()
                    .filter(t -> t.getTransactionHash().equals(hash))
                    .collect(Collectors.toList()).get(0);
        }

        for (Block block : this.chain) {
            for (Transaction transaction: block.getTransactions()) {
                System.out.printf(transaction.getTransactionHash());

                if (hash.equals(transaction.getTransactionHash())) {
                    return transaction;
                }
            }
        }
        return new Transaction();
    }

    public List<Transaction> getPendingTransaction() {
        return this.currentTransactions;
    }

    public long getChainSize() {
        return this.chain.size();
    }

    public Set<URL> getNodes() {
        return Collections.unmodifiableSet(this.nodes);
    }
}
