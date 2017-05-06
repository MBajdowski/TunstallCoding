package Tunstall;

import Tree.TunstallTree;
import com.sun.javaws.exceptions.InvalidArgumentException;

public class  Tunstall {

    private int N=256;
    private double[] probability;
    private int K;
    private TunstallTree tunstallTree;


    public Tunstall(byte[] byteArray, int k) throws InvalidArgumentException{
        this.probability = calculateProbability(byteArray);
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

    public byte[] generateCodedFile() {

        return null;
    }
}
