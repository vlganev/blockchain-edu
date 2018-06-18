package blockchainedu.blockchainedu.models;

import blockchainedu.blockchainedu.helper.HashHelper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class Transaction implements Serializable {
    private String sender;
    private String recipient;
    private BigDecimal amount;
    public String senderPublickKey;
    public String senderSignature;
    public long minedInBlockIndex;
    private String transactionHash;
    private Boolean paid;

    public Transaction() {}

    public String getTransactionHash() {
        return transactionHash;
    }

    public Transaction(String sender, String recipient, BigDecimal amount, String senderPublickKey, String senderSignature) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.senderPublickKey = senderPublickKey;
        this.senderSignature = senderSignature;
        this.paid = false;
    }

    public void didTransaction(long index) {
        this.minedInBlockIndex = index;
        this.paid = true;
    }

    public String generateTransactionHash() {
        HashHelper hashHelper = new HashHelper();
        transactionHash = hashHelper.hash(System.currentTimeMillis() + sender + recipient + amount);
        this.transactionHash = transactionHash;
        return transactionHash;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
