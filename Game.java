import java.util.*;
import javax.sound.midi.Soundbank;

/**
 * This class is the main class of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game. Users
 * can walk around some scenery. That's all. It should really be extended
 * to make it more interesting!
 * 
 * To play this game, create an instance of this class and call the "play"
 * method.
 * 
 * This main class creates and initialises all the others: it creates all
 * rooms, creates the parser and starts the game. It also evaluates and
 * executes the commands that the parser returns.
 * 
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game {
    private Parser parser;
    private Room currentRoom;
    private Item item;
    private Stack<Room> oldRooms = new Stack<>();
    private Player player = new Player();

    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {
        Room mineirao, pirulito, guanabara, mangabeiras, pBandeira, pPapa, pLiberdade, escritorio, casa, motel, sobeeDesce;

        // create the rooms
        mineirao = new Room("mais conhecido como gigante da Pampulha (Salao de festas do Galo DOIDO!!!)");
        guanabara = new Room("o parqe da familia, Guanabara, uma festa a cada dia!");
        pBandeira = new Room("belas vistas num conhecido ponto turistico");
        pPapa = new Room("amem na praça do Papa, irmaos");
        pLiberdade = new Room("bem bonito as fontes, o problema eh us mendigo tomando banho");
        pirulito = new Room("só lembro que existe quando o meu time ganha alguma coisa...(Já fazem mais de 84 anos)");
        mangabeiras = new Room("mangueiras eh bom para levar as crianças");
        escritorio = new Room("eu to cansado, chefe");
        casa = new Room("lar, doce lar");
        motel = new Room("isso, mais forte, yamete kudasai, ahn, ahn ahn");
        sobeeDesce = new Room("Tá se perdendo no personagem!!!");

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
	casa.setExit("norte",sobeeDesce);
        motel.setExit("norte", casa);
	sobeeDesce.setExit("sul", casa);

        currentRoom = casa; // start game outside
    }

    /**
     * Main play routine. Loops until end of play.
     */
    public void play() {
        printWelcome();

        // Enter the main command loop. Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Coe ze, BH eh nois, fraga?");
        System.out.println("Eh aqui que eu amuuuuuuuu, eh aqui quequero ficar, pois num ha luga meioh que BH");
        System.out.println("Qual trem que ce que fazer, fi?");
        System.out.println();
        printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * 
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        String secondWord = command.getSecondWord();
        if (commandWord.equals("help")) {
            printHelp();
        } else if (commandWord.equals("go")) {
            goRoom(command);
        } else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }else if(commandWord.equals("look")){
            printLocationInfo();
        }else if(commandWord.equals("back")){
            goRoom(command);
        }else if(commandWord.equals("take")){
           takeItem(command);
        }else if(commandWord.equals("drop")){

            dropItem(command);
        }else if(commandWord.equals("items")){
            String items = player.getItems();
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
    private void printHelp() {
        System.out.println("Ta perdidin, dirocha. Tu vai logali");
        System.out.println("Eh o seguinte, ze");
        System.out.println();
        System.out.println("Voce vai fazer isso aqui, fraga ,ze?");
        parser.showCommands();
    }

    /**
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) {
        if(command.getCommandWord().equals("back")){
            if(oldRooms.size() == 0){
                System.out.println("Ta viajanu meu fi kkk, dirocha memu"); 
                return; 
            }else{      
                currentRoom = oldRooms.pop();
                printLocationInfo();
                return;
            }

        }else if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("Sem trem");
        }else {
            oldRooms.push(currentRoom); 
            currentRoom = nextRoom;     
            printLocationInfo();
        }
    }

    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * 
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quequi foi meu fi?");
            return false;
        } else {
            return true; // signal that we want to quit
        }
    }

    private void printLocationInfo() {
        System.out.println(currentRoom.getLongDescription());
    }

    private void takeItem(Command command){
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("qual trem?");
            return;
        }

        String secondWord = command.getSecondWord();
        Item itemRoom = currentRoom.getItem(secondWord);

        if (itemRoom == null) {
            System.out.println("ess trem nao existe na sala!");
            return;
        }

        currentRoom.loseItem(secondWord);

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
            System.out.println("qual trem ce quer?");
            return;
        }

        String secondWord = command.getSecondWord();
        Item itemPlayer = player.getItem(secondWord);

        if(itemPlayer != null){
            currentRoom.gainItem(secondWord, itemPlayer);
            player.dropItem(secondWord);
        }else{
            System.out.println("Ce possui ess trem naum meu fi");
        }

        String itensOfPlayer = player.getItems();
        String itensOfRoom = currentRoom.getItems();
        System.out.println(itensOfPlayer + "\n" + itensOfRoom);

    }

}
