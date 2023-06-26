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

    // BÚSQUEDA PARA DEVOLVER DATOS
    public T get(T x) throws ItemNotFound {
        Node res = searchNode(x, root);
        if (res == null)
            throw new ItemNotFound("El dato " + x + " no está ...");
        return res.data;
    }

    private Node searchNode(T x, Node n) {
        if (n == null)
            return null;
        else {
            int resC = n.data.compareTo(x);
            if (resC < 0)
                return searchNode(x, n.right);
            else if (resC > 0)
                return searchNode(x, n.left);
            else
                return n;
        }
    }

    public String toString() {
        if (isEmpty())
            return "Arbol Vacio...";
        String str = inOrden(this.root);
        return str;
    }

    private String inOrden(Node current) {
        // IRD
        String str = "";
        if (current.left != null)
            str += inOrden(current.left);
        str += current.data.toString() + "[" + current.fb + "], ";
        if (current.right != null)
            str += inOrden(current.right);
        return str;
    }

    // Mínimo
    public T getMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("El árbol está vacío");
        }
        Node minNode = getMinNode(root);
        return minNode.data;
    }

    private Node getMinNode(Node current) {
        if (current.left == null) {
            return current;
        }
        return getMinNode(current.left);
    }

    // Máximo
    public T getMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("El árbol está vacío");
        }
        Node maxNode = getMaxNode(root);
        return maxNode.data;
    }

    private Node getMaxNode(Node current) {
        if (current.right == null) {
            return current;
        }
        return getMaxNode(current.right);
    }
    // Padre

    public T parent(T x) throws ItemNotFound {
        if (isEmpty()) {
            throw new ItemNotFound("El árbol está vacío");
        }
        Node parentNode = getParentNode(x, root);
        if (parentNode == null) {
            throw new ItemNotFound("El elemento " + x + " no tiene padre en el árbol");
        }
        return parentNode.data;
    }

    private Node getParentNode(T x, Node current) {
        if (current == null || current.data.compareTo(x) == 0) {
            return null;
        }
        if ((current.left != null && current.left.data.compareTo(x) == 0) ||
                (current.right != null && current.right.data.compareTo(x) == 0)) {
            return current;
        }
        if (current.data.compareTo(x) > 0) {
            return getParentNode(x, current.left);
        } else {
            return getParentNode(x, current.right);
        }
    }

    // Hijo
    public T getChild(T x, boolean leftChild) throws ItemNotFound {
        Node parentNode = searchNode(x, root);
        if (parentNode == null) {
            throw new ItemNotFound("El elemento " + x + " no está en el árbol");
        }
        if (leftChild) {
            if (parentNode.left != null) {
                return parentNode.left.data;
            } else {
                throw new ItemNotFound("El elemento " + x + " no tiene hijo izquierdo");
            }
        } else {
            if (parentNode.right != null) {
                return parentNode.right.data;
            } else {
                throw new ItemNotFound("El elemento " + x + " no tiene hijo derecho");
            }
        }
    }

    // Eliminar
    public void remove(T x) throws ItemNotFound {
        this.root = removeRec(x, this.root);
    }

    private Node removeRec(T x, Node current) throws ItemNotFound {
        if (current == null) {
            throw new ItemNotFound("El dato " + x + " no se encuentra en el árbol.");
        }

        int comparison = x.compareTo(current.data);

        if (comparison < 0) {
            current.left = removeRec(x, current.left);
        } else if (comparison > 0) {
            current.right = removeRec(x, current.right);
        } else {
            // Se encontró el nodo a eliminar

            // Caso 1: El nodo a eliminar es una hoja (no tiene hijos)
            if (current.left == null && current.right == null) {
                return null;
            }
            // Caso 2: El nodo a eliminar tiene un hijo derecho
            else if (current.left == null) {
                return current.right;
            }
            // Caso 3: El nodo a eliminar tiene un hijo izquierdo
            else if (current.right == null) {
                return current.left;
            }
            // Caso 4: El nodo a eliminar tiene dos hijos
            else {
                Node successor = findMin(current.right);
                current.data = successor.data;
                current.right = removeRec(successor.data, current.right);
            }
        }

        return current;
    }

    private Node findMin(Node current) {
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }
}