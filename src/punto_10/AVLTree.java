package punto_10;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Node {
    int data;
    Node left;
    Node right;
    int height;

    public Node(int data) {
        this.data = data;
        this.height = 1;
    }
}

public class AVLTree {
    private Node root;

    public AVLTree() {
        this.root = null;
    }

    // Inserta un nodo en el árbol AVL
    public void insert(int data) {
        root = insertRecursive(root, data);
    }

    // Elimina un nodo en el árbol AVL
    public void delete(int data) {
        root = deleteRecursive(root, data);
    }

    // Método auxiliar para calcular la altura de un nodo
    private int height(Node node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    // Método auxiliar para obtener el balance de un nodo
    private int getBalance(Node node) {
        if (node == null) {
            return 0;
        }
        return height(node.left) - height(node.right);
    }

    // Método para realizar una rotación simple a la derecha
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // Método para realizar una rotación simple a la izquierda
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }
    
    private Node insertRecursive(Node node, int data) {
        if (node == null) {
            return new Node(data);
        }

        if (data < node.data) {
            node.left = insertRecursive(node.left, data);
        } else if (data > node.data) {
            node.right = insertRecursive(node.right, data);
        } else {
            // Duplicados no están permitidos
            return node;
        }

        // Actualizar la altura del nodo actual
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Obtener el balance del nodo
        int balance = getBalance(node);

        // Realizar las rotaciones si es necesario
        // Rotación a la derecha (LL)
        if (balance > 1 && data < node.left.data) {
            return rightRotate(node);
        }

        // Rotación a la izquierda (RR)
        if (balance < -1 && data > node.right.data) {
            return leftRotate(node);
        }

        // Rotación a la izquierda-derecha (LR)
        if (balance > 1 && data > node.left.data) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Rotación a la derecha-izquierda (RL)
        if (balance < -1 && data < node.right.data) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private Node deleteRecursive(Node root, int data) {
        if (root == null) {
            return root;
        }

        if (data < root.data) {
            root.left = deleteRecursive(root.left, data);
        } else if (data > root.data) {
            root.right = deleteRecursive(root.right, data);
        } else {
            

            if (root.left == null || root.right == null) {
                Node temp = (root.left != null) ? root.left : root.right;

                if (temp == null) {
                    temp = root;
                    root = null;
                } else { 
                    root = temp;
                }
            } else { 
                Node temp = minValueNode(root.right);
                root.data = temp.data;
                root.right = deleteRecursive(root.right, temp.data);
            }
        }

        if (root == null) {
            return root;
        }

        root.height = 1 + Math.max(height(root.left), height(root.right));

        int balance = getBalance(root);

       
        if (balance > 1 && getBalance(root.left) >= 0) {
            return rightRotate(root);
        }

       
        if (balance < -1 && getBalance(root.right) <= 0) {
            return leftRotate(root);
        }

        
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    
    private Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

   
    public void inorderTraversal(Node node) {
        if (node != null) {
            inorderTraversal(node.left);
            System.out.print(node.data + " ");
            inorderTraversal(node.right);
        }
    }
    public void displayTree() {
        displayTree(root, "", true);
    }

    private void displayTree(Node node, String prefix, boolean isTail) {
        if (node != null) {
            System.out.println(prefix + (isTail ? "└── " : "├── ") + node.data);

            if (node.left != null || node.right != null) {
                List<Node> children = new ArrayList<>();
                children.add(node.left);
                children.add(node.right);

                for (int i = 0; i < children.size() - 1; i++) {
                    Node child = children.get(i);
                    if (child != null) {
                        displayTree(child, prefix + (isTail ? "    " : "│   "), false);
                    } else {
                        System.out.println(prefix + (isTail ? "    " : "│   ") + "│");
                    }
                }

                if (children.size() > 1) {
                    Node child = children.get(children.size() - 1);
                    if (child != null) {
                        displayTree(child, prefix + (isTail ? "    " : "│   "), true);
                    } else {
                        System.out.println(prefix + (isTail ? "    " : "│   ") + "│");
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Árbol AVL Vacío");

        while (true) {
            System.out.print("Ingrese un número para insertar (-1 para finalizar la inserción): ");
            int num = scanner.nextInt();
            if (num == -1) {
                break;
            }
            tree.insert(num);
        }

        System.out.println("Árbol AVL ordenado:");
        tree.displayTree();

        
        System.out.print("¿Desea eliminar un número? (S/N): ");
        scanner.nextLine(); 
        String response = scanner.nextLine().trim().toUpperCase();
        
        if (response.equals("S")) {
            System.out.print("Ingrese el número que desea eliminar: ");
            int numToDelete = scanner.nextInt();
            tree.delete(numToDelete);

            System.out.println("Árbol AVL actualizado:");
            tree.displayTree();
        }
    }
}
