import java.util.*;
import javax.sound.midi.Soundbank;
import java.net.URL;
import javax.swing.*;
import javax.sound.sampled.*;

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
    private Command command;
    private Stack<Room> oldRooms = new Stack<>();
    private Player player = new Player();
    Scanner tec = new Scanner(System.in);
    Random random = new Random();
    private boolean wifeWant = false;

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
        currentRoom.createItem("Dinheiro para fazer os trem aqui", 50, "dinheiro");
        casa = new Room("lar, doce lar");
        motel = new Room("isso, mais forte, yamete kudasai, ahn, ahn ahn");
        sobeeDesce = new Room("Tá se perdendo no personagem!!!");

         // initialise room exits
        mineirao.setExit("norte",guanabara);
        mineirao.setExit("leste",escritorio);
        //---------------------------------------------
        pirulito.setExit("norte", escritorio);
        pirulito.setExit("sul", pLiberdade);
        //---------------------------------------------
        guanabara.setExit("norte", mineirao);
        guanabara.setExit("sul", mangabeiras);
        //---------------------------------------------
        pBandeira.setExit("leste" , pLiberdade);
        pBandeira.setExit("sul" , pPapa);
        pBandeira.setExit("oeste" , mangabeiras);
        //---------------------------------------------
        pPapa.setExit("norte", pBandeira);
        pPapa.setExit("sul", mangabeiras);
        //---------------------------------------------
        pLiberdade.setExit("sul", pirulito);
        pLiberdade.setExit("oeste", pBandeira);
        //---------------------------------------------
        mangabeiras.setExit("leste", pBandeira);
        //---------------------------------------------
        escritorio.setExit("oeste", mineirao);
        escritorio.setExit("sul", pirulito);
        escritorio.setExit("norte", pLiberdade);
        escritorio.setExit("leste", casa);
        //---------------------------------------------
        casa.setExit("norte",guanabara);
        //---------------------------------------------
        motel.setExit("oeste", pirulito);
        motel.setExit("norte", pPapa);
        //---------------------------------------------
        sobeeDesce.setExit("norte", pPapa);
        sobeeDesce.setExit("oeste", pBandeira );
        sobeeDesce.setExit("sul", mangabeiras);
        sobeeDesce.setExit("leste", motel);
        
        List<Room> startspoint = Arrays.asList(mineirao, guanabara, pBandeira, pPapa, pLiberdade, pirulito, mangabeiras, motel, sobeeDesce);
        List<Room> passear = Arrays.asList(mineirao, guanabara, pBandeira, pPapa, pLiberdade, mangabeiras);
        
        // room's function
        if(currentRoom == casa){
            boolean roleta = random.nextBoolean();
            Room passeio = passear.get(random.nextInt(startspoint.size()));
            if(roleta) {
                if(player.getItem("dama da noite") != null){
                System.out.println("Perdeu tudo na divisao de bens");
                quit(command);
                }
                else{
                    currentRoom.createItem("Para momentos memoraveis", 1, "camera");
                    currentRoom.createItem("Um dia feliz (triste), um bom lugar para ler um livro...", 1, "livro");
                    System.out.println("Filhos: papai, podemos ir ate "+passear+"hoje?");
                    System.out.println("Claro!");
                    System.out.println("Esposa: que legal! Se divirtam"); 
                }
            }
            else {
                System.out.println("Vamos sair, amor?");
                wifeWant = true;
                currentRoom = motel;
            }
            
        }
        
        else if(currentRoom == escritorio){ 
            System.out.println("Chefe: voce precisa de quantas notas de R$50?");
            int notas = tec.nextInt();
            System.out.println("Chefe: vou emprestar R$"+(50*notas)+". Mas voce devera me pagar em horas extras...");
            player.createItem("Dinheiro pois vivemos num mundo capitalista e necessitamos de um meio de troca eficiente para transaçoes economicas",(50*notas), "dinheiro");
            player.pickItem("dinheiro");
            
        }
        
        else if(currentRoom == guanabara){
            System.out.println("Voce precisa de R$120 para os 4 membros da familia entrarem");
            if(item.getQtdeItem("dinheiro") == 120){
                
                System.out.println("Familia curtiu um role no parque da familia e todos se divertiram, parabens!");
            }
            else{
                System.out.println("Voce acabou com o passeio da propria familia. Gamer Over");
                quit(command);
            }
        }
        
        else if(currentRoom == mineirao){
            System.out.println("Hoje nao tem jogo ;-;. Voce quer uma camisa do galo? Custa R$200");
            String choose = tec.nextLine().toLowerCase();
            if(choose == "sim"){
                System.out.println("Quantas camisas?");
                int qtdade = tec.nextInt();
                if(item.getQtdeItem("dinheiro") == 200*qtdade){
                    currentRoom.createItem("Voce eh muito galo doido, torce para o maior de Minas", qtdade, "Camisa do Galo");
                    player.dropItem("dinheiro", (200*qtdade));
                }
            }
            else
                System.out.println("Ataque da galoucura kkkkkkkkkkk. JOSIAS TOG ON. PERDEU TUDO!!");
                System.out.println("Voce realizou o sonho de todo homem: morrer em briga de torcida organizada");
                quit(command);
        }
        
        else if(currentRoom == mangabeiras){
            System.out.println("Aqui eh um luga bom dmais para deixar as crianças brincando");
            System.out.println("Eh um bom momento para ler um livro para passar o tempo");
            if(player.getItem("livro") != null){
                player.pickItem("Livro");
            }
            else{
                 System.out.println("Ate que foi divertido");   
            }
            
        }
        
        else if(currentRoom == pLiberdade){
            System.out.println("Voce eh uma boa pessoa? Digite sim ou nao");
            String choose = tec.nextLine().toLowerCase();
            if(choose == "sim"){
                System.out.println("De um trocado para seu mendigo, Oh BH abundante");
                int doar = tec.nextInt();
                player.dropItem("dinheiro", doar);
                System.out.println("Agora os mendigos vao comprar cachaça!");
            }
            else
                player.dropItem("dinheiro", 999);
                System.out.println("Ataque dos mendigo loko kkkkkkkkkkk. PERDEU TUDO");
        }
        
        else if(currentRoom == pPapa){
                tec.nextLine();
                String choose = tec.nextLine().toLowerCase();
                System.out.println("Voce vai orar? Digite sim ou nao");
                if(choose == "sim"){
                    currentRoom.createItem("Uma biblia para acompanhar a oraçao, amem", 1, "Biblia");
                    currentRoom.createItem("Bendito seja este irmao", 1, "bençao");
                    player.pickItem("Biblia");
                    player.pickItem("bencao");
                    System.out.println("Em nome do Pai, do filho, e do espirito santo...");
                }
                else{
                   System.out.println("VOCE SO NAO VAI PODER OUVIR, O SOM DA ULTIMA TROMBETAAAAAAAAAAA!!");
                }
        }
        
        else if(currentRoom == pBandeira){
            System.out.println("Momento agradavel com a familia, merece uma foto");
            if(player.getItem("camera") != null){
                currentRoom.createItem("Recordaçao feliz", 1, "foto");
                player.pickItem("foto");
            }
            else{
                System.out.println("Foi divertido");
            }
        }
        
        else if(currentRoom == pirulito){
            while(currentRoom == pirulito){
                tec.nextLine();
                String choose = tec.nextLine().toLowerCase();
                System.out.println("OLHA O CHIP DA TIIIIIIIM? Digite sim ou nao caso queira");
                if(choose == "sim"){
                    currentRoom.createItem("Agora tens um numero novo", 1, "chip da tim");
                    player.pickItem("chip da tim");
                    player.dropItem("dinheiro", 20);
                    System.out.println("Novo numero adquirido");
                }

                else{
                    System.out.println("Quer naum");
                }
                
                System.out.println("OLHA O CHIP DA Vivo? Digite sim ou nao caso queira");
                tec.nextLine();
                choose = tec.nextLine().toLowerCase();
                if(choose == "sim"){
                    currentRoom.createItem("Agora tens um numero novo", 1, "chip da vivo");
                    player.pickItem("chip da vivo");
                    player.dropItem("dinheiro", 20);
                    System.out.println("Novo numero adquirido");
                }

                else{
                    System.out.println("Quer naum");
                }
                
                tec.nextLine();
                choose = tec.nextLine().toLowerCase();
                System.out.println("OLHA O CHIP DA Claro? Digite sim ou nao caso queira");
                if(choose == "sim"){
                    currentRoom.createItem("Agora tens um numero novo", 1, "chip da claro");
                    player.pickItem("chip da claro");
                    player.dropItem("dinheiro", 20);
                    System.out.println("Novo numero adquirido");
                }

                else{
                    System.out.println("Quer naum");
                }
                
                tec.nextLine();
                choose = tec.nextLine().toLowerCase();
                System.out.println("COMPRO OOOOUROOO? Digite sim ou nao caso queira");
                if(choose == "sim"){
                    if(player.getItem("ouro") != null)
                    currentRoom.createItem("Dinheiro pois vivemos num mundo capitalista e necessitamos de um meio de troca eficiente para transaçoes economicas",(500), "dinheiro");
                    player.pickItem("dinheiro");
                    player.dropItem("ouro", 1);
                    currentRoom.gainItem("ouro", item );
                    System.out.println("Reserva boa mesmo eh biticonho");
                }
                else{
                System.out.println("Quer naum");
                }
                
                tec.nextLine();
                choose = tec.nextLine().toLowerCase();
                System.out.println("FOTO 3x4? Digite sim ou nao caso queira");
                if(choose == "sim"){
                    player.dropItem("dinheiro", 15);
                    player.pickItem("foto");
                    System.out.println("Bonitaum meu nobre");
                }

                else{
                    System.out.println("Quer naum");
                }
                
                currentRoom.createItem("Profissional do eita bixo kkkkk", 1, "dama da noite");
                System.out.println("Oi gostoso, rsrs. Quer se divertir em algum lugar?");
                tec.nextLine();
                choose = tec.nextLine().toLowerCase();
                if(choose == "sim"){
                    player.pickItem("dama da noite");
                    System.out.println("Sua esposa vai saber...Boa sorte...");
                    currentRoom = motel;
                }
                else{
                    System.out.println("Saia, sou da tropa dos 100% fiel");
                }
                goRoom(command);
            }
            
        }
        
        else{
            if(player.getItem("dama da noite") != null){
                System.out.println("Ui. que delicia...");
                System.out.println("TOC TOC");
                System.out.println("TOC TOC TOC TOC TOC!!!");
                System.out.println("Sua esposa acaba de adentrar furiosa... Gamer Over");
                quit(command);     
            }
            else{
                if(wifeWant = true){
                    System.out.println("O novo herdeiro vem, rsrs");
                }
                else{
                    System.out.println("Voce nao deveria estar aqui...");
                    currentRoom.getExitString();
                }
            }
        }
        
        do{
            currentRoom = startspoint.get(random.nextInt(startspoint.size()));
        } while(currentRoom.equals(casa));
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
        System.out.println("Soh força");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Coe ze, BH eh nois, fraga?");
        System.out.println("Eh aqui que eu amuuuuuuuu, eh aqui queu quero fica, pois num ha luga meioh que BH");
        System.out.println("Que qui pega, ze. Ce vai aparecer num canto aleatorio di BH e tem que ih pra casa, fraga? Podi se importante pega uns trem");
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
            System.out.println("Viaja ni mim naum");
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
        }else if(commandWord.equals("pick")){
           pickItem(command);
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
            System.out.println("Vai ondi kkkkk? Loko de toddyn estragado");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("Tah viajanu kkk");
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
            System.out.println("Vaza");
            return false;
        } else {
            return true; // signal that we want to quit
        }
    }

    private void printLocationInfo() {
        System.out.println(currentRoom.getLongDescription());
    }

    private void pickItem(Command command){
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("qual trem?");
            return;
        }

        String secondWord = command.getSecondWord();
        Item itemRoom = currentRoom.getItem(secondWord);

        if (itemRoom == null) {
            System.out.println("Tem ess trem aqui naum");
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
            player.dropItem(secondWord, 1);
        }else{
            System.out.println("Ce possui ess trem naum meu fi");
        }

        String itensOfPlayer = player.getItems();
        String itensOfRoom = currentRoom.getItems();
        System.out.println(itensOfPlayer + "\n" + itensOfRoom);

    }

}
