package blockchainedu.blockchainedu.dto;

import blockchainedu.blockchainedu.models.Transaction;

import java.io.Serializable;
import java.util.List;

public class TransactionsResponseDto implements Serializable {
    private List<Transaction> transactions;
    private int length;

    public TransactionsResponseDto() {

    }

    public TransactionsResponseDto(List<Transaction> transactions) {
        this.transactions = transactions;
        this.length = transactions.size();
    }

    public int getLength() {
        return length;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
