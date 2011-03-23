/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package AVLTree;

/**
 *
 * @author Spellabbet
 */
public class Person {
    String name;
    String number;

    public Person(String name, String number){
        this.name = name;
        this.number = number;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setNumber(String number){
        this.number = number;
    }
    public String getName(){
        return this.name;
    }
    public String getNumber(){
        return this.number;
    }
    public String toString(){
        return getName()+"\n "+getNumber();
    }
}
