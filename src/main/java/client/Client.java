package client;

public class Client {
    private Environment env;
    public Client(Environment env){
        this.env = env;
    }
    public void init() {
        while (env.isRunning()) {

            String s = env.getIn().readline();
            if (s == null)
                if (env.isScript())
                    break;
                else
                    continue;

            String cmd = "";
            String arg = "";

            String[] sArr = s.split("\\s");
            if (sArr.length==1)
                cmd = sArr[0];
            if (sArr.length>1)
            {
                cmd = sArr[0];
                arg = sArr[1];
            }

            if (env.getCommandMap().containsKey(cmd)) {
                env.getCommandMap().get(cmd).execute(env, arg);
            }
            else {
                env.getOut().writeln("Command not found (type \"help\" to get information about available commands)");
            }
        }
    }
}

