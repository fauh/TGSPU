/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package AVLTree;

/**
 *
 * @author Spellabbet
 */
public interface Directory {
    public Object find(String key);
    public void insert(String key, Object value);
    public Object delete(String key);
    public int size();
    public Object getFirst();
    public Object getNext();
}