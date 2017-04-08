package test1;

/**
 * Created by if on 2017/3/9.
 *
 */

public class Test1 {
    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = newTree();
        BinarySearchTree<Integer> tree1 = newTree();
        tree1.insert(7);

        System.out.print("the preOrderRecursive of the tree:\t\t");
        tree.preOrderRecursive();
        System.out.print("\nthe preOrderNonRecursive of the tree:\t");
        for (int temp:tree.preOrderNonRecursive()) {
            System.out.print(temp + "\t");
        }

        System.out.print("\nthe inOrderRecursive of the tree:\t\t");
        tree.inOrderRecursive();
        System.out.print("\nthe inOrderNonRecursive of the tree :\t");
        for (int temp:tree.inOrderNonRecursive()) {
            System.out.print(temp + "\t");
        }
        System.out.println("\nfindMin() " + tree.findMin());
        System.out.println("numberOfKLevelTreeNode(2) " + tree.numberOfKLevelTreeNode(2));
        System.out.println("isBalance() " + tree.isBalance());
        System.out.println("isEmpty() " + tree.isEmpty());
        System.out.println("numberOfLeafNode() " + tree.numberOfLeafNode());
        System.out.println("numberOfTreeNode() " + tree.numberOfTreeNode());
        System.out.println("minDepthOfTree() " + tree.minDepthOfTree());
        System.out.println("maxDepthOfTree() " + tree.maxDepthOfTree());
        System.out.println("isCompleteTree() " + tree.isCompleteTree());
        System.out.println("isSameTreeNode(tree1) " + tree.isSameTreeNode(tree1));
//        tree.mirrorTree();
//        System.out.println("after mirror ");
//        tree.preOrderRecursive();
        System.out.println("theLastCommonParent:" + tree.getLastCommonParent(1,3));


    }

    private static BinarySearchTree<Integer> newTree() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.insert(4);
        tree.insert(2);
        tree.insert(1);
        tree.insert(3);
        tree.insert(6);
        tree.insert(5);

        return tree;
    }
}
