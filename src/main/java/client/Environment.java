package client;

import cmd.ICommand;
import collection.CollectionManager;
import ioManager.IReadable;
import ioManager.IWritable;

import java.util.HashMap;

public class Environment {
    private CollectionManager collectionManager;
    private HashMap<String, ICommand> commandMap;
    private IReadable in;
    private IWritable out;
    private String path;
    private boolean running;
    private final boolean script;
    public Environment(CollectionManager collectionManager, HashMap<String, ICommand> commandMap,String path, IReadable in, IWritable out,boolean isScript){
        this.collectionManager = collectionManager;
        this.commandMap = commandMap;
        this.path = path;
        this.in = in;
        this.out = out;
        this.script = isScript;
        running = true;
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

    public HashMap<String, ICommand> getCommandMap() {
        return commandMap;
    }

    public IReadable getIn(){
        return in;
    }

    public IWritable getOut(){
        return out;
    }
    public boolean isRunning(){
        return running;
    }
    public void turnOff(){
        running = false;
    }
    public boolean isScript(){
        return script;
    }
    public String getPath() {
        return path;
    }
}
