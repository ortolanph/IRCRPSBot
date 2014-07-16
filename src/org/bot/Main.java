package org.bot;

import org.bot.irc.RPSIRCBot;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bot.engine.RPSAssembly;
import org.bot.engine.RPSEngine;

public class Main {
    
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    
    public static void main(String[] args) {
        try {
            RPSAssembly assembly = new RPSAssembly();
            
            RPSEngine engine = new RPSEngine(assembly);
            
            RPSIRCBot ircBot = new RPSIRCBot(engine);

            ircBot.logon();
            ircBot.join();
            ircBot.parseCommand();

        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
}
