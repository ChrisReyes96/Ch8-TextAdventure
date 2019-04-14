
import java.util.ArrayList;
/**
 *  This class is the inventory class where a inventory of type array is created.
 *  The inventory class is called every time a item is picked or dropped. Or when 
 *  inventory information needs to be printed.
 *  
 * 
 * @author  Christopher Reyes
 * @version 04/13/2019
 */
public class Inventory{

    private ArrayList<Item> items;
    private int maxWeight;
    private int currentWeight;
    
    /**
     * Constructor for inventory class.
     * @param maxWeight takes the maximum weight of the inventory.
     */
    public Inventory(int maxWeight){
        this.maxWeight = maxWeight;
        this.currentWeight = 0;
        this.items = new ArrayList<Item>();
    }
    
    /**
     * Creates a inventory of maximum weight of 10.
     */
    public Inventory(){
        this(10);
    }
    
    /**
     * Method that adds item to the inventory.
     * @param of type Item, item that is stored in the inventory.
     * @return true if item is added to inventory and there is enough space
     * @return false if inventory is full.
     */
    public boolean addItem(Item item){
        if ((currentWeight + item.getWeight()) <= maxWeight){
            items.add(item);
            currentWeight += item.getWeight();
            return true;
        }
        return false;
    }
    
    /**
     * Method that deletes item from the inventory.
     * @param of type Item, item that is deleted from the inventory.
     */
    public void removeItem(Item item){
        if(items.size() > 0){
            currentWeight -= Math.max(item.getWeight(), 0);
            items.remove(item);
        }
    }
    
    /**
     * Method that gets selected item from array.
     * @return items in the array list.
     */
    public ArrayList<Item> getItems(){
        return items;
    }

    /** Method that gets selected item from array.
     * @return The maximum Weight.
     */
    public int getMaxWeight() {
        return maxWeight;
    }

    /** Method that gets the current weight of inventory.
     * @return The current weight.
     */
    public int getCurrentWeight() {
        return currentWeight;
    }

    /** Method that sets the maximum weight.
     * @param maxWeight that takes the maximum weight of the inventory.
     */
    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    /** Method that sets the current weight in the inventory.
     * @param maxWeight that takes the current weight of the inventory.
     */
    public void setCurrentWeight(int currentWeight) {
        this.currentWeight = currentWeight;
    }
    

}