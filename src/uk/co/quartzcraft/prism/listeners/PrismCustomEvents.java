package uk.co.quartzcraft.prism.listeners;

import java.util.ArrayList;

import uk.co.quartzcraft.prism.Prism;
import uk.co.quartzcraft.prism.actionlibs.ActionFactory;
import uk.co.quartzcraft.prism.actionlibs.RecordingQueue;
import uk.co.quartzcraft.prism.events.PrismCustomPlayerActionEvent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PrismCustomEvents implements Listener {

    /**
	 * 
	 */
    private final Prism plugin;

    /**
     * 
     * @param plugin
     */
    public PrismCustomEvents(Prism plugin) {
        this.plugin = plugin;
    }

    /**
     * 
     * @param event
     */
    @SuppressWarnings("unchecked")
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onCustomPlayerAction(final PrismCustomPlayerActionEvent event) {
        final ArrayList<String> allowedPlugins = (ArrayList<String>) plugin.getConfig().getList(
                "prism.tracking.api.allowed-plugins" );
        if( allowedPlugins.contains( event.getPluginName() ) ) {
            RecordingQueue.addToQueue( ActionFactory.createPlayer(event.getActionTypeName(), event.getPlayer(),
                    event.getMessage()) );
        }
    }
}