package blockchainedu.blockchainedu.models;


import java.io.Serializable;
import java.util.List;

public class Block implements Serializable {
    private long index;
    private long timestamp;
    private List<Transaction> transactions;
    private long nonce;
    private String previousHash;

    public Address minedBy;
    public long difficulty;
    public String blockDataHash;

    public Block() {}

    public Block(long nonce, String previousHash) {
        this.nonce = nonce;
        this.previousHash = previousHash;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public long getNonce() {
        return nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }
}
