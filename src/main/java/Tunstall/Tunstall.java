package Tunstall;

import Tree.TunstallTree;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.apache.commons.lang3.ArrayUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class Tunstall {

    private int N = 256;
    private int[] nrOfBits;
    private byte[] byteArray;
    private double[] probability;
    private int K, k;
    private TunstallTree tunstallTree;


    public Tunstall(byte[] byteArray, int k) throws InvalidArgumentException {
        this.byteArray = byteArray;
        this.nrOfBits = countBytes(byteArray);
        this.probability = calculateProbability(nrOfBits);
        this.k = k;
        this.K = (int) Math.pow(2, k);
        if (K <= N) {
            throw new InvalidArgumentException(new String[]{"2^k must be greater than N!"});
        }
        this.tunstallTree = new TunstallTree(N, K, k, probability);
    }

    public int getN() {
        return N;
    }

    public List<Byte> getInputBytes() {
        return Arrays.asList(ArrayUtils.toObject(byteArray));
    }

    public double getProbability(final Byte ofByte) {
        return probability[ofByte & 0xFF];
    }

    private int[] countBytes(byte[] byteArray) {
        int[] nrOfBytes = new int[N];
        for (int i = 0; i < N; i++) {
            nrOfBytes[i] = 0;
        }
        for (byte number : byteArray) {
            nrOfBytes[number & 0xFF]++;
        }
        return nrOfBytes;
    }

    private double[] calculateProbability(int[] nrOfBytes) {
        double[] probability = new double[N];
        for (int i = 0; i < N; i++) {
            probability[i] = (double) nrOfBytes[i] / byteArray.length;
        }
        return probability;
    }

    public byte[] generateCodedFile() throws InvalidArgumentException {
        BitSet bits = generateHeader();
        int nrOfBitsInHeader = (N + 1) * 4 * 8;
        int nrOfWords = 0;
        List<Byte> temp = new ArrayList<Byte>();
        for (int i = 0; i < byteArray.length; i++) {
            temp.add(byteArray[i]);
            BitSet code = tunstallTree.getCode(temp);
            if (code == null) {
                if (byteArray.length - 1 == i) {
                    //kodowanie ostatnich znaków + znak specjalny
                    for (int j = 0; j < k; j++) {
                        bits.set(nrOfBitsInHeader + nrOfWords * k + j);
                    }
                    nrOfWords++;
                    BitSet lastSymbols = BitSet.valueOf(ArrayUtils.toPrimitive((Byte[]) temp.toArray()));
                    for (int j = 0; j < temp.size() * 8; j++) {
                        if (lastSymbols.get(j)) {
                            bits.set(nrOfBitsInHeader + nrOfWords * k + j);
                        }
                    }
                }
                continue;
            }
            for (int j = 0; j < k; j++) {
                if (code.get(j)) {
                    bits.set(nrOfBitsInHeader + nrOfWords * k + j);
                }
            }
            nrOfWords++;
            temp.clear();
        }
        return bits.toByteArray();
    }

    private BitSet generateHeader() {
        int[] intHeader = new int[nrOfBits.length + 1];
        intHeader[0] = k;
        for (int i = 0; i < nrOfBits.length; i++) {
            intHeader[i + 1] = nrOfBits[i];
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(intHeader.length * 4);
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(intHeader);

        byte[] byteArray = byteBuffer.array();
        return BitSet.valueOf(byteArray);
    }
}
