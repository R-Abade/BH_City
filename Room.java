import java.util.*;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 *
 * A "Room" represents one location in the scenery of the game. It is
 * connected to other rooms via exits. The exits are labelled north,
 * east, south, west. For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Room {
    private String description;
    private HashMap<String, Room> exits = new HashMap<>(); 
    private HashMap<String,Item> itensOnRoom = new HashMap<>(); 

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * 
     * @param description The room's description.
     */
    public Room(String description) {
        this.description = description;
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    private void setItem(String name, Item item) {
        itensOnRoom.put(name,item);
    }
    
     public int getQtde()
    {
        int qtdeOfItens = itensOnRoom.keySet().size();
        System.out.println("Número total de itens na sala: " + qtdeOfItens);
        
        return qtdeOfItens;
    }

    public void createItem(String description, int qtde, String name) {
        Item item = new Item(description, name, qtde);
        setItem(name, item);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription() {
        return description;
    }

    public Room getExit(String direction) {
        return exits.get(direction) != null ? exits.get(direction) : null;
    }

    public String getExitString() {
        Set<String> keys = exits.keySet();
        String information = String.join(" ", keys);
        return information;
    }

    public String getLongDescription() {
        return "You are " + description + "\n" + "Exits: " + getExitString() + "\n" + getItems();
    }

    public String getItems(){
        String information = "";
        Set<String> keys = itensOnRoom.keySet();
        for(String key: keys){
            information +=    key + ":" +itensOnRoom.get(key).getQtdeItens() + " ";
        }
        return "Items of room:"+information;
    }
    
    public void dropItem(String name){
        itensOnRoom.get(name).loseItem();
        if(itensOnRoom.get(name).getQtdeItens() == 0){
            itensOnRoom.remove(name);
        }
    }

    public void pickItem(String name, Item item){
        if(itensOnRoom.get(name) != null){
            itensOnRoom.get(name).gainItem();
        }else{
            createItem(item.getDescription(), 1,name);
            int itemQtde = itensOnRoom.get(name).getQtdeItens();
            System.out.println(itemQtde);
        }

    }
    public Item getItem(String name){
        return itensOnRoom.get(name) != null ? itensOnRoom.get(name) : null;
    }
    
    public void loseItem(String name){
        itensOnRoom.get(name).loseItem();
        if(itensOnRoom.get(name).getQtdeItens() > 0){
            itensOnRoom.remove(name);
        }
    }

    public void gainItem(String name, Item item){
        if(itensOnRoom.get(name) != null){
            itensOnRoom.get(name).gainItem();
        }else{
            createItem(item.getDescription(), 1,name);
            int a = itensOnRoom.get(name).getQtdeItens();
            System.out.println(a);
        }

    }
}