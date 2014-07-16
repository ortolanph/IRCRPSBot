package org.bot.irc;

public enum RPSCommand {
    RPS_PLAY_COMMAND("!RPSPLAY"),
    RPS_INFO_COMMAND("!RPSINFO"),
    RPS_HELP_COMMAND("!RPSHELP");
    
    private String command;

    private RPSCommand(String command) {
        this.command = command;
    }
    
    public String getCommand() {
        return this.command;
    }
    
}
