package Tunstall;

import Tree.TunstallTree;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class  Tunstall {

    private int N=256;
    private byte[] byteArray;
    private double[] probability;
    private int K, k;
    private TunstallTree tunstallTree;


    public Tunstall(byte[] byteArray, int k) throws InvalidArgumentException{
        this.byteArray = byteArray;
        this.probability = calculateProbability(byteArray);
        this.k = k;
        this.K=(int)Math.pow(2,k);
        if(K<=N){
            throw new InvalidArgumentException(new String[]{"2^k must be greater than N!"});
        }
        this.tunstallTree = new TunstallTree(N, K, k, probability);
    }


    private double[] calculateProbability(byte[] byteArray){
        double[] probability = new double[256];
        int[] nrOfBytes = new int[256];
        for(int i=0;i<256;i++){
            probability[i]=0;
            nrOfBytes[i]=0;
        }
        for(byte number : byteArray){
            nrOfBytes[number & 0xFF]++;
        }
        for(int i =0;i<256;i++) {
            probability[i]=(double)nrOfBytes[i]/byteArray.length;
        }
        return probability;
    }

    public byte[] generateCodedFile() throws InvalidArgumentException {
        BitSet bits = new BitSet();
        int nrOfWords = 0;
        List<Byte> temp = new ArrayList<Byte>();
        for (int i=0;i<byteArray.length;i++){
            temp.add(byteArray[i]);
            BitSet code = tunstallTree.getCode(temp);
            if(code==null){
                if(byteArray.length-1==i) {
                    for (int j = 0; j < k; j++) {
                        bits.set(nrOfWords * k + j);
                    }
                    //TODO: kod ostatnich znaków
                }
                continue;
            }
            for(int j=0; j<k;j++){
                if(code.get(j)) {
                    bits.set(nrOfWords*k+j);
                }
            }
            nrOfWords++;
            temp.clear();
        }
        return bits.toByteArray();
    }
}
