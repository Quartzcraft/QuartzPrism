package uk.co.quartzcraft.prism.commands;

import uk.co.quartzcraft.prism.Prism;
import uk.co.quartzcraft.prism.commandlibs.CallInfo;
import uk.co.quartzcraft.prism.commandlibs.SubHandler;
import org.bukkit.ChatColor;

import java.util.List;

public class AboutCommand implements SubHandler {

    /**
	 * 
	 */
    private final Prism plugin;

    /**
     * 
     * @param plugin
     * @return
     */
    public AboutCommand(Prism plugin) {
        this.plugin = plugin;
    }

    /**
     * Handle the command
     */
    @Override
    public void handle(CallInfo call) {
        call.getSender().sendMessage(
                Prism.messenger.playerHeaderMsg( "Prism - By " + ChatColor.GOLD + "viveleroi" + ChatColor.GRAY + " v"
                        + plugin.getPrismVersion() ) );
        call.getSender().sendMessage( Prism.messenger.playerSubduedHeaderMsg( "Help: " + ChatColor.WHITE + "/pr ?" ) );
        call.getSender().sendMessage(
                Prism.messenger.playerSubduedHeaderMsg( "IRC: " + ChatColor.WHITE + "irc.esper.net #prism" ) );
        call.getSender().sendMessage(
                Prism.messenger.playerSubduedHeaderMsg( "Wiki: " + ChatColor.WHITE + "http://discover-prism.com" ) );
    }

    @Override
    public List<String> handleComplete(CallInfo call) {
        return null;
    }
}