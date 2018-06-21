package blockchainedu.blockchainedu.models;

import blockchainedu.blockchainedu.helper.HashHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public class BlockCandidate implements Serializable {
    private String previousHash;
    private long index;
    private long timestamp;
    private long nonce;
    private String hash;
    private String address;

    public BlockCandidate() {}

    public BlockCandidate(String previousHash, long index, long timestamp, long nonce, String hash, String address) {
        this.previousHash = previousHash;
        this.index = index;
        this.timestamp = timestamp;
        this.nonce = nonce;
        this.hash = hash;
        this.address = address;
    }

    public boolean isValid(Block lastBlock, int difficulty) {
        HashHelper hashHelper = new HashHelper();

        if (!this.previousHash.equals(lastBlock.getPreviousHash().trim())) {
            return false;
        }

        String calculatedHash = hashHelper.hash(
                this.index +
                this.previousHash +
                this.timestamp +
                this.nonce);

        String difficultyString = new String(new char[difficulty]).replace('\0', '0');
        if (calculatedHash.startsWith(difficultyString)) {
            return true;
        }
        return false;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
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

    public long getNonce() {
        return nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}