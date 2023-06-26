<div align="center">
<table>
    <theader>
        <tr>
            <td><img src="https://github.com/rescobedoq/pw2/blob/main/epis.png?raw=true" alt="EPIS" style="width:50%; height:auto"/></td>
            <th>
                <span style="font-weight:bold;">UNIVERSIDAD NACIONAL DE SAN AGUSTIN</span><br />
                <span style="font-weight:bold;">FACULTAD DE INGENIERÍA DE PRODUCCIÓN Y SERVICIOS</span><br />
                <span style="font-weight:bold;">DEPARTAMENTO ACADÉMICO DE INGENIERÍA DE SISTEMAS E INFORMÁTICA</span><br />
                <span style="font-weight:bold;">ESCUELA PROFESIONAL DE INGENIERÍA DE SISTEMAS</span>
            </th>
            <td><img src="https://github.com/rescobedoq/pw2/blob/main/abet.png?raw=true" alt="ABET" style="width:50%; height:auto"/></td>
        </tr>
    </theader>
    <tbody>
        <tr><td colspan="3"><span style="font-weight:bold;">Formato</span>: Guía de Práctica de Laboratorio</td></tr>
        <tr><td><span style="font-weight:bold;">Aprobación</span>:  2023/06/23</td><td><span style="font-weight:bold;">Código</span>: GUIA-PRLD-001</td><td><span style="font-weight:bold;">Página</span>: 1</td></tr>
    </tbody>
</table>
</div>

<div align="center">
<span style="font-weight:bold;">INFORME DE LABORATORIO</span><br />
</div>

<table>
<theader>
<tr><th colspan="6">INFORMACIÓN BÁSICA</th></tr>
</theader>
<tbody>
<tr><td>ASIGNATURA:</td><td colspan="5">Estructura de Datos y Algoritmos</td></tr>
<tr><td>TÍTULO DE LA PRÁCTICA:</td><td colspan="5">Árbol AVL</td></tr>
<tr>
<td>NÚMERO DE PRÁCTICA:</td><td>05</td><td>AÑO LECTIVO:</td><td>2023 A</td><td>NRO. SEMESTRE:</td><td>III</td>
</tr>
<tr>
<td>FECHA INICIO::</td><td>23-Jun-2023</td><td>FECHA FIN:</td><td>25-Jun-2023</td><td>DURACIÓN:</td><td>02 horas</td>
</tr>
<tr><td colspan="6">Integrante:
    <ul>
        <li>Tejada Lazo Jordy Rolando</li>
</td>
</<tr>
<tr><td colspan="6">DOCENTES:
<ul>
<li>Dra. Karim Guevara Puente de la Vega

Mg. Richart Smith Escobedo Quispe

Mg. Edith Giovanna Cano Mamani

Mg. Jeymi Valdivia Eguiluz

</li>
</ul>
</td>
</<tr>
</tdbody>
</table>

# Árboles

[![License][license]][license-file]
[![Downloads][downloads]][releases]
[![Last Commit][last-commit]][releases]

[![Debian][Debian]][debian-site]
[![Git][Git]][git-site]
[![GitHub][GitHub]][github-site]
[![Vim][Vim]][vim-site]
[![Java][Java]][java-site]

#

## OBJETIVOS TEMAS Y COMPETENCIAS

### OBJETIVOS

- Estudiar arboles AVL.

### TEMAS

- Definiciones de árboles AVL.
- Operaciones con árboles AVL.

<details>
<summary>COMPETENCIAS</summary>

- C.m. Construye responsablemente soluciones haciendo uso de estructuras de datos y algoritmos, siguiendo un proceso adecuado para resolver problemas computacionales que se ajustan al uso de los recursos disponibles y a especificaciones concretas.

</details>

## EJERCICIOS PROPUESTOS

# - Implementación de Árbol AVL

- Elabore un informe implementando A´
  rboles AVL con toda la lista de operaciones search(),
  getMin(), getMax(), parent(), son(), insert(), remove().
- INPUT: Una s´ola palabra en mayu´sculas.
- OUTPUT: Se debe contruir el ´arbol AVL considerando el valor decimal de su c´odigo ascii.
- Luego, pruebe todas sus operaciones implementadas.
- Estudie la librer´ıa Graph Stream para obtener una salida gr´afica de su implementaci´on.
- Utilice todas las recomendaciones dadas por el docente.

# Resolución:

