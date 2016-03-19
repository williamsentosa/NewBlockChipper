package Bits;

/**
 *
 * @author William Sentosa
 */
public class Key {
    private Bit bits[];
    private int size;
    private final int defaultSize = 128;
    
    public Key() {
        size = defaultSize;
        bits = new Bit[size];
        for(int i=0; i<size; i++) {
            bits[i] = new Bit();
        }
    }
    
    /**
     * Consructor
     * @param key string key yang ingin diubah menjadi array of bit
     * @param size panjang key yang seharusnya, dalam bit = 128 bit
     */
    public Key(String key, int size) {
        this.size = size;
        setKey(key, size);
    }
    
    public Bit[] getBits() {
        return bits;
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
     * mengubah string menjadi bits
     * @param key String key yang ingin diubah menjadi array of bit
     * @param s panjang key yang seharusnya, dalam bit = 128 bit
     */
    public void setKey(String key, int s) {
        size = s;
        // Ubah key menjadi Bits dengan panjang s
        encryptKey();
    }
    
    /**
     * Melakukan enkripsi dengan vigenere chipper 256 bit
     */
    private void encryptKey() {
        Bit[] key = generateKeyForVigenere();
        // Lakukan enkripsi dengan key sebagai kuncinya
    }
    
    /**
     * Menghasilkan kunci untuk dienkripsi dengan vigenere
     * @return kunci untuk enkripsi
     */
    private Bit[] generateKeyForVigenere() {
        // Buat key untuk chipper dengan mengguankan vigenere chipper
        return null;
    }
    
}
