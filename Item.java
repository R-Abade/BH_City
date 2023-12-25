import javax.print.DocFlavor.STRING;
public class Item
{
    private String description;
    private int qtde;
    
    /**
     * Construtor para objetos da classe Item
     */
    public Item(String description, int qtde)
    {
        this.description = description;
        this.qtde = qtde;
    }
    
     public void dropItem()
     {
        qtde--;
    }

    public void pickItem()
    {
        qtde++;
    }
    
    public String getDescription()
    {
        return description;
    }

    
}
