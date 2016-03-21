/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bits;

/**
 *
 * @author William Sentosa
 */
public class Text {
    private Block[] blocks;
    private int sizeOfBlocks;
    private final int defaultSizeOfBlocks = 16; 
    
    public Text() {
        sizeOfBlocks = defaultSizeOfBlocks;
        blocks = new Block[sizeOfBlocks];
        for(int i=0; i<sizeOfBlocks; i++) {
            blocks[i] = new Block();
        }
    }
    
    /**
     * Ubah dari text ke blocks
     * @param text string yang mau diubah
     * @param sizeOfBits jumlah bits untuk setiap block message = 128
     */
    public Text(String text, int sizeOfBits) {
        sizeOfBlocks = sizeOfBits / Block.getDefaultSize();
        setText(text);
    }
    
    public Block[] getBlocks() {
        return blocks;
    }
    
    public void setBlocks(Block[] blocks) {
        this.blocks = blocks;
    }
    
    public void setText(String text) {
        // Ubah dari string ke blocks
    }
    
    public String toString() {
        String result = "";
        for(int i=0; i<blocks.length; i++) {
            result = result + blocks[i];
            result = result + "\n";
        }
        return result;
    }
    
    /**
     * Ubah dari blocks ke string
     * @return string dari sekumpulan blocks
     */
    public String getText() {
        // Ubah dari blocks ke text
        return null;
    }
    
}
