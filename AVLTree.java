import java.util.NoSuchElementException;

public class AVLTree {

    class Node {
        protected char data;
        protected Node left;
        protected Node right;
        protected int height;

        public Node(char data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.height = 1;
        }
    }

    private Node root;

    public AVLTree() {
        this.root = null;
    }

    public void insert(char data) {
        root = insertRec(root, data);
    }

    private Node insertRec(Node node, char data) {
        if (node == null) {
            return new Node(data);
        }

        if (data < node.data) {
            node.left = insertRec(node.left, data);
        } else if (data > node.data) {
            node.right = insertRec(node.right, data);
        } else {
            // Duplicado, no se permiten elementos repetidos
            return node;
        }

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        int balance = getBalance(node);

        if (balance > 1 && data < node.left.data) {
            return rotateRight(node);
        }

        if (balance < -1 && data > node.right.data) {
            return rotateLeft(node);
        }

        if (balance > 1 && data > node.left.data) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        if (balance < -1 && data < node.right.data) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    public boolean search(char data) {
        return searchRec(root, data);
    }

    private boolean searchRec(Node node, char data) {
        if (node == null) {
            return false;
        }

        if (data == node.data) {
            return true;
        } else if (data < node.data) {
            return searchRec(node.left, data);
        } else {
            return searchRec(node.right, data);
        }
    }

    public char getMin() {
        if (root == null) {
            throw new NoSuchElementException("El árbol está vacío");
        }

        Node current = root;
        while (current.left != null) {
            current = current.left;
        }

        return current.data;
    }

    public char getMax() {
        if (root == null) {
            throw new NoSuchElementException("El árbol está vacío");
        }

        Node current = root;
        while (current.right != null) {
            current = current.right;
        }

        return current.data;
    }

    public char parent(char data) {
        if (root == null) {
            throw new NoSuchElementException("El árbol está vacío");
        }

        Node parent = null;
        Node current = root;

        while (current != null) {
            if (data == current.data) {
                break;
            } else if (data < current.data) {
                parent = current;
                current = current.left;
            } else {
                parent = current;
                current = current.right;
            }
        }

        if (current == null) {
            throw new NoSuchElementException("El nodo no existe en el árbol");
        }

        return parent != null ? parent.data : '\0';
    }

    public String sons(char data) {
        if (root == null) {
            throw new NoSuchElementException("El árbol está vacío");
        }

        Node current = searchNode(root, data);

        if (current == null) {
            throw new NoSuchElementException("El nodo no existe en el árbol");
        }

        StringBuilder result = new StringBuilder();
        if (current.left != null) {
            result.append(current.left.data);
        }
        if (current.right != null) {
            result.append(current.right.data);
        }

        return result.toString();
    }

    public void remove(char data) {
        root = removeRec(root, data);
    }

    private Node removeRec(Node node, char data) {
        if (node == null) {
            throw new NoSuchElementException("El nodo no existe en el árbol");
        }

        if (data < node.data) {
            node.left = removeRec(node.left, data);
        } else if (data > node.data) {
            node.right = removeRec(node.right, data);
        } else {
            if (node.left == null && node.right == null) {
                return null;
            } else if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                Node successor = getMinNode(node.right);
                node.data = successor.data;
                node.right = removeRec(node.right, successor.data);
            }
        }

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        int balance = getBalance(node);

        if (balance > 1 && getBalance(node.left) >= 0) {
            return rotateRight(node);
        }

        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        if (balance < -1 && getBalance(node.right) <= 0) {
            return rotateLeft(node);
        }

        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    private Node rotateRight(Node node) {
        Node leftChild = node.left;
        Node leftRightChild = leftChild.right;

        leftChild.right = node;
        node.left = leftRightChild;

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        leftChild.height = 1 + Math.max(getHeight(leftChild.left), getHeight(leftChild.right));

        return leftChild;
    }

    private Node rotateLeft(Node node) {
        Node rightChild = node.right;
        Node rightLeftChild = rightChild.left;

        rightChild.left = node;
        node.right = rightLeftChild;

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        rightChild.height = 1 + Math.max(getHeight(rightChild.left), getHeight(rightChild.right));

        return rightChild;
    }

    private int getHeight(Node node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    private int getBalance(Node node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    private Node getMinNode(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    private Node searchNode(Node node, char data) {
        if (node == null || node.data == data) {
            return node;
        }

        if (data < node.data) {
            return searchNode(node.left, data);
        } else {
            return searchNode(node.right, data);
        }
    }

    void printTree() {
        printTreeRec(root, 0);
    }

    private void printTreeRec(Node node, int level) {
        if (node != null) {
            printTreeRec(node.right, level + 1);
            for (int i = 0; i < level; i++) {
                System.out.print("\t");
            }
            System.out.println(node.data);
            printTreeRec(node.left, level + 1);
        }
    }
}