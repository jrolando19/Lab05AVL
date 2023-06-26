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

    // Insertar
    public void insert(T x) throws ItemDuplicated {
        this.height = false; // Por si el nodo no se inserta
        this.root = insertRec(x, this.root);
    }

    private Node insertRec(T x, Node current) throws ItemDuplicated {
        Node res = current;
        if (current == null) {
            // En el caso base cuando hay inserción debemos consultar el cambio de altura
            this.height = true;
            res = new Node(x);
        } else {
            // resC == resultado de la comparación
            int resC = current.data.compareTo(x);
            if (resC == 0)
                throw new ItemDuplicated("El dato " + x + " ya fue insertado antes.");
            if (resC < 0) {
                res.right = insertRec(x, current.right);
                if (this.height) { // Si existe un cambio en la altura
                    switch (res.fb) {// Casos de fb si insertamos por la derecha
                        // En inserción si fb pasa de -1 o 1 a 0 entonces no hay cambio de altura
                        case -1:
                            res.fb = 0;
                            this.height = false;
                            break; // Se cancela el recálculo de altura
                        case 0:
                            res.fb = 1;
                            this.height = true;
                            break; // Seguimos recalculando
                        case 1: // res.fb = 2; debemos balancear el árbol
                            res = balanceToLeft(res);
                            this.height = false; // Cuando hacemos la rotación el árbol debe quedar balanceado
                    }
                }
            } else {
                res.left = insertRec(x, current.left);
                if (this.height) { // Si existe un cambio en la altura
                    switch (res.fb) { // Casos de fb si insertamos por la derecha
                        // En inserción si fb pasa de -1 o 1 a 0 entonces no hay cambio de altura
                        case 1:
                            res.fb = 0;
                            this.height = false;
                            break;// Se cancela el rec�lculo de altura
                        case 0:
                            res.fb = -1;
                            this.height = true;
                            break; // Seguimos recalculando
                        case -1: // res.fb = -2; debemos balancear el árbol
                            res = balanceToRight(res);
                            this.height = false; // Cuando hacemos la rotación el árbol debe quedar balanceado
                    }
                }
            }
        }
        return res;
    }

    // Para balancear a la izquierda (árbol desbalanceado a la derecha)
    private Node balanceToLeft(Node node) {
        Node son = node.right; // Para balancear a la izquierda debemos trabajar con el hijo derecho
        switch (son.fb) {
            case 1: // Rotación simple (Hijo y padre tienden a la derecha)
                // Como serán rotados el fb será 0
                node.fb = 0;
                son.fb = 0;
                node = rotateSL(node);
                break;
            case -1: // Rotación doble (Padre desbalanceado a la derecha, hijo a la izquierda)
                Node grandson = son.left;
                switch (grandson.fb) { // Actualizamos los fb
                    case -1:
                        node.fb = 0;
                        son.fb = -1;
                        break;
                    case 0:
                        node.fb = 0;
                        son.fb = 0;
                        break;
                    case 1:
                        node.fb = 1;
                        son.fb = 0;
                        break;
                }
                grandson.fb = 0; // El nieto queda balanceado
                // Hacemos las rotaciones
                node.right = rotateSR(son);
                node = rotateSL(node);
                break;
        }
        return node;
    }

    // Para balancear a la derecha (árbol desbalanceado a la izquierda)
    private Node balanceToRight(Node node) {
        Node son = node.left; // Para balancear a la derecha debemos trabajar con el hijo izquierdo
        switch (son.fb) {
            case -1: // Rotación simple (Hijo y padre tienden a la izquierda)
                // Como serán rotados el fb será 0
                node.fb = 0;
                son.fb = 0;
                node = rotateSR(node);
                break;
            case 1: // Rotación doble (Padre desbalanceado a la izquierda, hijo a la derecha)
                Node grandson = son.right;
                switch (grandson.fb) { // Actualizamos los fb
                    case 1:
                        node.fb = 0;
                        son.fb = -1;
                        break;
                    case 0:
                        node.fb = 0;
                        son.fb = 0;
                        break;
                    case -1:
                        node.fb = 1;
                        son.fb = 0;
                        break;

                }
                grandson.fb = 0; // El nieto queda balanceado
                // Hacemos las rotaciones
                node.left = rotateSL(son);
                node = rotateSR(node);
                break;
        }
        return node;
    }

    private Node rotateSL(Node node) {
        Node son = node.right;
        node.right = son.left;
        son.left = node;
        node = son;
        return node;
    }

    private Node rotateSR(Node node) {
        Node son = node.left;
        node.left = son.right;
        son.right = node;
        node = son;
        return node;
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