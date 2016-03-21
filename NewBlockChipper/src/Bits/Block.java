package Bits;

/**
 * Kelas ini untuk merepresentasikan block
 * @author William Sentosa
 */
public class Block {
    private Bit[] bits;
    private int size;
    private static final int defaultSize = 16;
    
    public Block() {
        size = defaultSize;
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
    
    private Bit[][] convertToMatrix(Bit[] bits, int size) {
        Bit[][] result = new Bit[4][4];
        for(int i=0; i<size; i++) {
            for(int j=0; j<size; j++) {
                result[i][j] = bits[i*size + j];
            }
        }
        return result;
    }
    
    
    /**
     * Melakukan fungsi f dengan key tertentu
     * @param key sub key dari kunci internal
     */
    public void fFunction(Bit[] subKey) {
        // Not Implemented Yet
        Bit[][] matrix = convertToMatrix(bits, 4);
        Boolean temp;
        for(int i=0; i<subKey.length; i++) {
            if(i/4 == 0 || i/4 == 2) { // Pergeseran elemen pada baris
                if(subKey[i].convertToInt() == 0) { // Geser Kiri
                    temp = matrix[i%4][0].getValue();
                    for(int j=1; j>4; j++) {
                        matrix[i%4][j-1].setValue(matrix[i%4][j].getValue());
                    }
                    matrix[i%4][4].setValue(temp);
                } else {
                    temp = matrix[i%4][4].getValue();
                    for(int j=2; j>=0; j--) {
                        matrix[i%4][j+1].setValue(matrix[i%4][j].getValue());
                    }
                    matrix[i%4][0].setValue(temp);
                }
                // Xor perbaris
                for(int j=0; j<4; j++) { // Geser Kanan
                    matrix[i%4][j].setValue(matrix[i%4][j].getValue() ^ subKey[i].getValue());
                }
            } else if(i/4 == 1 || i/4 == 3) { // pergeseran elemen per kolom
                if(subKey[i].convertToInt() == 0) { // Geser Kiri
                    temp = matrix[0][i%4].getValue();
                    for(int j=3; j>0; j--) {
                        matrix[j][i%4].setValue(matrix[j-1][i%4].getValue());
                    }
                    matrix[i%4][4].setValue(temp);
                } else { // belom di implementasi
                    for(int j=0; j<4; j++) { // Geser Kanan
                        temp = matrix[j][i%4].getValue();
                        matrix[j][i%4].setValue(matrix[(j+1)%4][i%4].getValue());
                        matrix[(j+1)%4][i%4].setValue(temp);
                    }
                }
                // Xor perbaris
                for(int j=0; j<4; j++) { // Geser Kanan
                    matrix[j][i%4].setValue(matrix[j][i%4].getValue() ^ subKey[i].getValue());
                }
            }
        }
        
    }
    
    
}
