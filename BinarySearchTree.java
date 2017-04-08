package test1;


import org.omg.CORBA.Any;

import java.util.*;

/**
 * Created by if on 2017/3/10.
 * usage of BinarySearchTree
 */
public class BinarySearchTree<AnyType> {

    private static class BinaryNode<AnyType> {
        AnyType element;
        BinaryNode<AnyType> left;
        BinaryNode<AnyType> right;

        BinaryNode(AnyType theElement) {
            this(theElement, null, null);
        }

        BinaryNode(AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt) {
            element = theElement;
            left = lt;
            right = rt;
        }

        @Override
        public String toString() {
            return "element is " + this.element.toString();
        }
    }

    private BinaryNode<AnyType> root;
    private Comparator<? super AnyType> cmp;

    public BinarySearchTree() {
        this(null);
    }

    public BinarySearchTree(Comparator<? super AnyType> c) {
        root = null;
        cmp = c;
    }

    private int myCompare(AnyType lhs, AnyType rhs) {
        if (cmp != null) {
            return cmp.compare(lhs, rhs);
        } else {
            return ((Comparable) lhs).compareTo(rhs);
        }
    }

    public void makeEmpty() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean contains(AnyType x) {
        return contains(x, root);
    }

    private boolean contains(AnyType x, BinaryNode<AnyType> t) {
        if (t == null) {
            return false;
        }
        int compareResult = myCompare(x, t.element);
        if (compareResult < 0) {
            return contains(x, t.left);

        } else if (compareResult > 0) {
            return contains(x, t.right);
        } else {
            return true;
        }
    }

    public void insert(AnyType x) {
        //root = insertRecursive(x, root);\
        insertNonRecursive(x);
    }


    /**
     * 递归插入节点
     * @param x the element of the node
     * @param t current node
     * @return the next node
     */
    private BinaryNode<AnyType> insertRecursive(AnyType x, BinaryNode<AnyType> t) {
        if (t == null) {
            return new BinaryNode<AnyType>(x, null, null);
        }
        int compareResult = myCompare(x, t.element);
        if (compareResult < 0) {
            t.left = insertRecursive(x, t.left);
        } else if (compareResult > 0) {
            t.right = insertRecursive(x, t.right);
        } else {
            ;   //Duplicate ; do nothing
        }
        return t;
    }

    /**
     * 非递归插入节点
     *
     * @param x 插入的数值
     * @return 是否正确插入节点
     */
    private boolean insertNonRecursive(AnyType x) {
        BinaryNode<AnyType> last, node;
        last = null;
        node = root;
        if (node == null) {
            root = new BinaryNode<AnyType>(x, null, null);
            return true;
        }
        while (node != null) {
            last = node;
            if (myCompare(node.element, x) > 0) {
                node = node.left;
            } else if (myCompare(node.element, x) < 0) {
                node = node.right;
            }
        }
        node = new BinaryNode<AnyType>(x, null, null);
        if (myCompare(last.element, x) > 0) {
            last.left = node;
        } else {
            last.right = node;
        }
        return true;

    }

    public void remove(AnyType x) {
        root = remove(x, root);
    }

    private BinaryNode<AnyType> remove(AnyType x, BinaryNode<AnyType> t) {
        if (t == null) {
            return null;
        }
        int compareResult = myCompare(x, t.element);
        if (compareResult < 0) {
            remove(x, t.left);
        } else if (compareResult > 0) {
            remove(x, t.right);
        } else if (t.right != null && t.left != null) {
            t.element = findMin(t.right).element;
            t.right = remove(x, t.right);
        } else {
            t = (t.left != null) ? t.left : t.right;
        }
        return t;
    }

