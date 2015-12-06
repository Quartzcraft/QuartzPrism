package uk.co.quartzcraft.prism.commands;

import me.botsko.elixr.TypeUtils;
import uk.co.quartzcraft.prism.Prism;
import uk.co.quartzcraft.prism.commandlibs.CallInfo;
import uk.co.quartzcraft.prism.commandlibs.SubHandler;
import uk.co.quartzcraft.prism.events.BlockStateChange;
import uk.co.quartzcraft.prism.events.PrismBlocksExtinguishEvent;
import uk.co.quartzcraft.prism.utils.BlockUtils;

import java.util.ArrayList;
import java.util.List;

public class ExtinguishCommand implements SubHandler {

    /**
	 * 
	 */
    private final Prism plugin;

    /**
     * 
     * @param plugin
     * @return
     */
    public ExtinguishCommand(Prism plugin) {
        this.plugin = plugin;
    }

    /**
     * Handle the command
     */
    @Override
    public void handle(CallInfo call) {

        int radius = plugin.getConfig().getInt( "prism.ex.default-radius" );
        if( call.getArgs().length == 2 ) {
            if( TypeUtils.isNumeric( call.getArg( 1 ) ) ) {
                final int _tmp_radius = Integer.parseInt( call.getArg( 1 ) );
                if( _tmp_radius > 0 ) {
                    if( _tmp_radius > plugin.getConfig().getInt( "prism.ex.max-radius" ) ) {
                        call.getPlayer()
                                .sendMessage( Prism.messenger.playerError( "Radius exceeds max set in config." ) );
                        return;
                    } else {
                        radius = _tmp_radius;
                    }
                } else {
                    call.getPlayer()
                            .sendMessage(
                                    Prism.messenger
                                            .playerError( "Radius must be greater than zero. Or leave it off to use the default. Use /prism ? for help." ) );
                    return;
                }
            } else {
                call.getPlayer()
                        .sendMessage(
                                Prism.messenger
                                        .playerError( "Radius must be a number. Or leave it off to use the default. Use /prism ? for help." ) );
                return;
            }
        }

        final ArrayList<BlockStateChange> blockStateChanges = BlockUtils.extinguish( call.getPlayer().getLocation(),
                radius );
        if( blockStateChanges != null && !blockStateChanges.isEmpty() ) {

            call.getPlayer().sendMessage( Prism.messenger.playerHeaderMsg( "Extinguished nearby fire! Cool!" ) );

            // Trigger the event
            final PrismBlocksExtinguishEvent event = new PrismBlocksExtinguishEvent( blockStateChanges,
                    call.getPlayer(), radius );
            plugin.getServer().getPluginManager().callEvent( event );

        } else {
            call.getPlayer().sendMessage(
                    Prism.messenger.playerError( "No fires found within that radius to extinguish." ) );
        }
    }

    @Override
    public List<String> handleComplete(CallInfo call) {
        return null;
    }
}