package uk.co.quartzcraft.prism.wands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import uk.co.quartzcraft.prism.Prism;
import uk.co.quartzcraft.prism.actionlibs.ActionsQuery;
import uk.co.quartzcraft.prism.actionlibs.QueryParameters;
import uk.co.quartzcraft.prism.actionlibs.QueryResult;
import uk.co.quartzcraft.prism.appliers.PrismApplierCallback;
import uk.co.quartzcraft.prism.appliers.PrismProcessType;
import uk.co.quartzcraft.prism.appliers.Rollback;

public class RollbackWand extends QueryWandBase implements Wand {

    /**
	 * 
	 */
    protected boolean item_given = false;

    /**
     * 
     * @param plugin
     * @return
     */
    public RollbackWand(Prism plugin) {
        super( plugin );
    }

    /**
	 * 
	 */
    @Override
    public void playerLeftClick(Player player, Location loc) {
        if( loc != null ) {
            rollback( player, loc );
        }
    }

    /**
	 * 
	 */
    @Override
    public void playerRightClick(Player player, Location loc) {
        if( loc != null ) {
            rollback( player, loc );
        }
    }

    /**
     * 
     * @param player
     * @param block
     */
    protected void rollback(Player player, Location loc) {

        final Block block = loc.getBlock();

        plugin.eventTimer.recordTimedEvent( "rollback wand used" );

        // Build params
        QueryParameters params;
        try {
            params = parameters.clone();
        } catch ( final CloneNotSupportedException ex ) {
            params = new QueryParameters();
            player.sendMessage( Prism.messenger
                    .playerError( "Error retrieving parameters. Checking with default parameters." ) );
        }
        params.setWorld( player.getWorld().getName() );
        params.setSpecificBlockLocation( block.getLocation() );
        params.setLimit( 1 );
        params.setProcessType( PrismProcessType.ROLLBACK );

        boolean timeDefault = false;
        for ( final String _default : params.getDefaultsUsed() ) {
            if( _default.startsWith( "t:" ) ) {
                timeDefault = true;
            }
        }
        if( timeDefault ) {
            params.setIgnoreTime( true );
        }

        final ActionsQuery aq = new ActionsQuery( plugin );
        final QueryResult results = aq.lookup( params, player );
        if( !results.getActionResults().isEmpty() ) {
            final Rollback rb = new Rollback( plugin, player, results.getActionResults(), params,
                    new PrismApplierCallback() );
            rb.apply();
        } else {
            final String space_name = ( block.getType().equals( Material.AIR ) ? "space" : block.getType().toString()
                    .replaceAll( "_", " " ).toLowerCase()
                    + ( block.getType().toString().endsWith( "BLOCK" ) ? "" : " block" ) );
            player.sendMessage( Prism.messenger.playerError( "Nothing to rollback for this " + space_name + " found." ) );
        }
    }

    /**
	 * 
	 */
    @Override
    public void playerRightClick(Player player, Entity entity) {}

    /**
	 * 
	 */
    @Override
    public void setItemWasGiven(boolean given) {
        this.item_given = given;
    }

    /**
	 * 
	 */
    @Override
    public boolean itemWasGiven() {
        return item_given;
    }
}