import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
/**
 * This class is the item class, where the items are created with their description and weight.
 * 
 * @Christopher Reyes
 * 04/13/2019
 */
public class Item
{
    
    private String descriptionItem; 
    private int weight;
    /**
     * Constructor for the Item class
     * @param descriptionItem stores the description of the items
     * @param weight stores the weight of the items in type int.
     */
    public Item(String descriptionItem, int weight)
    {
        // initialise instance variables
        this.descriptionItem = descriptionItem;
        this.weight = weight;

    }

    /**
     * Method that gets the description of the desired item.
     * @return The description of the item.
     */
    public String getItemDescription()
    {   
        return descriptionItem;
    }
    
    /**
     * Method that gets the weight of the desired item.
     * @return The weight of the item.
     */
    public int getWeight()
    {
        return weight;
    }
    
    /**
     * Method that gets the description of the desired item in a more detailed way.
     * @return The description of the item more detailed including its weight.
     */
    public String getLongDescription()
    {
     return "Item" + descriptionItem + "Weight" + weight;   
    }
    
    /**
     * This method sets the description of the item
     * @param Of type string that takes the description of the item.
     */
    public void setItemDescription(String descriptionItem) 
    {
        this.descriptionItem = descriptionItem;
    }

    /**
     * This method sets the weight of the item
     * @param Of type int that takes the weight of the item.
     */
    public void setWeight(int weight) 
    {
        this.weight = weight;
    }
    
    /**
     * This method returns the description if the item to string.
     * @return Description of item.
     */
    public String toString()
    {
    return descriptionItem;
    }
    
}