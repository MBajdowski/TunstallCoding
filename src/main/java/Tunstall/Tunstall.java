package Tunstall;

public final class  Tunstall {

    public static double[] getProbability(byte[] byteArray){
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

    public static void buildTree(){

    }
}
