package Tree;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TreeNode {
    private double probability;
    private byte symbol;
    boolean isLeaf;
    private TreeNode leftChild;
    private TreeNode rightChild;
    private TreeNode parent;

}
