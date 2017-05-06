package Tree;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.BitSet;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TreeNode {
    private double probability;
    private byte symbol;
    private BitSet code;
    boolean isLeaf;
    List<TreeNode> subTree;
    private TreeNode parent;
}
