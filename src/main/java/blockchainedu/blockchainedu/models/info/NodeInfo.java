package blockchainedu.blockchainedu.models.info;

public class NodeInfo {
    public String About;
    public String NodeName;
    public int Peers;
    public int Blocks;
    public long ConfirmedTransactions;
    public long PendingTransactions;
    public long Addresses;

    public String getAbout() {
        return About;
    }

    public void setAbout(String about) {
        About = about;
    }

    public String getNodeName() {
        return NodeName;
    }

    public void setNodeName(String nodeName) {
        NodeName = nodeName;
    }

    public int getPeers() {
        return Peers;
    }

    public void setPeers(int peers) {
        Peers = peers;
    }

    public int getBlocks() {
        return Blocks;
    }

    public void setBlocks(int blocks) {
        Blocks = blocks;
    }

    public long getConfirmedTransactions() {
        return ConfirmedTransactions;
    }

    public void setConfirmedTransactions(long confirmedTransactions) {
        ConfirmedTransactions = confirmedTransactions;
    }

    public long getPendingTransactions() {
        return PendingTransactions;
    }

    public void setPendingTransactions(long pendingTransactions) {
        PendingTransactions = pendingTransactions;
    }

    public long getAddresses() {
        return Addresses;
    }

    public void setAddresses(long addresses) {
        Addresses = addresses;
    }

    public long getCoins() {
        return Coins;
    }

    public void setCoins(long coins) {
        Coins = coins;
    }

    public long Coins;
}
