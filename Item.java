import javax.print.DocFlavor.STRING;
import java.util.*;
import java.lang.*;

public class Item
{
    private String description;
    protected String name;
    protected Item item;
    protected Map<String, Item> itensOnRoom = new HashMap<>();
    
    /**
     * Construtor para objetos da classe Item
     */
    public Item(String description, String name, Item item)
    {
        this.description = description;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setItem(String name,Item item) {
        itensOnRoom.put(name,this.item);
    }
   
    /*public void getItem(String name)
    {
        //String information = "";
        Set<String> keys = itensOnRoom.keySet();
        for(String key: keys){
            String information =    key + ":" +itensOnRoom.get(key)+" ";
        }
        
    }
    */
    public Item getItem(String name){
        return itensOnRoom.get(name) != null ? itensOnRoom.get(name) : null;
    }
    public int getQtde()
    {
        int qtdeOfItens = itensOnRoom.keySet().size();
        System.out.println("Número total de itens na sala: " + qtdeOfItens);
        
        return qtdeOfItens;
    }

    public void dropItem(Item item)
    {
        if(itensOnRoom.get(item).getQtde() > 0){
            itensOnRoom.remove(item);
        }
        else {
            System.out.println("Nao ha item para remover");
        }
    }
    
    public void createItem(String description, int qtde, String name) 
    {
        item = new Item(description, name, item);
        setItem(name, this.item);
    }
    
    public void pickItem(String name, Item item)
    {
        if(itensOnRoom.get(name) != null){
            itensOnRoom.get(name).pickItem(name, item);
        }else{
            createItem(item.getDescription(),1,name);
            int itemQtde = itensOnRoom.get(name).getQtde();
            System.out.println(itemQtde);
        }

    }
}
