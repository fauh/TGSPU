/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AVLTree;

/**
 *
 * @author Spellabbet
 */
public class AVLTree implements Directory {

    private class Node {

        private String key;
        private Object value;
        private Node parent, left, right;
        private int depth;

        public Node(String key, Object value, Node parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.depth = 1;
        }

        private Node(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        private int depth(Node nd) {
            return (nd != null) ? nd.depth : 0;
        }

        private Node insert(String key, Object value) {
            if (key.compareTo(this.key) < 0) {
                if (this.left == null) {
                    this.left = new Node(key, value, this);
                } else {
                    this.left = this.left.insert(key, value);
                }
            } else {
                if (this.right == null) {
                    this.right = new Node(key, value, this);
                } else {
                    this.right = this.right.insert(key, value);
                }
            }
            fixDepth(this);
            return this.checkAndBalance();
        }

        private Object find(String key) {
            if (key.equalsIgnoreCase(key)) {
                return this;
            } else if (key.compareTo(this.key) < 0) {
                if (left != null) {
                    return left.find(key);
                } else {
                    return "missing data";
                }
            } else {
                if (right != null) {
                    return right.find(key);
                } else {
                    return "missing data";
                }
            }
        }

        private Node delete(String key) {
            Node deleteNode = (Node) find(key);
            if (deleteNode.left == null && deleteNode.right == null) {
                if (deleteNode == deleteNode.parent.left) {
                    deleteNode.parent.left = null;
                } else {
                    deleteNode.parent.right = null;
                }
            } else if (deleteNode.left == null) {
                if (deleteNode.parent.left == deleteNode) {
                    deleteNode.parent.left = deleteNode.right;
                    deleteNode.right.parent = deleteNode.parent;
                } else {
                    deleteNode.parent.right = deleteNode.right;
                    deleteNode.right.parent = deleteNode.parent;
                }
            } else if (deleteNode.right == null) {
                if (deleteNode.parent.left == deleteNode) {
                    deleteNode.parent.left = deleteNode.left;
                    deleteNode.left.parent = deleteNode.parent;
                } else {
                    deleteNode.parent.right = deleteNode.left;
                    deleteNode.left.parent = deleteNode.parent;
                }
            } else {
                if (deleteNode.parent.left == deleteNode) {
                    deleteNode.parent.left = deleteNode.left;
                    deleteNode.left.parent = deleteNode.parent;
                } else {
                    deleteNode.parent.right = deleteNode.left;
                    Node rightMost = deleteNode.left.getRightMost();
                    if (rightMost.left != null) {
                        rightMost.parent.right = rightMost.left;
                        rightMost.left.parent = rightMost.parent;
                    }
                    if (deleteNode.parent.left == deleteNode) {
                        deleteNode.parent.left = rightMost;
                    } else {
                        deleteNode.parent.right = rightMost;
                    }
                    rightMost.parent = deleteNode.parent;
                    rightMost.left = deleteNode.left;
                    rightMost.right = deleteNode.right;
                    rightMost.left.parent = rightMost;
                    rightMost.right.parent = rightMost;
                }
            }
            size--;
            fixDepth(deleteNode.parent);
            return deleteNode;
        }

        private Node getRightMost() {
            if (this.right == null) {
                return this;
            }
            return this.right.getRightMost();
        }

        /**
         * DON'T USE THIS ITS BROKEN
         * @param node that is unbalanced.
         */
        private Node checkAndBalance() {
            Node subtreeRoot = this;
            Node subtreeRight = this.right;
            Node subtreeLeft = this.left;

            if (depth(subtreeLeft) - depth(subtreeRight) <= -2) {//höger subträd är tyngre än vänster i subträdsroten
                if (depth(subtreeRight.left) > depth(subtreeRight.right)) {
                    return counterClockWiseRotation(subtreeRoot);
                } else if (depth(subtreeRight.left) - depth(subtreeRight.right) > 1) {
                    //dubbelhöger
                    return counterClockWiseRotation(clockWiseRotation(subtreeLeft));
                } else {
                    System.out.println("Balancing Failed. Previous Balances funked up.");
                    return null;
                }
            } else if (depth(subtreeLeft) - depth(subtreeRight) >= 2) {//vänster subträd är tyngre än höger i subträdsroten
                if (depth(subtreeLeft.left)< depth(subtreeLeft.right) ) {
                    return clockWiseRotation(subtreeRoot);
                } else if (depth(subtreeLeft.left) - depth(subtreeLeft.right) > 1) {
                    clockWiseRotation(subtreeRight);
                    return clockWiseRotation(counterClockWiseRotation(subtreeRoot));
                } else {
                    System.out.println("Balancing Failed. Previous Balances funked up.");
                    return null;
                }
            } else {
                return this;
            }
        }

        private Node counterClockWiseRotation(Node node) {
            Node newRoot = node.right;
            Node oldParent = node.parent;

            newRoot.parent = node.parent;
            node.parent = newRoot;
            node.right = newRoot.left;
            newRoot.left = node;

            if (oldParent == null) {
                root = newRoot;
            }
            fixDepth(node);
            return newRoot;
        }

        /**
         * AVLTree "Right Rotation"
         * @param unbalanced node
         */
        private Node clockWiseRotation(Node node) {
            Node oldParent = node.parent;
            Node newRoot = node.left;

            newRoot.parent = node.parent;
            node.parent = newRoot;
            node.left = newRoot.right;
            newRoot.right = node;

            if (oldParent == null) {
                root = newRoot;
            }
            fixDepth(node);
            return newRoot;
        }

        public void fixDepth(Node node) {
            while (node != null) {
                node.depth = Math.max(depth(node.left), depth(node.right)) + 1;
                node = node.parent;
            }
        }
    }
    private Node root, ref;
    private int size;

