public class TestAvl {

    public static void main(String[] args) {
        AVLTree<String> c = new AVLTree<String>();
        AVLTree<Integer> b = new AVLTree<Integer>();

        try {
            b.insert(50);
            b.insert(80);
            b.insert(30);
            b.insert(100);
            b.insert(15);
            c.insert("R");
            c.insert("I");
            c.insert("C");
            c.insert("H");
            c.insert("A");
            c.insert("R2");
            c.insert("T");
            System.out.println(b);
            System.out.println(c);
            System.out.println("\nUSANDO LA FUNCIÓN GET");
            System.out.println("¿Está el dato 50? " + b.get(50)); // Si aparece el número entonces está el dato
            // Mínimo
            System.out.println("Mínimo: " + b.getMin());
            // Máximo
            System.out.println("Máximo: " + b.getMax());
            // Parent
            System.out.println("Padre de 30: " + b.parent(30));
            // Son
            System.out.println("Hijo de 50:" + b.getChild(50, true));
            System.out.println("Borrar: 100");
            b.remove(100);
            System.out.println(b);
            // Cuando un elemento no está se lanza la excepción

            System.out.println("¿Está el dato 500? " + b.get(500)); // Si aparece el número entonces está el dato
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}