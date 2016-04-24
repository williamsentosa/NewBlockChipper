/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newblockchipper;

import bits.Bit;
import bits.Block;
import bits.Key;
import bits.Text;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author William Sentosa
 * @author Randi Chilyon Alfianto
 */
public class NewBlockChipper {
    private Key key;
    private Text text;
    private String result;
    private Block[] blocks;
    
    public NewBlockChipper() {
        key = new Key();
        text = new Text();
    }
    
    public NewBlockChipper(String source, String key) {
        try {
            text = new Text();
            text.setText(source);
            this.key = new Key();
            this.key.setKey(key, 128);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(NewBlockChipper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private ArrayList<Bit[]> divideBits(Bit[] bitKey) {
        ArrayList<Bit[]> result = new ArrayList<Bit[]>();
        for(int i=0; i<8; i++) {
            int idx = 0;
            Bit[] element = new Bit[16];
            for(int j = 16 * i; j < 16 * (i+1); j++) {
                element[idx] = bitKey[j];
                idx++;
            }
            result.add(element);
        }
        return result;
    }
    
    public String encrypt() {
        ArrayList<Block> blocks = text.getBlocks();
        if(blocks.size() % 2 != 0) {
            Block temp = new Block();
            blocks.add(temp);
        }
        System.out.println(blocks);
        Bit[] bitKey = key.getBits();
        ArrayList<Bit[]> subKey = divideBits(bitKey);
        int idx = 0;
        for(int i=0; i<blocks.size(); i+=2) {
            // Bekerja dengan model jaringan feistel
            for(int j=0; j<8; j++) {
                if(j % 2 == 0) {
                    blocks.get(i+1).fFunction(subKey.get(idx));
                    blocks.get(i).xor(blocks.get(i+1));
                    blocks.get(i+1).reverseFFunction(subKey.get(idx));
                } else {
                    blocks.get(i).fFunction(subKey.get(idx));
                    blocks.get(i+1).xor(blocks.get(i));
                    blocks.get(i).reverseFFunction(subKey.get(idx));
                }
            }
            idx++;
            if(idx >= subKey.size()) {
                idx = 0;
            }
        }
        System.out.println(blocks);
        Text temp = new Text();
        temp.setBlocks(blocks);
        System.out.println("Ukuran Blok = " + blocks.size());
        try {
            return temp.getText();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(NewBlockChipper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String ecb() {
        String result = "";
        ArrayList<Block> blocks = text.getBlocks();
        Bit[] bitKey = key.getBits();
        ArrayList<Bit[]> subKey = this.divideBits(bitKey);
        for(int i=0; i<blocks.size(); i++) {
            blocks.get(i).fFunction(subKey.get(0));
        }
        Text temp = new Text();
        temp.setBlocks(blocks);
        System.out.println(blocks);
        try {
            return temp.getText();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(NewBlockChipper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
   
    public String cbc() {
        String result = "";
        ArrayList<Block> blocks = text.getBlocks();
        Bit[] bitKey = key.getBits();
        ArrayList<Bit[]> subKey = this.divideBits(bitKey);
        Bit[] tempKey = subKey.get(0);
        for(int i=0; i<blocks.size(); i++) {
            blocks.get(i).fFunction(tempKey);
            tempKey = blocks.get(i).getBits();
        }
        Text temp = new Text();
        temp.setBlocks(blocks);
        System.out.println(blocks);
        try {
            return temp.getText();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(NewBlockChipper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public String cfb() {
        String result = "";
        ArrayList<Block> blocks = text.getBlocks();
        Bit[] bitKey = key.getBits();
        ArrayList<Bit[]> subKey = this.divideBits(bitKey);
        Bit[] tempKey = subKey.get(0);
        Block b = new Block(blocks.get(0));
        Block[] p = new Block[blocks.size()];
        for(int i=0; i<blocks.size(); i++) {
            p[i] = new Block(blocks.get(i));
        }
        for(int i=1; i<blocks.size(); i++) {
            b.fFunction(tempKey);
            blocks.set(i, b);
            blocks.get(i).xor(p[i]);
            b = new Block(blocks.get(i));
        }
        Text temp = new Text();
        temp.setBlocks(blocks);
        System.out.println(blocks);
        try {
            return temp.getText();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(NewBlockChipper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public String decrypt() {
        ArrayList<Block> blocks = text.getBlocks();
        System.out.println(blocks);
        System.out.println("Ukuran blok awal : " + blocks.size());
        Bit[] bitKey = key.getBits();
        ArrayList<Bit[]> subKey = divideBits(bitKey);
        int idx = 0;
        for(int i=0; i<blocks.size(); i+=2) {
            // Bekerja dengan model jaringan feistel
            for(int j=0; j<8; j++) {
                if(j % 2 == 0) {
                    blocks.get(i).fFunction(subKey.get(idx));
                    blocks.get(i+1).xor(blocks.get(i));
                    blocks.get(i).reverseFFunction(subKey.get(idx));
                } else {
                    blocks.get(i+1).fFunction(subKey.get(idx));
                    blocks.get(i).xor(blocks.get(i+1));
                    blocks.get(i+1).reverseFFunction(subKey.get(idx));
                }
            }
            idx++;
            if(idx >= subKey.size()) {
                idx = 0;
            }
        }
        Text temp = new Text();
        temp.setBlocks(blocks);
        System.out.println(blocks);
        System.out.println("Ukuran Blok = " + blocks.size());
        try {
            return temp.getText();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(NewBlockChipper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String text = "william sentosa dan randi chilyon alfianto";
        String key = "ifitb";
        NewBlockChipper chipper;
        chipper = new NewBlockChipper(text, key);
        Instant first = Instant.now();
        String result = chipper.cfb();
        Instant second = Instant.now();
        Duration duration = Duration.between(first, second);
        System.out.println(result);
        System.out.println("Duration : " + duration.getNano() + " ns");
        result = chipper.encrypt();
        System.out.println("Panjang result : " + result.length());
        System.out.println("*** Chipper ***");
        System.out.println(result);
        NewBlockChipper enchiper = new NewBlockChipper(result, key);
        result = enchiper.decrypt();
        System.out.println("*** Dechipper ***");
        System.out.println(result);
        System.out.println("Panjang result : " + result.length());
    }
    
}
