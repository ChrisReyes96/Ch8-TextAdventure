import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
/**
 * Write a description of class Items here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item
{
    // instance variables - replace the example below with your own
    private String descriptionItem; 
    private int weight;
    
    
    /**
     * Constructor for objects of class Items
     */
    public Item(String descriptionItem, int weight)
    {
        // initialise instance variables
        this.descriptionItem = descriptionItem;
        this.weight = weight;
        
        
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public String getItemDescription()
    {   
        return descriptionItem;
    }
    
    public int getWeight()
    {
        return weight;
    }
    
    public String getLongDescription()
    {
     return "Item" + descriptionItem + "Weight" + weight;   
    }
    
    public void setItemDescription(String descriptionItem) 
    {
        this.descriptionItem = descriptionItem;
    }


    public void setWeight(int weight) 
    {
        this.weight = weight;
    }
	
    public String toString()
    {
	return descriptionItem;
    }
    
}