INFORME
Código y Explicación en Comentarios en el mismo Código:

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

    // MÉTODOS DEL ÁRBOL
    public void insert(char data) {
        root = insertRec(root, data);
    }
    // Inserta una palabra en el árbol AVL

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
        // Caso de rotación izquierda-izquierda
        if (balance > 1 && data < node.left.data) {
            return rotateRight(node);
        }
        // Caso de rotación derecha-derecha
        if (balance < -1 && data > node.right.data) {
            return rotateLeft(node);
        }
        // Rotación doble
        if (balance > 1 && data > node.left.data) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        // Rotación doble
        if (balance < -1 && data < node.right.data) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    // Busca una palabra en el árbol AVL

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
    // Devuelve la letra mínima en el árbol AVL

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
    // Devuelve la letra máxima en el árbol AVL

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

    // Devuelve el padre de una palabra en el árbol AVL

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

    // Devuelve el hijo o hijos de una letra en el árbol AVL

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

    // Elimina una letra del árbol AVL

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

       // Caso de rotación izquierda-izquierda

        if (balance > 1 && getBalance(node.left) >= 0) {
            return rotateRight(node);
        }
        // Caso de rotación doble
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        // Caso de rotación derecha-derecha
        if (balance < -1 && getBalance(node.right) <= 0) {
            return rotateLeft(node);
        }
        // Caso de rotación doble
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }
    // Realiza una rotación a la derecha sobre el nodo dado

    private Node rotateRight(Node node) {
        Node leftChild = node.left;
        Node leftRightChild = leftChild.right;

        leftChild.right = node;
        node.left = leftRightChild;

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        leftChild.height = 1 + Math.max(getHeight(leftChild.left), getHeight(leftChild.right));

        return leftChild;
    }
    // Realiza una rotación a la izquierda sobre el nodo dado

    private Node rotateLeft(Node node) {
        Node rightChild = node.right;
        Node rightLeftChild = rightChild.left;

        rightChild.left = node;
        node.right = rightLeftChild;

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        rightChild.height = 1 + Math.max(getHeight(rightChild.left), getHeight(rightChild.right));

        return rightChild;
    }

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

Clase para testear:

public class TestAvl {

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

        // Insertar caracteres en el árbol
        String word = "JORDY";
        for (char c : word.toCharArray()) {
            tree.insert(c);
        }

        // Realizar pruebas de las operaciones
        System.out.println("Árbol AVL construido:");
        tree.printTree();

        char searchChar = 'J';
        System.out.println("\nBúsqueda de '" + searchChar + "': " + tree.search(searchChar));

        char minChar = tree.getMin();
        System.out.println("Mínimo elemento: " + minChar);

        char maxChar = tree.getMax();
        System.out.println("Máximo elemento: " + maxChar);

        char parentChar = 'O';
        System.out.println("Padre de '" + parentChar + "': " + tree.parent(parentChar));

        char sonsChar = 'D';
        System.out.println("Hijos de '" + sonsChar + "': " + tree.sons(sonsChar));

        char removeChar = 'Y';
        tree.remove(removeChar);
        System.out.println("\nÁrbol AVL después de eliminar '" + removeChar + "':");
        tree.printTree();
    }

}

Explicación:

Se implementa la clase ÁrbolAVL donde está la clase node con atributos de char para las letras del árbol formado por una palabra, los nodos left, rigth, y la altura, luego se implementa los métodos básicos para la inserción (INSERT), eliminación(REMOVE), la busqueda de un carácter (SEARCH), obtener el mínimo (getMin), obtener el máximo(getMax), obtener el padre del nodo (parent) y el/los hijo/hijos (sons), las rotaciones consideradas a partir de insertar o eliminar un nodo para el balanceo del árbol avl, así como las rotaciones dobles, y por último imprimir el árbol en consola, con el testeo de los métodos anteriormente explicados.

Consola:

<img src="https://github.com/jrolando19/Lab05AVL/blob/main/consola.png"/>
	
Explicación: Se muestra el árbol construido, y se hace la busqueda de “J” que retorna true osea que existe en el árbol, el mínimo elemento es D con el método getmin, y el máximo es Y con el método getMax, padre de “O” ya que es el padre global del árbol no imprime nada, y hijos de D de igual manera, y también se muestra la eliminación de Y.

Versión con Graphstream:

Código:

    Clase para el testeo:

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

public class TestAvl2 {

    public static void main(String[] args) {
        AVLTree2 tree = new AVLTree2();

        // Insertar caracteres en el árbol
        String word = "JORDY";
        for (char c : word.toCharArray()) {
            tree.insert(c);
        }

        // Realizar pruebas de las operaciones
        System.out.println("Árbol AVL construido:");
        // Crear el grafo
        Graph graph = new SingleGraph("AVL Tree");

        // Agregar los nodos al grafo
        addNodesToGraph(graph, tree.root);

        // Agregar las aristas al grafo
        addEdgesToGraph(graph, tree.root);

        // Visualizar el grafo
        Viewer viewer = graph.display();

        char searchChar = 'J';
        System.out.println("\nBúsqueda de '" + searchChar + "': " + tree.search(searchChar));

        char minChar = tree.getMin();
        System.out.println("Mínimo elemento: " + minChar);

        char maxChar = tree.getMax();
        System.out.println("Máximo elemento: " + maxChar);

        char parentChar = 'O';
        System.out.println("Padre de '" + parentChar + "': " + tree.parent(parentChar));

        char sonsChar = 'D';
        System.out.println("Hijos de '" + sonsChar + "': " + tree.sons(sonsChar));

        char removeChar = 'Y';
        tree.remove(removeChar);
        System.out.println("\nÁrbol AVL después de eliminar '" + removeChar + "':");
        // Esperar hasta que se cierre la ventana de visualización
        while (!viewer.isCloseRequested()) {
            viewer.sleep(100);
        }
    }

