package blockchainedu.blockchainedu.dto;

import blockchainedu.blockchainedu.models.Block;

import java.io.Serializable;
import java.util.List;

public class BlockchainResponseDto implements Serializable {

    private List<Block> chain;

    private int length;

    public BlockchainResponseDto() {

    }

    public BlockchainResponseDto(List<Block> chain) {
        this.chain = chain;
        this.length = chain.size();
    }

    public List<Block> getChain() {
        return chain;
    }

    public int getLength() {
        return length;
    }

    public void setChain(List<Block> chain) {
        this.chain = chain;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
