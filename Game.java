import java.util.ArrayList;
/**
 *  This class is the main class of the "Homer Simpson" application. 
 *  "Homer Simpson" is a text based adventure game.  Users 
 *  can walk around some scenery. Pick items, look for information in rooms. 
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game. Creates and inventory and items to add
 *  to the inventory 
 *  It also evaluates and executes the commands that the parser returns.
 * 
 * @author  Christopher Reyes
 * @version 04/13/2019
 */

public class Game 
{
      
    private int minutes; //stores minutes left
    private Parser parser;
    private Room currentRoom, explosionZone, dreams, canteenRoom, mechanicalRoom;
    private Inventory inventory; 
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        inventory = new Inventory();
        minutes = 20; 
    }
    

    /**
     * Create all the rooms, items in the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room controlRoom, maintanceRoom, laboratory, bathroom1, lockerRoom;
        Room office, lobby, reception, offices, bathroom2, exit;
      
        // create the rooms
        controlRoom = new Room("inside the control room");
        mechanicalRoom = new Room("in the mechanical room. Bart Simpons is here");
        maintanceRoom = new Room("in the maintance room");
        laboratory = new Room("in the laboratory");
        dreams = new Room ("in a Homer Simpson dream");
        bathroom1 = new Room("in the bathroom 1");
        lockerRoom = new Room("in the locker room");
        lobby = new Room("in the lobby");
        office = new Room("in Mr. Burns office");
        canteenRoom = new Room("in the canteen room. Ned Flanders is here.");
        offices = new Room ("in the offices");
        reception = new Room ("in the reception");
        bathroom2 = new Room ("in the bathroom2");
        explosionZone = new Room("in the explosion zone");
        exit = new Room ("out of the nuclear power plant");
        
        
        // initialise room exits
        controlRoom.setExit("east", maintanceRoom);
        controlRoom.setExit("south", mechanicalRoom);
        controlRoom.setExit("west", bathroom1);
        controlRoom.addItem(new Item("flashlight", 1));
        controlRoom.addItem(new Item("mask", 1));

        mechanicalRoom.setExit("north", controlRoom);
        mechanicalRoom.setExit("east", explosionZone);
        mechanicalRoom.addItem(new Item("hammer", 1)); 
        mechanicalRoom.addItem(new Item("key", 1)); 
        
        maintanceRoom.setExit("east", laboratory);
        maintanceRoom.setExit("west", controlRoom);
        maintanceRoom.setExit("north", lockerRoom);
        maintanceRoom.addItem(new Item("key2", 1));
        
        laboratory.setExit("east", maintanceRoom);
        laboratory.setExit("north", dreams);
        laboratory.addItem(new Item("donuts", 1));
        
        dreams.setExit("south", laboratory);

        bathroom1.setExit("north", canteenRoom);
        bathroom1.setExit("east", controlRoom);
        bathroom1.addItem(new Item("gun", 1));

        lockerRoom.setExit("south", maintanceRoom);
        lockerRoom.setExit("west", lobby);
        lockerRoom.addItem(new Item("pillow", 1));
        
        lobby.setExit("west", canteenRoom);
        lobby.setExit("east", lockerRoom);
        lobby.addItem(new Item("water", 1));
        
        office.setExit("west", bathroom2);
        office.setExit("north", exit);
        office.addItem(new Item("lighter", 1));
        
        canteenRoom.setExit("south", bathroom1);
        canteenRoom.setExit("east", lobby);
        canteenRoom.setExit("north", offices);
        
        
        offices.setExit("south", canteenRoom);
        offices.setExit("north", reception);
        offices.setExit("east", bathroom2); 
        offices.addItem(new Item("helmet", 1));
        
        reception.setExit("south", offices);
        
        bathroom2.setExit("west", offices);
        bathroom2.setExit("east", office);
        bathroom2.addItem(new Item("phone", 1));
        
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
        System.out.println("Find a way to exit the nuclear plant. Going to another room "+
        "takes about a minute");
        System.out.println("You have 20 minutes to exit the nuclear plant");
        System.out.println("You have to LOOK for items in the room");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }
    
    /**
     * Print out the item information in each room
     */
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
    
    /**
     * Print out the inventory
     */
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
                printTime(); // prints time left every time you move
                break;
                
            case LOOK:
                look(command);
                break;
                
            case PICK:
                pick(command);
                break;
                
            case DROP:
                drop(command);
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
     * 
     */
    private void printHelp() 
    {
        
        System.out.println("Your command words are:");
        parser.showCommands();
    }
    
     /**
     * Print out time left
     */
    private void printTime()
    {
        System.out.println("You have " + minutes + " minutes left");
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
        if(minutes ==0)
        {
            System.out.println("The time is over");
            System.out.println("The nuclear plant has collapsed with you inside");
            System.out.println("You lost");
            System.exit(0);
        }
        
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        
        
        else if (nextRoom == explosionZone){
            System.out.println("You have entered the explosion zone");
            System.out.println("The toxic gas in this area has killed you");
            System.out.println("You lost!");
            System.exit(0);

        }
        
        else if (nextRoom == canteenRoom){
            System.out.println("Ned has a clue for you: ");
            System.out.println("Ned: Mr.Burns is so selfish. ");
            System.out.println("Ned: In case of any emergency, he would be the first one" +
            "to get out of here");
            System.out.println("Ned: 1 North, 3 East. It's all I can say, good luck! ");
            currentRoom = nextRoom;
        }
        else if (nextRoom == mechanicalRoom){
            System.out.println("Hey dad, don't worry about me. I will be fine");
            System.out.println("But you are going in the wrong direction.");
            currentRoom = nextRoom;
        }
        
        else if (nextRoom == dreams){
            System.out.println("A piece of the roof has fallen in your head");
            System.out.println("You are unconscious and having a dream");
            System.out.println("You lost 30 seconds ");
            System.out.println("You are back in the laboratory");
            minutes = minutes -3;
        }
        
        else 
        {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            minutes = minutes -1; //every move takes one minute
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
    
     /**
     * "Look" was entered. Look for information of a room and its items.
     * @param command The command to be processed.
     */
    private void look (Command command)
    {
        System.out.println(currentRoom.getLongDescription());
        printItemInfo();
        printInventory();
    }
    
     /**
     * "Pick" command. Picks items in rooms. Checks if inventory is full.
     * Checks if items exists in the current room.
     * @param command The command to be processed.
     */
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
     
     /**
     * "Drop" command. Drops item contained in inventory.
     * @param command The command to be processed.
     */
     public void drop(Command command){
    	ArrayList<Item> items = inventory.getItems();
    	for (int i = 0; i < items.size(); i++){
    		Item item = items.get(i);
    		if(command.getSecondWord().equals(item.getItemDescription())){
    			inventory.removeItem(item);
    			currentRoom.addItem(item);
    			System.out.println("Dropped item: " + item.getItemDescription());
    			break;
    		}
    	}
    }
 }