    public void insert(String key, Object value) {
        if (root != null) {
            root.insert(key, value);
        } else {
            root = new Node(key, value);
        }
        size++;
    }

    public Object find(String key) {
        if (root != null) {
            return root.find(key);
        }
        return null;
    }

    public Object delete(String key) {
        if (root != null) {
            if (key.equalsIgnoreCase(root.key)) {
                Node node = root;
                if (size == 1) {
                    root = null;
                } else {
                    if (root.left != null) {
                        Node parentNode;
                        if (root.left.right != null) {
                            parentNode = root.left.getRightMost();
                            root.left.parent = parentNode;
                        } else {
                            parentNode = root.left;
                        }
                        if (root.right != null) {
                            root.parent = parentNode;
                        }
                        parentNode.parent = null;
                        parentNode.left = root.left;
                        parentNode.right = root.right;
                        root = parentNode;
                    } else {
                        root.right.parent = null;
                        root = root.right;
                    }
                }
                return node;
            } else {
                Node node = root.delete(key);
                if (node != null) {
                    return node;
                }
            }
            return null;
        } else {
            return null;
        }
    }

    public Object getFirst() {
        if (root != null) {
            ref = root;
            while (ref.left != null) {
                ref = ref.left;
            }
        }
        return ref.value;
    }
    //DO NOT USE

    public Object getNext() {
        if (ref.left == null && ref.right == null) {
            if (ref.parent.left == ref) {
                ref = ref.parent;
                return ref;
            } else {
                while (ref.parent != null && ref.parent.right == ref) {
                    ref = ref.parent;
                }
                if (ref.parent != null) {
                    ref = ref.parent;
                } else {
                    ref = null;
                    return ref;
                }
                return ref;
            }
        } else {
            if (ref.right != null) {
                ref = ref.right;
                while (ref.left != null) {
                    ref = ref.left;
                }
                return ref;
            } else {
                if (ref.parent != null) {
                    while (ref.parent.right == ref) {
                        ref = ref.parent;
                    }
                    if (ref.parent != null) {
                        ref = ref.parent;
                    }
                    return ref;
                } else {
                    return null;
                }
            }
        }
    }

    public int size() {
        return this.size;
    }

    public static void main(String[] args) {
        AVLTree avt = new AVLTree();
        String name, number;

        for (int i = 0; i < 100; i++) {
            name = Integer.toString(i);
            number = Integer.toString(i);
            avt.insert(name, number);
        }
        System.out.println(avt.getFirst());
        while (avt.ref != null) {
            Node nd = (Node) avt.getNext();
            if (nd != null) {
                System.out.println(nd.value + " höjd: " + nd.depth);
            } else {
                System.out.println("null");
            }
        }


        while (avt.ref != null) {
            Node nd = (Node) avt.getNext();
            if (nd != null) {
                System.out.println(nd.value);
            } else {
                System.out.println("null");
            }
        }
    }

    public Node getNext2() {
        if (ref == null) {
            return null;
        }
        if (ref.right != null) {
            ref = ref.right;
            while (ref.left != null) {
                ref = ref.left;
            }
            return ref;
        }
        while (ref.parent != null) {
            if (ref.parent.left == ref) {
                ref = ref.parent;
                return ref;
            }
        }
        ref = null;
        return ref;
    }
}
