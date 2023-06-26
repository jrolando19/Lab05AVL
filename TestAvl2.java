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