package uk.co.quartzcraft.prism.actionlibs;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.plugin.Plugin;

import uk.co.quartzcraft.prism.Prism;
import uk.co.quartzcraft.prism.actions.BlockAction;
import uk.co.quartzcraft.prism.actions.BlockChangeAction;
import uk.co.quartzcraft.prism.actions.BlockShiftAction;
import uk.co.quartzcraft.prism.actions.EntityAction;
import uk.co.quartzcraft.prism.actions.EntityTravelAction;
import uk.co.quartzcraft.prism.actions.GenericAction;
import uk.co.quartzcraft.prism.actions.GrowAction;
import uk.co.quartzcraft.prism.actions.Handler;
import uk.co.quartzcraft.prism.actions.HangingItemAction;
import uk.co.quartzcraft.prism.actions.ItemStackAction;
import uk.co.quartzcraft.prism.actions.PlayerAction;
import uk.co.quartzcraft.prism.actions.PlayerDeathAction;
import uk.co.quartzcraft.prism.actions.PrismProcessAction;
import uk.co.quartzcraft.prism.actions.PrismRollbackAction;
import uk.co.quartzcraft.prism.actions.SignAction;
import uk.co.quartzcraft.prism.actions.UseAction;
import uk.co.quartzcraft.prism.actions.VehicleAction;
import uk.co.quartzcraft.prism.exceptions.InvalidActionException;

public class HandlerRegistry<H> {

    /**
	 * 
	 */
    private final HashMap<String, Class<? extends Handler>> registeredHandlers = new HashMap<String, Class<? extends Handler>>();

    /**
	 * 
	 */
    public HandlerRegistry() {
        registerPrismDefaultHandlers();
    }

    /**
     * Register a new action type for event recording, lookups, etc.
     * 
     * @param handlerClass
     */
    protected void registerHandler(Class<? extends Handler> handlerClass) {
        final String[] names = handlerClass.getName().split( "\\." );
        if( names.length > 0 ) {
            registeredHandlers.put( names[names.length - 1], handlerClass );
        }
    }

    /**
     * Register a new action type for event recording, lookups, etc.
     * 
     * @param apiPlugin
     * @param handlerClass
     * @throws InvalidActionException
     */
    public void registerCustomHandler(Plugin apiPlugin, Class<? extends Handler> handlerClass)
            throws InvalidActionException {
        // Is plugin allowed?
        @SuppressWarnings("unchecked")
        final ArrayList<String> allowedPlugins = (ArrayList<String>) Prism.config
                .getList( "prism.tracking.api.allowed-plugins" );
        if( !allowedPlugins.contains( apiPlugin.getName() ) ) { throw new InvalidActionException(
                "Registering action type not allowed. Plugin '" + apiPlugin.getName()
                        + "' is not in list of allowed plugins." ); }
        final String[] names = handlerClass.getName().split( "\\." );
        if( names.length > 0 ) {
            registeredHandlers.put( names[names.length - 1], handlerClass );
        }
    }

    /**
     * 
     * @param name
     * @return
     */
    public Handler getHandler(String name) {
        if( name != null ) {
            if( registeredHandlers.containsKey( name ) ) {
                try {
                    final Class<? extends Handler> handlerClass = registeredHandlers.get( name );
                    return new HandlerFactory<Handler>( handlerClass ).create();
                } catch ( final InstantiationException e ) {
                    e.printStackTrace();
                } catch ( final IllegalAccessException e ) {
                    e.printStackTrace();
                }
            }
        }
        return new GenericAction();
    }

    /**
	 * 
	 */
    private void registerPrismDefaultHandlers() {

        registerHandler( GenericAction.class );
        registerHandler( BlockAction.class );
        registerHandler( BlockChangeAction.class );
        registerHandler( BlockShiftAction.class );
        registerHandler( EntityAction.class );
        registerHandler( EntityTravelAction.class );
        registerHandler( GrowAction.class );
        registerHandler( HangingItemAction.class );
        registerHandler( ItemStackAction.class );
        registerHandler( PlayerAction.class );
        registerHandler( PlayerDeathAction.class );
        registerHandler( PrismProcessAction.class );
        registerHandler( PrismRollbackAction.class );
        registerHandler( SignAction.class );
        registerHandler( UseAction.class );
        registerHandler( VehicleAction.class );

    }
}