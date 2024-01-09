import javax.print.DocFlavor.STRING;

public class Item {

    private String description;
    private String name;
    private int qtde;

    public Item(String description, String name, int qtde){
        this.description = description;
        this.name = name;
        this.qtde = qtde;
    }

    public void loseItem(){
        qtde--;
    }

    public void gainItem(){
        qtde++;
    }

    public int getQtdeItem(String name){
        return qtde;
    }
    
    public int getQtdeItens(){
        return qtde;
    }

    public String getDescription(){
        return description;
    }

}