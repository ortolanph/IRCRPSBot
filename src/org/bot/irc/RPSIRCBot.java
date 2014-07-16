package org.bot.irc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bot.engine.RPSEngine;

public class RPSIRCBot {

    private static final String SERVER = "«server IP here»";
    private static final String NICK = "rpsbot";
    private static final String LOGIN = "«login here»";
    private static final int PORT = 6667;
    private static final String CHANNEL = "#«channel here»";

    private static final Logger LOGGER = Logger.getLogger(RPSIRCBot.class.getName());

    private Socket socket;
    private BufferedWriter writer;
    private BufferedReader reader;
    private final RPSEngine engine;
    private boolean started = false;

    public RPSIRCBot(RPSEngine engine) throws IOException {
        this.engine = engine;

        socket = new Socket(SERVER, PORT);

        try {
            writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

    }

    public boolean logon() throws IOException {
        boolean connected = false;

        writer.write(IRCCommand.NICK_COMMAND.parseCommand(NICK));
        writer.write(IRCCommand.USER_COMMAND.parseCommand(LOGIN));
        writer.flush();

        // Read lines from the server until it tells us we have connected.
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            if (line.contains("004")) {
                connected = true;
                break;
            }
            
            if (line.contains("433")) {
                connected = false;
                break;
            }
            
        }

        return connected;
    }

    public void join() throws IOException {
        writer.write(IRCCommand.JOIN_COMMAND.parseCommand(CHANNEL));
        writer.flush();
    }

    public void parseCommand() throws IOException {
        String line = null;
        while ((line = reader.readLine()) != null) {

            if (line.contains("PING")) {
                writer.write(IRCCommand.PONG_COMMAND.parseCommand(line.substring(5)));
                LOGGER.log(Level.INFO, IRCCommand.PONG_COMMAND.parseCommand(line.substring(5)));
                if(!started) {
                    writer.write(IRCCommand.PRIVMSG_COMMAND.parseCommand(CHANNEL) + engine.help() + "\r\n");
                    LOGGER.log(Level.INFO, "{0}{1}\r\n", new Object[]{IRCCommand.PRIVMSG_COMMAND.parseCommand(CHANNEL), engine.help()});
                    started = true;
                }
                writer.flush();
            }

            if (line.contains(RPSCommand.RPS_PLAY_COMMAND.getCommand())) {
                String parameter = getParameter(line);

                LOGGER.log(Level.INFO, line);
                LOGGER.log(Level.INFO, parameter);
                
                writer.write(IRCCommand.PRIVMSG_COMMAND.parseCommand(CHANNEL) + engine.play(parameter) + "\r\n");
                LOGGER.log(Level.INFO, "{0}{1}", new Object[]{IRCCommand.PRIVMSG_COMMAND.parseCommand(CHANNEL), engine.play(parameter)});
                writer.flush();
            } else if (line.contains(RPSCommand.RPS_INFO_COMMAND.getCommand())) {
                String parameter = getParameter(line);
                
                LOGGER.log(Level.INFO, line);
                LOGGER.log(Level.INFO, parameter);
                
                writer.write(IRCCommand.PRIVMSG_COMMAND.parseCommand(CHANNEL) + engine.info(parameter) + "\r\n");
                LOGGER.log(Level.INFO, "{0}{1}", new Object[]{IRCCommand.PRIVMSG_COMMAND.parseCommand(CHANNEL), engine.info(parameter)});
                writer.flush();
            } else if (line.contains(RPSCommand.RPS_HELP_COMMAND.getCommand())) {
                LOGGER.log(Level.INFO, line);
                writer.write(IRCCommand.PRIVMSG_COMMAND.parseCommand(CHANNEL) + engine.help() + "\r\n");
                LOGGER.log(Level.INFO, "{0}{1}", new Object[]{IRCCommand.PRIVMSG_COMMAND.parseCommand(CHANNEL), engine.help()});
                writer.flush();
            }
        }
    }

    private String getParameter(String line) {
        return line.substring(line.lastIndexOf(" ")).trim().toUpperCase();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        LOGGER.log(Level.INFO, "Closing writer");
        writer.close();
        LOGGER.log(Level.INFO, "Closing reader");
        reader.close();
        LOGGER.log(Level.INFO, "Closing socket");
        socket.close();
        LOGGER.log(Level.INFO, "Done");
    }
    
}
