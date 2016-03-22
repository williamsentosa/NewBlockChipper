package Bits;

/**
 *
 * @author William Sentosa
 */

import encrypt.VigenereCipher;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;


public class Key {
    private Bit bits[];
    private int size;
    private final int defaultSize = 128;
    private String keyText;
    private String encryptedKey;
    VigenereCipher VC;
    ByteConverter BC;
    
    public Key() {
        size = defaultSize;
        bits = new Bit[size];
        for(int i=0; i<size; i++) {
            bits[i] = new Bit();
        }
        VC = new VigenereCipher();
        BC = new ByteConverter();
    }
    
    /**
     * Consructor
     * @param key string key yang ingin diubah menjadi array of bit
     * @param size panjang key yang seharusnya, dalam bit = 128 bit
     */
    public Key(String key, int size) throws UnsupportedEncodingException {
        this.size = size; 
        setKey(key, size);
    }
    
    public int getSize() {
        return size;
    }
    
    public Bit[] getBits() {
        return bits;
    }
    
    public String getKeyText() {
        return keyText;
    }
    
    public String getEncryptedText() {
        return encryptedKey;
    }
    
    private void setKeyText(String s){
        String sTemp = s;
        while(true){
            if (s.length()<16){
                s += sTemp;
            }
            else{
                break;
            }
        }
        keyText = s.substring(0, 16);
    }
    
    public void setEncryptedKey() throws UnsupportedEncodingException{
        encryptedKey = VC.Encrypt(keyText, generateKeyForVigenere(), 256);
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
    public void setKey(String key, int s) throws UnsupportedEncodingException {
        size = s;
        this.setKeyText(key);
        this.setEncryptedKey();
        
        
        
        // Ubah key menjadi Bits dengan panjang s
        
    }
    
    /**
     * Menghasilkan kunci untuk dienkripsi dengan vigenere
     * @return kunci untuk enkripsi
     */
    private String generateKeyForVigenere() throws UnsupportedEncodingException {
        // Buat key untuk chipper dengan menggunakan vigenere chipper
        byte[] keyByte = keyText.getBytes();
        Bit[] tempBit = BC.convertByteToBits(keyByte[0]);
        for (int i = 1;i<keyByte.length;i++){
            tempBit = concat(tempBit,BC.convertByteToBits(keyByte[i]));
        }
        Bit[] keyVigBit = new Bit[32];
        int count = 0;
        for (int i = 0;i<tempBit.length;i++){
            if (i%4 == 0){
                keyVigBit[count] = tempBit[i];
                count++;
            }
        }
        Bit[] tempVigBit = new Bit[8];
        byte[] arrVigKeyByte = new byte[4];
        int i = 0;
        int k = 0;
        for(int j = 0;j<keyVigBit.length;j++){
            tempVigBit[i] = keyVigBit[j];
            if((j+1) % 8 == 0){
                arrVigKeyByte[k] = BC.convertBitsToByte(tempVigBit);
                tempVigBit = new Bit[8];
                k++;
                i = 0;
            }
            i++;
        }
        
        String vigKey = new String(arrVigKeyByte,"UTF-8");
        return vigKey;
    }
    
    public Bit[] concat(Bit[] a, Bit[] b) {
        int aLen = a.length;
        int bLen = b.length;
        Bit[] c= new Bit[aLen+bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }
    
}
