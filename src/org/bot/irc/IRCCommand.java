package org.bot.irc;

public enum IRCCommand {
    NICK_COMMAND("NICK %s \r\n"),
    USER_COMMAND("USER %s 8 * : Java IRC Hacks Bot\r\n"),
    JOIN_COMMAND("JOIN %s \r\n"),
    PONG_COMMAND("PONG %s \r\n"),
    PRIVMSG_COMMAND("PRIVMSG %s :");

    private String command;
    
    private IRCCommand(String command) {
        this.command = command;
    }
    
    public String parseCommand(String parameters) {
        return String.format(command, parameters);
    }
}
