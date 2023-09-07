package punto9;

public class Punto_9_AVLTree {
    Punto_9 root;

    // Función para obtener la altura de un nodo
    int height(Punto_9 N) {
        if (N == null)
            return 0;
        return N.height;
    }

    // Función para obtener el balance del nodo
    int getBalance(Punto_9 N) {
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }

    // Función para rotar a la derecha un subárbol
    Punto_9 rightRotate(Punto_9 y) {
        Punto_9 x = y.left;
        Punto_9 T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // Función para rotar a la izquierda un subárbol
    Punto_9 leftRotate(Punto_9 x) {
        Punto_9 y = x.right;
        Punto_9 T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    // Función para insertar un nodo en el árbol AVL
    Punto_9 insert(Punto_9 node, int key) {
        if (node == null)
            return (new Punto_9(key));

        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else
            return node; // No se permiten duplicados

        // Actualizar la altura de este nodo
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Obtener el balance del nodo para verificar si se desequilibra
        int balance = getBalance(node);

        // Casos de desequilibrio

        // Caso Izquierda-Izquierda
        if (balance > 1 && key < node.left.key)
            return rightRotate(node);

        // Caso Derecha-Derecha
        if (balance < -1 && key > node.right.key)
            return leftRotate(node);

        // Caso Izquierda-Derecha
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Caso Derecha-Izquierda
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // Función para insertar un nodo público
    public void insert(int key) {
        root = insert(root, key);
    }

    // Función para realizar un recorrido inorder en el árbol AVL
    void inorder(Punto_9 node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.key + " ");
            inorder(node.right);
        }
    }

    public static void main(String[] args) {
        Punto_9_AVLTree tree = new Punto_9_AVLTree();

        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(40);
        tree.insert(50);
        tree.insert(25);

        System.out.println("Recorrido inorder del árbol AVL:");
        tree.inorder(tree.root);
    }
}
