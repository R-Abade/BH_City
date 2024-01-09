import java.util.*;

public class Player {

    private int qtdeItens;
    private Item item;
    private HashMap<String, Item> itensOfPlayer = new HashMap<>();

    public void pickItem(String name){
        itensOfPlayer.get(name).gainItem();
        updateQtde(name,true);
    }

    public void dropItem(String name, int qtde){
        itensOfPlayer.get(name).loseItem();
        updateQtde(name,false);
        if(itensOfPlayer.get(name).getQtdeItens() == 0){
            itensOfPlayer.remove(name);
        }
    }

    public String getItems(){
        Set<String> keys = itensOfPlayer.keySet();
        String information = "";
        for(String key: keys){
            information += " "+key +":"+ itensOfPlayer.get(key).getQtdeItens();
        }
        return "Inventory: " + "\n"+ "Items of player: " + information + "\n"+ "Total dos trem, ze: "+ qtdeItens;
    }

    public Item getItem(String name){
        return itensOfPlayer.get(name) != null ? itensOfPlayer.get(name) : null;
    }

    private void setItem(String name, Item item) {
        itensOfPlayer.put(name,item);
        updateQtde(name,true);
    }

    public void createItem(String description, int qtde, String name) {
        Item item = new Item(description, name, qtde);
        setItem(name, item);
    }

    private void updateQtde(String name, boolean add){
        if(add){
            qtdeItens +=  itensOfPlayer.get(name).getQtdeItens();
        }
        else{
            qtdeItens -=  itensOfPlayer.get(name).getQtdeItens();
        }

    }
}