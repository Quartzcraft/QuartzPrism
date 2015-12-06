package uk.co.quartzcraft.prism.appliers;

import java.util.List;

import org.bukkit.entity.Player;

import uk.co.quartzcraft.prism.Prism;
import uk.co.quartzcraft.prism.actionlibs.QueryParameters;
import uk.co.quartzcraft.prism.actions.Handler;

public class Undo extends Preview {

    /**
     * 
     * @param plugin
     * @return
     */
    public Undo(Prism plugin, Player player, List<Handler> results, QueryParameters parameters, ApplierCallback callback) {
        super( plugin, player, results, parameters, callback );
    }

    /**
     * Set preview move and then do a rollback
     * 
     * @return
     */
    @Override
    public void preview() {
        player.sendMessage( Prism.messenger.playerError( "You can't preview an undo." ) );
    }
}