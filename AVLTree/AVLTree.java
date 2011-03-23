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

        private void insert(String key, Object value) {
            if (key.compareTo(this.key) < 0) {
                if (this.left == null) {
                    this.left = new Node(key, value, this);
                } else {
                    this.left.insert(key, value);
                }
            } else {
                if (this.right == null) {
                    this.right = new Node(key, value, this);
                } else {
                    this.right.insert(key, value);
                }
            }
            this.depth = Math.max(depth(parent.left), depth(parent.right)) + 1;
            checkAndBalance(this); //betta check yaself befo' yo wreck yaself shiggity-check yaself befoä yo wreck yaself
            //cuz big deez in y mouth is bad fo' yo health.
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

        private void checkAndBalance(Node nd) {
            Node subtreeRoot = nd;
            Node subtreeRight = nd.right;
            Node subtreeLeft = nd.left;

            if (depth(subtreeLeft) - depth(subtreeRight) <= -2) {//höger subträd är tyngre än vänster i subträdsroten
                if (depth(subtreeRight.left) - depth(subtreeRight.right) < 0) {
                    rightRightRotation(subtreeRoot);
                } else if (depth(subtreeRight.left) - depth(subtreeRight.right) > 1) {
                    rightLeftRotation(subtreeRoot);
                } else {
                    System.out.println("Balancing Failed. Previous Balances funked up.");
                }
            } else if (depth(subtreeLeft) - depth(subtreeRight) >= 2) {//vänster subträd är tyngre än höger i subträdsroten
                if (depth(subtreeLeft.left) - depth(subtreeLeft.right) < 0) {
                    leftLeftRotation(subtreeRoot);
                } else if (depth(subtreeLeft.left) - depth(subtreeLeft.right) > 1) {
                    leftRightRotation(subtreeLeft);
                }else {
                    System.out.println("Balancing Failed. Previous Balances funked up.");
                }
            }
        }

        private void leftLeftRotation(Node A) {
            Node B = A.left;
            Node F = A.parent;

            A.left = B.right;
            B.right = A;

            if (F == null) {
                root = B;
                B.parent = null;
            } else {
                if (F.right == A) {
                    F.right = B;
                } else {
                    F.left = B;
                }
            }
            A.parent = B;
        }

        private void rightRightRotation(Node A) {
            Node B = A.right;
            Node F = A.parent;

            A.right = B.left;
            B.left = A;
            if (F == null) {
                root = B;
                B.parent = null;
            } else {
                if (F.right == A) {
                    F.right = B;
                } else {
                    F.left = B;
                }
                A.parent = B;
            }
        }

        private void leftRightRotation(Node A) {
            Node B = A.right;
            Node C = B.left;
            Node F = A.parent;

            A.right = C.left;
            B.left = C.right;
            C.right = B;
            C.left = A;

            if (F == null) {
                root = C;
                C.parent = null;
            } else {
                C.parent = F;
                if (F.right == A) {
                    F.right = C;
                } else {
                    F.left = C;
                }
            }
            A.right.parent = A;
            B.left.parent = B;
            A.parent = B.parent = C;
        }

        private void rightLeftRotation(Node A) {
            Node B = A.left;
            Node C = B.right;
            Node F = A.parent;

            A.left = C.right;
            B.right = C.left;
            C.right = A;
            C.left = B;

            if (F == null) {
                root = C;
                C.parent = null;
            } else {
                C.parent = F;
                if (F.right == A) {
                    F.right = C;
                } else {
                    F.left = C;
                }
                A.right.parent = A;
                B.left.parent = B;
                A.parent = B.parent = C;
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
