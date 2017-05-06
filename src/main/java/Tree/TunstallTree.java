package Tree;

import Helpers.CodesGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TunstallTree {

    private List<TreeNode> tree;
    private int N,Q,K,k;
    private double[] probability;
    private List<BitSet> codeBook;

    public TunstallTree(int N, int K, int k, double[] probability){
        this.N=N;
        this.K=K;
        this.k=k;
        this.Q=0;
        this.probability = probability;
        this.codeBook = CodesGenerator.getCodeList(k);
        tree = generateTunstallTree();

    }

    private List<TreeNode> generateTunstallTree() {
        List<TreeNode> tree = new ArrayList<TreeNode>();

        //Initialize the tree
        for(int i=0;i<N;i++){
            tree.add(new TreeNode(probability[i], (byte)i, codeBook.get(i), true, null, null));
        }
        Q+=N;

        //Main tree loop
        while(K-Q>N-1){
            TreeNode maxProbNode = getFirstLeaf(tree.get(0));
            searchForBiggestProb(tree, maxProbNode);
            BitSet freeCode = maxProbNode.getCode();
            double maxProb = maxProbNode.getProbability();
            maxProbNode.setCode(null);
            maxProbNode.setLeaf(false);
            maxProbNode.setSubTree(new ArrayList<TreeNode>());

            //Initialize subtree
            maxProbNode.getSubTree().add(new TreeNode(maxProb*probability[0], (byte)0, freeCode, true, null, null));
            for(int i=1;i<N;i++) {
                maxProbNode.getSubTree().add(new TreeNode(maxProb * probability[i], (byte) i, codeBook.get(Q), true, null, null));
                Q++;
            }
        }
        return tree;
    }

    private TreeNode getFirstLeaf(TreeNode treeNode) {
        if(treeNode.isLeaf) return treeNode;
        return getFirstLeaf(treeNode.getSubTree().get(0));
    }

    private void searchForBiggestProb(List<TreeNode> tree, TreeNode maxProbNode) {
        for (TreeNode node:tree) {
            if (!node.isLeaf) {
                searchForBiggestProb(node.getSubTree(), maxProbNode);
            }else {
                if (node.getProbability() > maxProbNode.getProbability()) {
                    maxProbNode = node;
                }
            }
        }
    }
}
