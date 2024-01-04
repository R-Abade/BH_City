import java.util.*;

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
 * @version 2016.02.29
 */

public class Game
{
    private Parser parser;
    private Room currentRoom;
    private Stack<Room> oldRooms = new Stack<>();
    private Player player = new Player();
    private CommandWords commandWords = new CommandWords();
       
    /**
     * Create the game and initialise its internal map.
     */
    public Game()
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room mineirao, pirulito, guanabara, mangabeiras, pBandeira, pPapa, pLiberdade, escritorio, casa, motel;
     
        // create the rooms
        mineirao = new Room("Mais conhecido como gigante da Pampulha (Salao de festas do Galo DOIDO!!!)");
        guanabara = new Room("o parqe da familia, Guanabara, uma festa a cada dia!");
        pBandeira = new Room("belas vistas num conhecido ponto turistico");
        pPapa = new Room("amem na praça do Papa, irmaos");
        pLiberdade = new Room("bem bonito as fontes, o problema eh o mendigo tomando banho");
        pirulito = new Room("Só lembro que existe quando o meu time ganha alguma coisa...(Já fazem mais de 84 anos)");
        mangabeiras = new Room("mangueiras eh bom para levar as crianças");
        escritorio = new Room("eu to cansado, chefe");
        casa = new Room("lar, doce lar");
        motel = new Room("isso, mais forte, yamete kudasai, ahn, ahn ahn");
        SobeeDesce = new Room("Tá se perdendo no personagem!!!");
       
        // initialise room exits
        mineirao.setExit("norte",guanabara);
        mineirao.setExit("leste",escritorio);
        pirulito.setExit("norte",escritorio);
        pirulito.setExit("norte", pLiberdade);
        guanabara.setExit("sul", mineirao);
        pBandeira.setExit("oeste" , pLiberdade);
        pBandeira.setExit("sul" , pPapa);
        pBandeira.setExit("leste" , mangabeiras);
        pPapa.setExit("norte", pBandeira);
        pPapa.setExit("sul", mangabeiras);
        pLiberdade.setExit("sul", pirulito);
        pLiberdade.setExit("leste", pBandeira);
        mangabeiras.setExit("oeste", pBandeira);
        escritorio.setExit( "leste", casa);
	escritorio.setExit("oeste", mineirao);
	escritorio.setExit("sul",pirulito);
        casa.setExit("sul", motel);
        casa.setExit("oeste", escritorio);
	casa.setExit("oeste",SobeeDesce);
        motel.setExit(norte"", casa);
	SobeeDesce.setExit("leste", casa);
	

        currentRoom = casa;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play()
    {            
        printWelcome();

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
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println("You are " + currentRoom.getDescription());
        System.out.print("Exits: ");
        printLocationInfo();
    }
   
    private void printLocationInfo(){
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command)
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        String secondWord = command.getSecondWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("look")) {
            printLocationInfo();
        }
        else if (commandWord.equals("eat")) {
            System.out.println("Snickers matou sua fome");
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if(commandWord.equals("pick")){
            pickItem(command);
        }
        else if(commandWord.equals("drop")){
            dropItem(command);
        }
        else if(commandWord.equals("items")){
            String items = player.getItem();
            System.out.println(items);
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
        System.out.println("Que qui pega, ze?");
        System.out.println("BH eh nois meu fi");
        System.out.println();
        System.out.println("Faz o seguinte entao: ", commandWords.getCommands());
        System.out.println("Go quit help");
    }

    /**
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }
        else
        {
            if(command.getCommandWord().equals("back")){
                if(oldRooms.size() == 0){
                    System.out.println("Sem salas");
                    return;
                }
                else
                {
                    currentRoom = oldRooms.pop();
                    printLocationInfo();
                    return;
                }
            }
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
       
        if(nextRoom == null)
        {
            System.out.println("There is no door");
        }
        else
        {
            oldRooms.push(currentRoom);
            currentRoom = nextRoom;
            printLocationInfo();
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
   
    private void pickItem(Command command){
        if(!command.hasSecondWord()){
            System.out.println("Que item queres?");
            return;
        }
       
        String secondWord = command.getSecondWord();
        Item itemRoom = currentRoom.getItem(secondWord);
       
        if (itemRoom == null) {
            System.out.println("Esse item nao existe aqui");
            return;
        }

        currentRoom.dropItem(secondWord);

        if(player.getItem(secondWord) != null){
            player.pickItem(secondWord);
        }else{
            player.createItem(itemRoom.getDescription(), 1, secondWord);
        }

        String itemsOfPlayer = player.getItems();
        String itemsOfRoom = currentRoom.getItems();
        System.out.println(itemsOfPlayer + "\n" + itemsOfRoom);

    }
   
    private void dropItem(Command command){
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Que item queres?");
            return;
        }

        String secondWord = command.getSecondWord();
        Item itemPlayer = player.getItem(secondWord);

        if(itemPlayer != null){
            currentRoom.pickItem(secondWord, itemPlayer);
            player.dropItem(secondWord);
        }else{
            System.out.println("O jogador nao possui este item");
        }

        String itemsOfPlayer = player.getItems();
        String itemsOfRoom = currentRoom.getItems();
        System.out.println(itemsOfPlayer + "\n" + itemsOfRoom);

    }

}