    private static void addNodesToGraph(Graph graph, Node node) {
        if (node != null) {
            // Agregar el nodo actual al grafo
            org.graphstream.graph.Node graphNode = graph.addNode(String.valueOf(node.data));
            graphNode.addAttribute("ui.label", String.valueOf(node.data));

            // Recursivamente agregar los nodos de los subárboles izquierdo y derecho
            addNodesToGraph(graph, node.left);
            addNodesToGraph(graph, node.right);
        }
    }

    private static void addEdgesToGraph(Graph graph, Node node) {
        if (node != null) {
            // Agregar las aristas entre el nodo actual y sus hijos
            if (node.left != null) {
                graph.addEdge(String.valueOf(node.data) + "-" + String.valueOf(node.left.data),
                        String.valueOf(node.data), String.valueOf(node.left.data), true);
            }
            if (node.right != null) {
                graph.addEdge(String.valueOf(node.data) + "-" + String.valueOf(node.right.data),
                        String.valueOf(node.data), String.valueOf(node.right.data), true);
            }

            // Recursivamente agregar las aristas de los subárboles izquierdo y derecho
            addEdgesToGraph(graph, node.left);
            addEdgesToGraph(graph, node.right);
        }
    }

}

Clase ArbolAVL:
public class AVLTree2 {

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

    public AVLTree2() {
        this.root = null;
    }

    // MÉTODOS DEL ÁRBOL
    public void insert(char data) {
        root = insertRec(root, data);
    }
    // Inserta una palabra en el árbol AVL

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
        // Caso de rotación izquierda-izquierda
        if (balance > 1 && data < node.left.data) {
            return rotateRight(node);
        }
        // Caso de rotación derecha-derecha
        if (balance < -1 && data > node.right.data) {
            return rotateLeft(node);
        }
        // Rotación doble
        if (balance > 1 && data > node.left.data) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        // Rotación doble
        if (balance < -1 && data < node.right.data) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    // Busca una palabra en el árbol AVL

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
    // Devuelve la letra mínima en el árbol AVL

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
    // Devuelve la letra máxima en el árbol AVL

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

    // Devuelve el padre de una palabra en el árbol AVL

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

    // Devuelve el hijo o hijos de una letra en el árbol AVL

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

    // Elimina una letra del árbol AVL

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
        // Caso de rotación izquierda-izquierda

        if (balance > 1 && getBalance(node.left) >= 0) {
            return rotateRight(node);
        }
        // Caso de rotación doble
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        // Caso de rotación derecha-derecha
        if (balance < -1 && getBalance(node.right) <= 0) {
            return rotateLeft(node);
        }
        // Caso de rotación doble
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }
    // Realiza una rotación a la derecha sobre el nodo dado

    private Node rotateRight(Node node) {
        Node leftChild = node.left;
        Node leftRightChild = leftChild.right;

        leftChild.right = node;
        node.left = leftRightChild;

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        leftChild.height = 1 + Math.max(getHeight(leftChild.left), getHeight(leftChild.right));

        return leftChild;
    }
    // Realiza una rotación a la izquierda sobre el nodo dado

    private Node rotateLeft(Node node) {
        Node rightChild = node.right;
        Node rightLeftChild = rightChild.left;

        rightChild.left = node;
        node.right = rightLeftChild;

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        rightChild.height = 1 + Math.max(getHeight(rightChild.left), getHeight(rightChild.right));

        return rightChild;
    }

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

## REFERENCIAS

    - https://www.w3schools.com/java/
    - https://www.eclipse.org/downloads/packages/release/2022-03/r/eclipse-ide-enterprise-java-and-web-developers
    -   https://algorithmtutor.com/Data-Structures/Tree/AVL-Trees/
    -   https://docs.oracle.com/javase/tutorial/java/generics/types.html

#

[license]: https://img.shields.io/github/license/rescobedoq/pw2?label=rescobedoq
[license-file]: https://github.com/rescobedoq/pw2/blob/main/LICENSE
[downloads]: https://img.shields.io/github/downloads/rescobedoq/pw2/total?label=Downloads
[releases]: https://github.com/rescobedoq/pw2/releases/
[last-commit]: https://img.shields.io/github/last-commit/rescobedoq/pw2?label=Last%20Commit
[Debian]: https://img.shields.io/badge/Debian-D70A53?style=for-the-badge&logo=debian&logoColor=white
[debian-site]: https://www.debian.org/index.es.html
[Git]: https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white
[git-site]: https://git-scm.com/
[GitHub]: https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white
[github-site]: https://github.com/
[Vim]: https://img.shields.io/badge/VIM-%2311AB00.svg?style=for-the-badge&logo=vim&logoColor=white
[vim-site]: https://www.vim.org/
[Java]: https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white
[java-site]: https://docs.oracle.com/javase/tutorial/

[![Debian][Debian]][debian-site]
[![Git][Git]][git-site]
[![GitHub][GitHub]][github-site]
[![Vim][Vim]][vim-site]
[![Java][Java]][java-site]

[![License][license]][license-file]
[![Downloads][downloads]][releases]
[![Last Commit][last-commit]][releases]
