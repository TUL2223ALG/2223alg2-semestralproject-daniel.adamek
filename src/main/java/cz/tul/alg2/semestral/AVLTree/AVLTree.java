package cz.tul.alg2.semestral.AVLTree;

import cz.tul.alg2.semestral.Utilities.GenericNode;

/**
 * The type Avl tree.
 *
 * @param <T> the type parameter
 */
public class AVLTree<T extends Comparable<T>> {
    public static class Node<T> extends GenericNode<T> {
        private int height = 1;
        protected Node<T> leftNode = null;
        protected Node<T> rightNode = null;
        Node(T nodeValue) {
            super(nodeValue);
        }
        Node(T nodeValue, int height) {
            super(nodeValue);
            this.height = height;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public Node<T> getLeftNode() {
            return leftNode;
        }

        public void setLeftNode(Node<T> leftNode) {
            this.leftNode = leftNode;
        }

        public Node<T> getRightNode() {
            return rightNode;
        }

        public void setRightNode(Node<T> rightNode) {
            this.rightNode = rightNode;
        }
    }

    Node<T> root;

    /**
     * Gets height.
     *
     * @param node the node
     * @return the height
     */
    int getHeight(Node<T> node) { return (node == null) ? 0 : node.height; }

    /**
     * Gets balance.
     *
     * @param node the node
     * @return the balance
     */
    //int getBalance(Node<T> node) { return (node == null) ? 0 : getHeight(node.leftNode) - getHeight(node.rightNode); }
    int getBalance(Node<T> node) { return (node == null) ? 0 : node.getLeftNode().getHeight() - node.rightNode.getHeight(); }

    /**
     * Update height.
     *
     * @param x the x
     */
    void updateHeight(Node<T> x) { x.height = Math.max(getHeight(x.leftNode), getHeight(x.rightNode)) + 1; }

    /**
     * Right rotation node.
     *
     * @param y the y
     * @return the node
     */
    Node<T> rightRotation(Node<T> y) {
        Node<T> x = y.leftNode;
        Node<T> z = x.rightNode;

        // Rotation
        //x.rightNode = y;
        x.setRightNode(y);
        //y.leftNode = z;
        y.setLeftNode(z);

        // Update heights
        updateHeight(y);
        updateHeight(x);

        return x;
    }

    /**
     * Left rotation node.
     *
     * @param y the y
     * @return the node
     */
    Node<T> leftRotation(Node<T> y) {
        Node<T> x = y.rightNode;
        Node<T> z = x.leftNode;

        //Rotation
        //x.leftNode = y;
        x.setLeftNode(y);
        //y.rightNode = z;
        y.setRightNode(z);

        // Update heights
        updateHeight(y);
        updateHeight(x);

        return x;
    }

    /**
     * Insert.
     *
     * @param value the value
     */
    public void insert(T value) {
        Node<T> node = new Node<>(value);

        if (root == null) {
            root = node;
            return;
        }
        root = insertAt(root, node);
    }
    private Node<T> insertAt(Node<T> node, Node<T> x) {
        if (node == null) return x;

        if (x.getValue().compareTo(node.getValue()) < 0)
            node.leftNode = insertAt(node.leftNode, x);
        else if (x.getValue().compareTo(node.getValue()) > 0)
            node.rightNode = insertAt(node.rightNode, x);
        else
            return node;
        node.height = 1 + Math.max(getHeight(node.leftNode), getHeight(node.rightNode));

        int bal = getBalance(node);
        if (bal > 1) {
            if (x.getValue().compareTo(node.leftNode.getValue()) < 0) {
                return rightRotation(node);
            } else if (x.getValue().compareTo(node.leftNode.getValue()) > 1) {
                node.leftNode = leftRotation(node.leftNode);
                return rightRotation(node);
            }
        }
        if (bal < -1) {
            if (x.getValue().compareTo(node.rightNode.getValue()) > 0) {
                return leftRotation(node);
            } else if (x.getValue().compareTo(node.rightNode.getValue()) < 0) {
                node.rightNode = rightRotation(node.rightNode);
                return leftRotation(node);
            }
        }
        return node;
    }
    public Node<T> find(T value) {
        return findAt(root, value);
    }
    private Node<T> findAt(Node<T> node, T value) {
        if (node == null) return null;

        if (value.compareTo(node.getValue()) > 0) return findAt(node.rightNode, value);
        else if (value.compareTo(node.getValue()) < 0) return findAt(node.leftNode, value);
        else return node;
    }
    public boolean exists(T value) { return (find(value) != null); }
}
