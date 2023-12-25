import java.util.*;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Room 
{
    private String description;
    private HashMap<String, Room> exits = new HashMap<>();
    private HashMap<String, Item> itemsOnRoom = new HashMap<>();
    
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
    }
    
    public void setExits(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    
    public Room getExit(String direction)
    {
        return exits.get(direction) != null ? exits.get(direction) : null;
        
    }
    
    public String getDescription()
    {
        return description;
    }
    
    /**
    * Retorna uma descrição das saídas deste Room,
    * por exemplo, "Exits: north west".
    * @return Uma descrição das saídas disponíveis.
    */
    private String getExitString() 
    {
        Set<String> keys = exits.keySet();
        String information = String.join(" ", keys);
        return information;
    }
    
    public String getLongDescription() 
    {
        return "You are " + description + "\n" + "Exits: " + getExitString() + "\n" + "Items: " + getItems();
    }
    
    private void setItem(String name, Item item) {
        itemsOnRoom.put(name,item);
    }
    
    public String getItems(){
        Set<String> keys = itemsOnRoom.keySet();
        String information = String.join(" ", keys);
        return information;
    }
    
    public Item getItem(String name)
    {
        String information = "";
        Set<String> keys = itemsOnRoom.keySet();
        for(String key: keys){
            information +=    key + ":" +itemsOnRoom.get(key).getQtde() + " ";
        }
        return "Items of room:"+information;
    }

    public void dropItem(String name)
    {
        itemsOnRoom.get(name).dropItem();
        if(itemsOnRoom.get(name).getQtde() == 0){
            itemsOnRoom.remove(name);
        }
    }
    
    public void createItem(String description, int amount, String name) 
    {
        Item item = new Item(description,amount);
        setItem(name, item);
    }
    
    public void pickItem(String name, Item item)
    {
        if(itemsOnRoom.get(name) != null){
            itemsOnRoom.get(name).pickItem();
        }else{
            createItem(item.getDescription(),1,name);
            int itemQtde = itemsOnRoom.get(name).getQtde();
            System.out.println(itemQtde);
        }

    }

}
