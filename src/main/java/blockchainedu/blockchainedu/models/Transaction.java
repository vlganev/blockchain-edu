package blockchainedu.blockchainedu.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class Transaction implements Serializable {

    private String sender;

    private String recipient;

    private BigDecimal amount;

    public String senderPublickKey;
    public String senderSignature;
    public int minedInBlockIndex;
    public Boolean paid;

    private String _transactionData;
    public String TransactionData;

    public Transaction() {}

    public Transaction(String sender, String recipient, BigDecimal amount, String senderPublickKey, String senderSignature) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.senderPublickKey = senderPublickKey;
        this.senderSignature = senderSignature;
        this.paid = false;
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