    public AnyType findMin() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException();
        }
        return findMin(root).element;
    }

    public AnyType findMax() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException();
        }
        return findMax(root).element;
    }

    /**
     * Internal method to find the smallest item in a subtree
     *
     * @param t the node that roots the subtree
     * @return node containing the smallest item
     */
    private BinaryNode<AnyType> findMin(BinaryNode<AnyType> t) {
        if (t == null) {
            return null;
        } else if (t.left == null) {
            return t;
        }
        return findMin(t.left);
    }

    /**
     * Internal method to find the largest item in a subtree
     *
     * @param t hte node that roots the subtree
     * @return node containing the largest item
     */

    private BinaryNode<AnyType> findMax(BinaryNode<AnyType> t) {
        if (t != null) {
            while (t.right != null) {
                t = t.right;
            }
        }
        return t;
    }

    /**
     * 递归先序遍历
     */
    public void preOrderRecursive() {
        if (isEmpty()) {
            System.out.println("Empty tree");
        } else {
            preOrderRecursive(root);
        }
    }

    private void preOrderRecursive(BinaryNode<AnyType> t) {
        if (t != null) {
            System.out.print(t.element + "\t");
            preOrderRecursive(t.left);
            preOrderRecursive(t.right);
        }
    }

    /**
     * 递归中序遍历
     */
    public void inOrderRecursive() {
        if (isEmpty()) {
            System.out.println("Empty tree");
        } else {
            inOrderRecursive(root);
        }
    }

    private void inOrderRecursive(BinaryNode<AnyType> t) {
        if (t != null) {
            inOrderRecursive(t.left);
            System.out.print(t.element + "\t");
            inOrderRecursive(t.right);
        }
    }

    /**
     *  非递归先序遍历
     * @return ArrayList of the result
     */
    public ArrayList<AnyType> preOrderNonRecursive() {
        ArrayList<AnyType> result = new ArrayList<>();
        Stack<BinaryNode<AnyType>> stack = new Stack<BinaryNode<AnyType>>();

        BinaryNode<AnyType> node = root;


        while (node != null || !stack.empty()) {

            while (node != null) {
                result.add(node.element);
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            node = node.right;
        }
        return result;
    }
    /**
     *  非递归中序遍历
     * @return ArrayList of the result
     */
    public ArrayList<AnyType> inOrderNonRecursive() {
        ArrayList<AnyType> result = new ArrayList<>();
        Stack<BinaryNode<AnyType>> stack = new Stack<BinaryNode<AnyType>>();

        BinaryNode<AnyType> node = root;

        while (node != null || !stack.empty()) {
            while (node != null) {
                stack.add(node);
                node = node.left;
            }
            node = stack.pop();
            result.add(node.element);
            node = node.right;

        }
        return result;
    }
    /**
     * 1. The maximum depth of binary tree
     *
     * @return the depth
     */
    public int maxDepthOfTree() {
        return maxDepthOfTree(root);
    }

    private int maxDepthOfTree(BinaryNode<AnyType> node) {
        if (node == null) {
            return 0;
        }
        return Math.max(maxDepthOfTree(node.left), maxDepthOfTree(node.right)) + 1;
    }

    /****** 这个需要多理解
     * 2.The minimum depth of binary tree
     *
     * @return the depth
     */
    public int minDepthOfTree() {
        return minDepthOfTree(root);
    }

    private int minDepthOfTree(BinaryNode<AnyType> node) {
        if (node == null) {
            return 0;
        }
        if (node.left == null && node.right == null) {
            return 1;
        }
        int m = minDepthOfTree(node.left) + 1;
        int n = minDepthOfTree(node.right) + 1;
        m = (m == 1 ? Integer.MAX_VALUE : m);
        n = (n == 1 ? Integer.MAX_VALUE : n);
        return m < n ? m : n;
    }

    /**
     * 3. the number of tree node
     *
     * @return the number
     */
    public int numberOfTreeNode() {
        return numberOfTreeNode(root);
    }

    private int numberOfTreeNode(BinaryNode<AnyType> node) {
        if (node == null) {
            return 0;
        }
        return numberOfTreeNode(node.left) + numberOfLeafNode(node.right) + 1;
    }

    /**
     * 3. The number of leaf nodes in the two fork tree
     *
     * @return number
     */
    public int numberOfLeafNode() {
        return numberOfLeafNode(root);
    }

    private int numberOfLeafNode(BinaryNode<AnyType> node) {
        if (node == null) {
            return 0;
        }
        if (node.left == null && node.right == null) {
            return 1;
        }
        return numberOfLeafNode(node.left) + numberOfLeafNode(node.right);
    }

    /**
     * 5
     *
     * @param k the level of tree
     * @return the num of k level treeNode;
     */
    public int numberOfKLevelTreeNode(int k) {
        return numberOfKLevelTreeNode(root, k);
    }

    private int numberOfKLevelTreeNode(BinaryNode<AnyType> node, int k) {
        if (node == null || k < 1) {
            return 0;
        }
        if (k == 1) {
            return 1;
        }
        return numberOfKLevelTreeNode(node.left, k - 1) + numberOfKLevelTreeNode(node.right, k - 1);

    }

    /**
     * 6
     *
     * @param null
     * @return if the tree is a AVL tree
     */
    public boolean isBalance() {
        return maxDepth(root) != -1;
    }

    private int maxDepth(BinaryNode<AnyType> node) {
        if (node == null) {
            return 0;
        }
        int leftDepth = maxDepth(node.left);
        int rightDepth = maxDepth(node.right);
        if (leftDepth == -1 || rightDepth == -1 || Math.abs(leftDepth - rightDepth) > 1) {
            return -1;
        }
        return Math.max(leftDepth, rightDepth) + 1;
    }

    /**
     * 7.
     *
     * @return isCompleteTree true or false
     */
    public boolean isCompleteTree() {
        return isCompleteTree(root);
    }

    private boolean isCompleteTree(BinaryNode<AnyType> node) {
        if (node == null) {
            return false;
        }

        Queue<BinaryNode<AnyType>> queue = new LinkedList<>();
        queue.add(node);
        boolean result = true;
        boolean hasNoChild = false;
        while (!queue.isEmpty()) {
            BinaryNode<AnyType> current = queue.remove();
            if (hasNoChild) {
                if (current.left != null || current.right != null) {
                    return false;
                }

            } else {
                if (current.left != null && current.right != null) {
                    queue.add(current.left);
                    queue.add(current.right);
                } else if (current.left != null && current.right == null) {
                    queue.add(current.left);
                    hasNoChild = true;
                } else if (current.left == null && current.right != null) {
                    return false;
                } else {
                    hasNoChild = true;
                }
            }
        }
        return true;
    }

    /**
     * 8.
     *
     * @param t is another tree
     * @return whether tree is same with t
     */
    public boolean isSameTreeNode(BinarySearchTree<AnyType> t) {
        return isSameTreeNode(this.root, t.root);
    }

    private boolean isSameTreeNode(BinaryNode<AnyType> node1, BinaryNode<AnyType> node2) {
        if (node1 == null && node2 == null) {
            return true;
        } else if (node1 == null || node2 == null) {
            return false;
        }

        return node1.element == node2.element && isSameTreeNode(node1.left, node2.left) && isSameTreeNode(node1.right, node2.right);

    }

    /**
     * 9.
     *
     * @param t is another tree
     * @return Whether the two binary tree is a mirror image of each other two
     */

    public boolean isMirror(BinarySearchTree<AnyType> t) {
        return isMirror(this.root, t.root);
    }

    private boolean isMirror(BinaryNode<AnyType> node1, BinaryNode<AnyType> node2) {
        if (node1 == null && node2 == null) {
            return true;
        } else if (node1 == null || node2 == null) {
            return false;
        }

        return node1.element == node2.element && isSameTreeNode(node1.left, node2.right) && isSameTreeNode(node1.right, node2.left);
    }

    /**
     * 10. Mirror the tree
     */
    public void mirrorTree() {
        mirrorTree(root);
    }

    private BinaryNode<AnyType> mirrorTree(BinaryNode<AnyType> node) {
        if (node == null) {
            return null;
        }
        if (node.right == null && node.left == null) {
            return node;
        }
        BinaryNode<AnyType> left = mirrorTree(node.left);

        node.left = mirrorTree(node.right);
        node.right = left;
        return node;
    }

    /**
     * 11. 假设树中没有重复element的节点
     *
     * @param x the first node
     * @param y the second node
     * @return the last common parent of x and y
     */
    public BinaryNode<AnyType> getLastCommonParent(AnyType x, AnyType y) {
        return getLastCommonParent(root, x, y);
    }

    private BinaryNode<AnyType> getLastCommonParent(BinaryNode<AnyType> node, AnyType x, AnyType y) {
        if (node.element == x || node.element == y) {
            return node;
        }

        if (findNode(node.left, x)) {
            if (findNode(node.right, y)) {
                return node;
            } else {
                return getLastCommonParent(node.left, x, y);
            }
        } else {
            if (findNode(node.left, y)) {
                return node;
            } else {
                return getLastCommonParent(node.right, x, y);
            }
        }
    }

    private boolean findNode(BinaryNode<AnyType> node, AnyType t) {
        if (node == null || t == null) {
            return false;
        }

        return myCompare(node.element, t) == 0 || findNode(node.left, t) || findNode(node.right, t);

    }



}
