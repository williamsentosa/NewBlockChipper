package Bits;

/**
 * Kelas ini untuk merepresentasikan block
 * @author William Sentosa
 */
public class Block {
    private Bit[] bits;
    private int size;
    private static final int defaultSize = 8;
    
    public Block() {
        size = defaultSize;
        bits = new Bit[size];
        for(int i=0; i<size; i++) {
            bits[i] = new Bit();
        }
    }
    
    public Block(int size) {
        this.size = size;
        bits = new Bit[size];
        for(int i=0; i<size; i++) {
            bits[i] = new Bit();
        }
    }
    
    public int getSize() {
        return size;
    }
    
    public Bit[] getBits() {
        return bits;
    }
    
    public void setBits(Bit[] bits) {
        this.bits = bits;
    }
    
    public static int getDefaultSize() {
        return defaultSize;
    }
    
    @Override
    public String toString() {
        String result = "";
        for(int i=0; i<bits.length; i++) {
            result = result + bits[i];
        }
        return result;
    }
    
    /**
     * Mengxor dirinya dengan block lain
     * @param anotherBlock block akan dixor dengan block ini
     */
    public void xor(Block anotherBlock) {
        assert(size == anotherBlock.getSize());
        for(int i=0; i<size; i++) {
            bits[i].setValue(bits[i].getValue() ^ anotherBlock.getBits()[i].getValue());
        }
    }
    
    /**
     * Melakukan fungsi f dengan key tertentu
     * @param key sub key dari kunci internal
     */
    public void fFunction(Bit[] subKey) {
        // Not Implemented Yet
    }
    
    
}
