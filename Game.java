import java.util.ArrayList;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.08.10
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom, explosionZone;
    private Inventory inventory; 
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        inventory = new Inventory();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room controlRoom, mechanicalRoom, maintanceRoom, room1, bathroom1, lockerRoom, lobby;
        Room office, canteenRoom, room2, room3, exit;
      
        // create the rooms
        controlRoom = new Room("inside the control room");
        mechanicalRoom = new Room("in the mechanical room");
        maintanceRoom = new Room("in the maintance room");
        room1 = new Room("in room1");
        bathroom1 = new Room("in the bathroom1");
        lockerRoom = new Room("in the locker room");
        lobby = new Room("in the lobby");
        office = new Room("in Mr. Burns office");
        canteenRoom = new Room("in the canteen room");
        room2 = new Room ("in room2");
        room3 = new Room ("in room3");
        explosionZone = new Room("in the explosion zone");
        exit = new Room ("out of the nuclear power plant");
        
        
        // initialise room exits
        controlRoom.setExit("east", maintanceRoom);
        controlRoom.setExit("south", mechanicalRoom);
        controlRoom.setExit("west", bathroom1);
        controlRoom.addItem(new Item("flashlight", 2));
        controlRoom.addItem(new Item("mask", 2));

        mechanicalRoom.setExit("north", controlRoom);
        mechanicalRoom.setExit("east", explosionZone);
        mechanicalRoom.addItem(new Item("hammer", 2)); 
        
        maintanceRoom.setExit("east", room1);
        maintanceRoom.setExit("west", controlRoom);
        maintanceRoom.setExit("north", lockerRoom);
        maintanceRoom.addItem(new Item("key", 2));
        
        room1.setExit("east", maintanceRoom);
        room1.addItem(new Item("donuts", 2));

        bathroom1.setExit("north", canteenRoom);
        bathroom1.setExit("east", controlRoom);
        bathroom1.addItem(new Item("gun", 2));

        lockerRoom.setExit("south", maintanceRoom);
        lockerRoom.setExit("west", lobby);
        lockerRoom.addItem(new Item("pillow", 2));
        
        lobby.setExit("west", canteenRoom);
        lobby.setExit("east", lockerRoom);
        lobby.addItem(new Item("water", 2));
        
        office.setExit("west", room3);
        office.setExit("north", exit);
        office.addItem(new Item("lighter", 2));
        
        canteenRoom.setExit("south", bathroom1);
        canteenRoom.setExit("east", lobby);
        canteenRoom.setExit("north", room2);
        canteenRoom.addItem(new Item("Ned_Flanders", 50));
        
        room2.setExit("south", canteenRoom);
        room2.setExit("east", room3);
        room2.addItem(new Item("helmet", 2));
        
        room3.setExit("west", room2);
        room3.setExit("east", office);
        room3.addItem(new Item("phone", 2));
        
        currentRoom = controlRoom;  // start game in control room
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Hey Homer Simpson");
        System.out.println("You fell asleep while working in the nuclear plant");
        System.out.println("There was an explosion and now the nuclear power plant is "+ 
         "about to collapse");
        System.out.println("Find a way to exit the nuclear plant. You have 5 minutes "+
        "before it collapses");
        System.out.println("You have 10 moves to exit the nuclear plant");
        System.out.println("You have to LOOK for items in the room");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }
    private void printItemInfo()
    {
        String result = "There is a ";
        ArrayList<Item> items = currentRoom.getItems();
        
        if (items.size() > 1)
        {
            for (int i=0; i <items.size() - 1; i++){
                result += items.get(i).getItemDescription() + ", a ";
            }
            result = result.substring(0, result.length()-2) +
            "and a " + items.get(items.size() - 1);
        }
        
        else if (items.size() == 1)
        {
            result+=  items.get(0);
        }
        
        else 
        {
            return;
        }
        
        System.out.println(result + " here");
    }
    
     private void printInventory(){
    	if(inventory.getItems().size() == 0)
    	{
    		System.out.println("You are currently not carrying anything");
    	}
    	else{
	    	System.out.println("You are currently carrying: ");
	    	for(Item i : inventory.getItems()){
	    		System.out.println("A " + i.getItemDescription());
	    	}
	    }
    }
    
    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;
                
            case LOOK:
                look(command);
                break;
                
            case PICK:
                pick(command);
                break;


            case QUIT:
                wantToQuit = quit(command);
                break;
                
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        boolean finished = false;
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        
        else if (nextRoom == explosionZone){
            System.out.println("You have entered the explosion zone");
            System.out.println("The toxic gas in this area has killed you");
            System.out.println("You lost!");
            System.exit(0);
        }
        else if (currentRoom != explosionZone)
        {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    private void look (Command command)
    {
        System.out.println(currentRoom.getLongDescription());
        printItemInfo();
        printInventory();
    }
    
    private void pick(Command command){
    	ArrayList<Item> items = currentRoom.getItems();
    	for (int i = 0; i < items.size(); i++){
    		Item item = items.get(i);
    		if(command.getSecondWord().equals(item.getItemDescription())){
    			if(inventory.addItem(item)){
    				currentRoom.removeItem(item);
    				System.out.println("Picked up item: " + 
    				item.getItemDescription());
    			}
    			else{
    				System.out.println("Your inventory is full!");    				
    			}
    			return;
    		}
    	}
    	System.out.println("That item is not here");
    }

}
