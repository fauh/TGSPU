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
            //betta check yaself befo' yo wreck yaself shiggity-check yaself befoä yo wreck yaself
            //cuz big deez in y mouth is bad fo' yo health.
            this.depth = Math.max(depth(parent.left), depth(parent.right)) + 1;
            return this.checkAndBalance();
            //return this.rebalance();
        }

        private Object find(String key) {
            if (key.equalsIgnoreCase(key)) {
                return this.value;
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
            depth = Math.max(depth(left), depth(right));
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

            if (depth(subtreeLeft) - depth(subtreeRight) <= -1) {//höger subträd är tyngre än vänster i subträdsroten
                if (depth(subtreeRight.left) - depth(subtreeRight.right) < 0) {
                    return clockWiseRotation(subtreeRoot);
                } else if (depth(subtreeRight.left) - depth(subtreeRight.right) > 1) {
                    //dubbelhöger
                    return counterClockWiseRotation(clockWiseRotation(subtreeLeft));
                } else {
                    System.out.println("Balancing Failed. Previous Balances funked up.");
                    return null;
                }
            } else if (depth(subtreeLeft) - depth(subtreeRight) >= 2) {//vänster subträd är tyngre än höger i subträdsroten
                if (depth(subtreeLeft.left) - depth(subtreeLeft.right) < 0) {
                    return counterClockWiseRotation(subtreeRoot);
                } else if (depth(subtreeLeft.left) - depth(subtreeLeft.right) > 1) {
                    clockWiseRotation(subtreeRight);
                    return counterClockWiseRotation(subtreeRoot);
                } else {
                    System.out.println("Balancing Failed. Previous Balances funked up.");
                    return null;
                }
            } else {
                System.out.println("NO NEED MAN NO NEED");
                return this;
            }
        }

        /**
         * given:
         *            node
         *          /     \
         *     node.left  newRoot
         *                 /    \
         *       newRoot.left  newRoot.right
         *
         * ----------------------------------
         * result:
         *
         *
         *              newRoot
         *              /       \
         *         node       newRoot.right
         *         /   \
         *    node.left   newRoot.left
         *
         * @param unbalanced node
         */
        private Node counterClockWiseRotation(Node node) {
            // TODO : Update heights..
            // TODO : check if node.parent == null ... update root
            Node newRoot = node.right;
            Node oldParent = node.parent;

            newRoot.parent = node.parent;
            node.parent = newRoot;
            node.right = newRoot.left;
            newRoot.left = node;

            if (oldParent == null) {
                root = newRoot;
            }

            return newRoot;
        }

        /**
         * AVLTree "Right Rotation"
         * @param unbalanced node
         */
        private Node clockWiseRotation(Node node) {
            // TODO : Update heights..
            // TODO : check if node.parent == null ... update root
            // same on rotateRight()
            Node oldParent = node.parent;
            Node newRoot = node.left;

            newRoot.parent = node.parent;
            node.parent = newRoot;
            node.left = newRoot.right;
            newRoot.right = node;

            if (oldParent == null) {
                root = newRoot;
            }
            return newRoot;
        }

        /**
         * Given metod ifrån BENI
         * @return
         */
        private Node rebalance() {
            if (depth(this.left) - depth(this.right) < -1) {
                //högertungt
                if (depth(this.right.left) > depth(this.right.right)) {
//                    //vänstertungt barn
//                 x
//               /   \
//              x     y
//             / \   / \
//            A    z1   z2
//                / \   /D\
//                B  C
                    return this.rotate(this.left, this, this.left.right.left, this.left.right, this.left.right.right, this.right, this.right.right, this.parent);
                } else {
//                 simpel
//                 x
//               /   \
//              x     y
//             / \   / \
//            A    z1   z2
//                / \   /D\
//                B  C
                    return this.rotate(this.left, this, this.right.left, this.right, this.right.right.left, this.right.right, this.right.right.right, this.parent);
                }
            }
            if (depth(this.left) - depth(this.right) > 1) {
                //vänstertugnt
                if (depth(this.right.left) > depth(this.right.right)) {
                    return null; //rotate();
                } else {
                    return null; //rotate();
                }
            } else {
                return null;
            }
        }

        /**
         * result:
         *
         *         r
         *       /   \
         *      x     y
         *     / \   / \
         *    A   B C   D
         *
         * FIGURE OUT HOW SHIT LOOKS FIRST YO
         *
         * @param A
         * @param x
         * @param B
         * @param r root
         * @param C
         * @param y
         * @param D
         * @param parent
         * @return
         */
        private Node rotate(Node A, Node x, Node B, Node r, Node C, Node y, Node D, Node parent) {
            //fixa djup
            x.depth = Math.max(depth(A), depth(B) + 1);
            y.depth = Math.max(depth(C), depth(D) + 1);
            r.depth = Math.max(depth(x), depth(y) + 1);
            //referenser
            x.left = A;
            x.right = B;
            y.left = C;
            y.right = D;
            r.left = x;
            r.right = y;
            //Parentfix
            A.parent = x;
            B.parent = x;
            C.parent = y;
            D.parent = y;
            x.parent = r;
            y.parent = r;
            r.parent = parent;

            return r;
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
                return node.value;
            } else {
                Node node = root.delete(key);
                if (node != null) {
                    return node.value;
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

    public Object getNext() {
        if (ref.left == null && ref.right == null) {
            if (ref.parent.left == ref) {
                ref = ref.parent;














                return ref.value;














            } else {
                while (ref.parent != null && ref.parent.right == ref) {
                    ref = ref.parent;














                }
                if (ref.parent != null) {
                    ref = ref.parent;














                } else {
                    ref = null;














                    return null;














                }
                return ref.value;














            }
        } else {
            if (ref.right != null) {
                ref = ref.right;
















                while (ref.left != null) {
                    ref = ref.left;














                }
                return ref.value;














            } else {
                if (ref.parent != null) {
                    while (ref.parent.right == ref) {
                        ref = ref.parent;














                    }
                    if (ref.parent != null) {
                        ref = ref.parent;














                    }
                    return ref.value;














                } else {
                    return null;














                }
            }
        }
    }

    public int size() {
        return this.size;







    }
}
