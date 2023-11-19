import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Your implementation of a BST.
 *
 * @author sarah suess
 * @version 1.0
 * @userid ssuess3
 * @GTID 903687080
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null");
        } else {
            size = 0;
            for (T each: data) {
                if (each ==  null) {
                    throw new IllegalArgumentException("Data can not be null");
                }
                add(each);
            }
        }

    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null");
        } else if (root == null) {
            BSTNode<T> newNode = new BSTNode<T>(data);
            root = newNode;
            size++;
        } else {
            addHelp(data, root);
        }

    }

    /**
     * Helper method for adding to the BST.
     *
     * @param data the data to add
     * @param node the current node being compared to
     */
    private void addHelp(T data, BSTNode<T> node) {
        BSTNode<T> newNode = new BSTNode<T>(data);
        if (data.compareTo(node.getData()) > 0) {
            if (node.getRight() == null) {
                node.setRight(newNode);
                size++;
            } else {
                addHelp(data, node.getRight());
            }
        } else if (data.compareTo(node.getData()) < 0) {
            if (node.getLeft() == null) {
                node.setLeft(newNode);
                size++;
            } else {
                addHelp(data, node.getLeft());
            }
        }
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null");
        } else {
            BSTNode<T> remove = new BSTNode<T>(null);
            root = removeHelp(data, root, remove);
            size--;
            return remove.getData();
        }

    }

    /**
     * Helper method for remove().
     *
     * @param data the data to remove
     * @param node the node currently being looked at.
     * @param remove the node to be removed
     * @return the node to replace the "lost" spot
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    private BSTNode<T> removeHelp(T data, BSTNode<T> node, BSTNode<T> remove) {
        if (node == null) {
            throw new NoSuchElementException("Data is not in the tree");
        } else {
            if (data.equals(node.getData())) {
                remove.setData(node.getData());
                if (node.getLeft() == null && node.getRight() == null) {
                    node = null;
                } else if (node.getLeft() == null) {
                    return node.getRight();
                } else if (node.getRight() == null) {
                    return node.getLeft();
                } else {
                    BSTNode<T> removal = new BSTNode<T>(null);
                    node.setRight(getSuccessor(node.getRight(), removal));
                    node.setData(removal.getData());
                }
            } else if (data.compareTo(node.getData()) > 0) {
                node.setRight(removeHelp(data, node.getRight(), remove));
            } else if (data.compareTo(node.getData()) < 0) {
                node.setLeft(removeHelp(data, node.getLeft(), remove));
            }
            return node;
        }

    }

    /**
     * Finds Successor node for the remove method.
     *
     * @param node the node currently being looked at.
     * @param removal the node to be removed
     * @return the successor node to node
     */
    private BSTNode<T> getSuccessor(BSTNode<T> node, BSTNode<T> removal) {
        if (node.getLeft() != null) {
            node.setLeft(getSuccessor(node.getLeft(), removal));
            return node;
        } else {
            removal.setData(node.getData());
            return node.getRight();
        }
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null");
        } else {
            return getHelp(data, root);
        }

    }

    /**
     * Helper method for get().
     *
     * @param data the data to search for
     * @param node the node currently being looked at
     * @return the data in the tree equal to the parameter
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    private T getHelp(T data, BSTNode<T> node) {
        if (node == null) {
            throw new NoSuchElementException("The data is not in the tree");
        } else {
            if (data.equals(node.getData())) {
                return node.getData();
            } else if (data.compareTo(node.getData()) > 0) {
                return getHelp(data, node.getRight());
            } else {
                return getHelp(data, node.getLeft());
            }
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null");
        } else {
            return containsHelp(data, root);
        }

    }

    /**
     * Helper method for contains().
     *
     * @param data the data to search for
     * @param node the current node being looked at
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    private boolean containsHelp(T data, BSTNode<T> node) {
        if (node == null) {
            return false;
        } else {
            if (data.equals(node.getData())) {
                return true;
            } else if (data.compareTo(node.getData()) > 0) {
                return containsHelp(data, node.getRight());
            } else {
                return containsHelp(data, node.getLeft());
            }
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> preList = new ArrayList<>();
        return preorderHelp(preList, root);

    }

    /**
     * Helper method for preorder().
     *
     * @param list traversal list
     * @param node node currently being looked at
     * @return the preorder traversal of the tree
     */
    private List<T> preorderHelp(List<T> list, BSTNode<T> node) {
        if (node != null) {
            list.add(node.getData());
            list = preorderHelp(list, node.getLeft());
            list = preorderHelp(list, node.getRight());
        }
        return list;
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> inList = new ArrayList<>();
        return inorderHelp(inList, root);
    }

    /**
     * Helper method for inorder().
     *
     * @param list traversal list
     * @param node node currently being looked at
     * @return the inorder traversal of the tree
     */
    private List<T> inorderHelp(List<T> list, BSTNode<T> node) {
        if (node != null) {
            list = inorderHelp(list, node.getLeft());
            list.add(node.getData());
            list = inorderHelp(list, node.getRight());
        }
        return list;
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> postList = new ArrayList<>();
        return postorderHelp(postList, root);
    }

    /**
     * Helper method for postorder().
     *
     * @param list traversal list
     * @param node node currently being looked at
     * @return the postorder traversal of the tree
     */
    private List<T> postorderHelp(List<T> list, BSTNode<T> node) {
        if (node != null) {
            list = postorderHelp(list, node.getLeft());
            list = postorderHelp(list, node.getRight());
            list.add(node.getData());
        }
        return list;
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> queue = new LinkedList<>();
        List<T> levelList = new ArrayList<>();
        if (root == null) {
            return levelList;
        }
        queue.add(root);
        while (!queue.isEmpty()) {
            levelList.add(queue.peek().getData());
            BSTNode<T> leftNode = queue.peek().getLeft();
            if (leftNode != null) {
                queue.add(leftNode);
            }
            BSTNode<T> rightNode = queue.peek().getRight();
            if (rightNode != null) {
                queue.add(rightNode);
            }
            queue.remove();
        }
        return levelList;

    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelp(root);
    }

    /**
     * Helper method for height().
     *
     * @param node the current node being looked at
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    private int heightHelp(BSTNode<T> node) {
        if (node == null) {
            return -1;
        } else {
            return Math.max(heightHelp(node.getLeft()), heightHelp(node.getRight())) + 1;
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;

    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k), with n being the number of data in the BST
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > size
     */
    public List<T> kLargest(int k) {
        if (k < 0 || k > size) {
            throw new IllegalArgumentException("k can not be less than 0 or greater than the size");
        }
        List<T> list = new ArrayList<>(k);
        if (size == 0) {
            return list;
        }
        return kLargestHelp(root, list, k);
    }

    /**
     * Helper method for kLargest();
     *
     * @param node the current node being looked at
     * @param list list consisting of the k largest elements
     * @param k number of elements to add to list
     * @return the root of the tree
     */
    private List<T> kLargestHelp(BSTNode<T> node, List<T> list, int k) {
        if (node == null) {
            return list;
        } else {
            kLargestHelp(node.getRight(), list, k);
            if (list.size() == k) {
                return list;
            } else {
                list.add(0, node.getData());
                kLargestHelp(node.getLeft(), list, k);
            }
        }
        return list;
    }


    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
