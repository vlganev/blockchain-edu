package blockchainedu.blockchainedu.models;

import java.util.ArrayList;

public class Address {
    public String AddressId;
    public long Amount;
    public ArrayList<Transaction> Transactions;

    public Address(String address) {
        //TODO: validate address?
        this.AddressId = address;
    }

    private static Address _generatorAddress;
    public static Address GeneratorAddress;

    public static Address getGeneratorAddress() {
        if (_generatorAddress == null)
        {
            _generatorAddress = new Address("0"); ;
        }

        return _generatorAddress;
    }
}
