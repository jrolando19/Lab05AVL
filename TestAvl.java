public class TestAvl {

     public static void main(String[] args) {
        AVLTree<String> tree = new AVLTree<String>();

        // Insertar palabras en el árbol AVL
        tree.insert("HELLO");
        tree.insert("WORLD");
        tree.insert("AVL");
        tree.insert("TREE");

        // Buscar palabras en el árbol AVL
        System.out.println(tree.search("HELLO"));  // true
        System.out.println(tree.search("WORLD"));  // true
        System.out.println(tree.search("AVL"));    // true
        System.out.println(tree.search("TREE"));   // true
        System.out.println(tree.search("TEST"));   // false

        // Obtener la palabra mínima
        System.out.println(tree.getMin());  // "AVL"

        // Obtener la palabra máxima
        System.out.println(tree.getMax());  // "WORLD"

        // Obtener el padre de una palabra
        System.out.println(tree.parent("HELLO"));  // "TREE"
        System.out.println(tree.parent("WORLD"));  // "TREE"
        System.out.println(tree.parent("AVL"));    // "HELLO"
        System.out.println(tree.parent("TREE"));   // "HELLO"

        // Obtener los hijos de una palabra
        String[] children = tree.son("HELLO");
        System.out.println("Left Child: " + children[0]);   // "AVL"
        System.out.println("Right Child: " + children[1]);  // "TREE"

        // Eliminar palabras del árbol AVL
        tree.remove("HELLO");
        tree.remove("WORLD");

        // Verificar que las palabras se hayan eliminado
        System.out.println(tree.search("HELLO"));  // false
        System.out.println(tree.search("WORLD"));  // false
    }
}
}