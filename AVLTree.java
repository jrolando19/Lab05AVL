import java.util.NoSuchElementException;

public class AVLTree<T extends Comparable<T>> {

    private class Node {
        private String data;
        private int key;
        private Node left;
        private Node right;
        private int height;

        public Node(String data) {
            this.data = data;
            this.key = calculateKey(data);
            this.left = null;
            this.right = null;
            this.height = 1;
        }
    }

    private Node root;

    public AVLTree() {
        root = null;
    }

    // MÉTODOS DEL ÁRBOL

    // Calcula la altura de un nodo
    private int getHeight(Node node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    // Calcula la clave (valor decimal del código ASCII) de una palabra
    private int calculateKey(String word) {
        int key = 0;
        for (int i = 0; i < word.length(); i++) {
            key += (int) word.charAt(i);
        }
        return key;
    }

    // Calcula el factor de balance de un nodo
    private int getBalance(Node node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    // Para saber si el árbol está vacío
    public boolean isEmpty() {
        return this.root == null;
    }

    // Inserta una palabra en el árbol AVL
    public void insert(String word) {
        root = insertRec(root, word);
    }

    private Node insertRec(Node node, String word) {
        if (node == null) {
            return new Node(word);
        }

        int comparison = word.compareTo(node.data);
        if (comparison < 0) {
            node.left = insertRec(node.left, word);
        } else if (comparison > 0) {
            node.right = insertRec(node.right, word);
        } else {
            // La palabra ya existe en el árbol
            return node;
        }

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        int balance = getBalance(node);

        // Caso de rotación izquierda-izquierda
        if (balance > 1 && word.compareTo(node.left.data) < 0) {
            return rotateRight(node);
        }

        // Caso de rotación derecha-derecha
        if (balance < -1 && word.compareTo(node.right.data) > 0) {
            return rotateLeft(node);
        }

        // Caso de rotación izquierda-derecha
        if (balance > 1 && word.compareTo(node.left.data) > 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // Caso de rotación derecha-izquierda
        if (balance < -1 && word.compareTo(node.right.data) < 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    // Realiza una rotación a la derecha sobre el nodo dado
    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));
        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));

        return x;
    }

    // Realiza una rotación a la izquierda sobre el nodo dado
    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));
        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));

        return y;
    }

    // Busca una palabra en el árbol AVL
    public boolean search(String word) {
        return searchRec(root, word);
    }

    private boolean searchRec(Node node, String word) {
        if (node == null) {
            return false;
        }

        int comparison = word.compareTo(node.data);

        if (comparison < 0) {
            return searchRec(node.left, word);
        } else if (comparison > 0) {
            return searchRec(node.right, word);
        } else {
            return true;
        }
    }

    // Devuelve la palabra mínima en el árbol AVL
    public String getMin() {
        if (root == null) {
            throw new NoSuchElementException("El árbol está vacío");
        }
        Node minNode = findMin(root);
        return minNode.data;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // Devuelve la palabra máxima en el árbol AVL
    public String getMax() {
        if (root == null) {
            throw new NoSuchElementException("El árbol está vacío");
        }
        Node maxNode = findMax(root);
        return maxNode.data;
    }

    private Node findMax(Node node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    /*
     * // Padre
     * 
     * public T parent(T x) throws ItemNotFound {
     * if (isEmpty()) {
     * throw new ItemNotFound("El árbol está vacío");
     * }
     * Node parentNode = getParentNode(x, root);
     * if (parentNode == null) {
     * throw new ItemNotFound("El elemento " + x + " no tiene padre en el árbol");
     * }
     * return parentNode.data;
     * }
     * 
     * private Node getParentNode(T x, Node current) {
     * if (current == null || current.data.compareTo(x) == 0) {
     * return null;
     * }
     * if ((current.left != null && current.left.data.compareTo(x) == 0) ||
     * (current.right != null && current.right.data.compareTo(x) == 0)) {
     * return current;
     * }
     * if (current.data.compareTo(x) > 0) {
     * return getParentNode(x, current.left);
     * } else {
     * return getParentNode(x, current.right);
     * }
     * }
     * 
     * // Hijo
     * public T getChild(T x, boolean leftChild) throws ItemNotFound {
     * Node parentNode = searchNode(x, root);
     * if (parentNode == null) {
     * throw new ItemNotFound("El elemento " + x + " no está en el árbol");
     * }
     * if (leftChild) {
     * if (parentNode.left != null) {
     * return parentNode.left.data;
     * } else {
     * throw new ItemNotFound("El elemento " + x + " no tiene hijo izquierdo");
     * }
     * } else {
     * if (parentNode.right != null) {
     * return parentNode.right.data;
     * } else {
     * throw new ItemNotFound("El elemento " + x + " no tiene hijo derecho");
     * }
     * }
     * }
     */
    // Elimina una palabra del árbol AVL
    public void remove(String word) {
        root = removeRec(root, word);
    }

    private Node removeRec(Node node, String word) {
        if (node == null) {
            return null;
        }

        int comparison = word.compareTo(node.data);

        if (comparison < 0) {
            node.left = removeRec(node.left, word);
        } else if (comparison > 0) {
            node.right = removeRec(node.right, word);
        } else {
            // Se encontró el nodo a eliminar

            // Caso 1: El nodo a eliminar es una hoja (no tiene hijos)
            if (node.left == null && node.right == null) {
                return null;
            }
            // Caso 2: El nodo a eliminar tiene un hijo derecho
            else if (node.left == null) {
                return node.right;
            }
            // Caso 3: El nodo a eliminar tiene un hijo izquierdo
            else if (node.right == null) {
                return node.left;
            }
            // Caso 4: El nodo a eliminar tiene dos hijos
            else {
                Node successor = findMin(node.right);
                node.data = successor.data;
                node.right = removeRec(node.right, successor.data);
            }
        }

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        int balance = getBalance(node);

        // Caso de rotación izquierda-izquierda
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rotateRight(node);
        }

        // Caso de rotación derecha-derecha
        if (balance < -1 && getBalance(node.right) <= 0) {
            return rotateLeft(node);
        }

        // Caso de rotación izquierda-derecha
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // Caso de rotación derecha-izquierda
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }
}