package blockchainedu.blockchainedu.models;


import blockchainedu.blockchainedu.helper.HashHelper;

import java.io.Serializable;
import java.util.List;

public class Block implements Serializable {
    private long index;
    private long timestamp;
    private List<Transaction> transactions;
    private long nonce;
    private String previousHash;

    public String minedBy;
    public long difficulty;

    public Block() {}

    public Block(String previousHash, long index, long timestamp, long nonce) {
        HashHelper hashHelper = new HashHelper();
        this.previousHash = previousHash;
        this.index = index;
        this.timestamp = timestamp;
        this.nonce = nonce;
    }

    public Block(BlockCandidate block, String previousHash, int difficulty, String address) {
        HashHelper hashHelper = new HashHelper();
        this.previousHash = previousHash;
        this.index = block.getIndex();
        this.timestamp = block.getTimestamp();
        this.nonce = block.getNonce();
        this.difficulty = difficulty;
        this.minedBy = address;
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
