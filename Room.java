import java.util.*;
import java.lang.*;

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
public class Room extends Item
{
    private String description;
    private HashMap<String, Room> exits = new HashMap<>();
    private Item itens = new Item(name, item);
    private String information = "";
    
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description, String name, Item item) 
    {
        this.description = description;
        this.name = name;
        this.item = item;
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
        information = String.join(" ", keys);
        return this.information;
    }
    
    public String getLongDescription() 
    {   
        return "You are " + description + "\n" + "Exits: " + getExitString() + "\n" + "Items: " + item.setItem(name, item);
    }
}
