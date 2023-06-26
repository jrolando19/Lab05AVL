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