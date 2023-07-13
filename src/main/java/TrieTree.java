import java.util.Scanner;


public class TrieTree {

    static class Node {
        char data;
        Node[] childrenOfNode = new Node[27];
        boolean isWord;
        int numberOfChildren;

        Node() {
            for (int i = 0; i < childrenOfNode.length; i++) {
                childrenOfNode[i] = null;
            }
            numberOfChildren = 0;
        }

        Node(char data) {
            for (int i = 0; i < childrenOfNode.length; i++) {
                childrenOfNode[i] = null;
            }
            this.data = data;
            isWord = false;
            numberOfChildren = 0;
        }
    }

    static int index;
    static Node currentNode;

    static void insertWord(String word, Node root) {
        currentNode = root;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == ' ') {
                index = 26;
                currentNode.childrenOfNode[26] = new Node(word.charAt(i));
                currentNode.numberOfChildren++;
            } else {
                index = word.charAt(i) - 97;
                if (currentNode.childrenOfNode[index] == null) {
                    currentNode.childrenOfNode[index] = new Node(word.charAt(i));
                    currentNode.numberOfChildren++;
                }
            }
            currentNode = currentNode.childrenOfNode[index];
        }
        currentNode.isWord = true;
    }

    static void deleteWord(String word, Node root) {
        if (root.numberOfChildren == 0) {
            System.out.println("The tree is empty!");
        } else {
            currentNode = root;
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == ' ') {
                    index = 26;
                } else {
                    index = word.charAt(i) - 97;
                }
                currentNode = currentNode.childrenOfNode[index];
            }
            if (!currentNode.isWord) {
                System.out.println("This word does not exist in the tree!");
            } else {
                currentNode.isWord = false;
                System.out.println("The word has been deleted successfully!");
            }
        }
    }

    static boolean searchWord(String word, Node root) {
        boolean doesExist = true;
        currentNode = root;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == ' ') {
                index = 26;
            } else {
                index = word.charAt(i) - 97;
            }
            if (currentNode.childrenOfNode[index] == null) {
                doesExist = false;
                break;
            }
            currentNode = currentNode.childrenOfNode[index];
        }
        if (!currentNode.isWord) {
            doesExist = false;
        }
        return doesExist;
    }

    static void suggestWord(String word, Node root) {
        currentNode = root;
        if (currentNode.isWord) {
            System.out.println(word);
        }
        int[] indexOfChildren = new int[currentNode.numberOfChildren];
        int in = 0;
        while (in < currentNode.numberOfChildren) {
            for (int i = 0; i < 27; i++) {
                if (currentNode.childrenOfNode[i] != null) {
                    indexOfChildren[in] = i;
                    in++;
                }
            }
        }
        StringBuilder suggestion = new StringBuilder(word);
        for (int i = 0; i < currentNode.numberOfChildren; i++) {
            currentNode = currentNode.childrenOfNode[indexOfChildren[i]];
            suggestion = suggestion.append(currentNode.data);
            suggestWord(suggestion.toString(), currentNode);
        }
    }

//    static boolean hasNoChild(Node node) {
//        boolean hasNoChild = true;
//        for (int i = 0; i < 27; i++) {
//            if (node.childrenOfNode[i] != null) {
//                hasNoChild = false;
//            }
//        }
//        return hasNoChild;
//    }

    static void menu(Node root) {
        Scanner input = new Scanner(System.in);
        System.out.println("1) add word");
        System.out.println("2) delete word");
        System.out.println("3) search word");
        System.out.println("4) suggest word");
        int choice = input.nextInt();
        String word;
        input.nextLine();
        if (choice == 1) {
            System.out.println("Enter the word: ");
            word = input.nextLine();
            insertWord(word, root);
            menu(root);
        } else if (choice == 2) {
            System.out.println("Enter the word: ");
            word = input.nextLine();
            deleteWord(word, root);
            menu(root);
        } else if (choice == 3) {
            System.out.println("Enter the word: ");
            word = input.nextLine();
            if (searchWord(word, root)) {
                System.out.println("This word is in the tree:)");
            } else {
                System.out.println("This word is not in the tree:(");
            }
            menu(root);
        } else if (choice == 4) {
            currentNode = root;
            System.out.println("Enter the word: ");
            word = input.nextLine();
            for (int i = 0; i < word.length(); i++) {
                index = word.charAt(i) - 97;
                if (currentNode.childrenOfNode[index] != null) {
                    currentNode = currentNode.childrenOfNode[index];
                }
            }
            suggestWord(word, currentNode);
            menu(root);
        }
    }

    public static void main(String[] args) {
        Node root = new Node();
        menu(root);
    }
}
