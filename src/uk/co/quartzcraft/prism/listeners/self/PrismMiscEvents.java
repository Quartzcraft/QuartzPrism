package uk.co.quartzcraft.prism.listeners.self;

import uk.co.quartzcraft.prism.actionlibs.ActionFactory;
import uk.co.quartzcraft.prism.actionlibs.RecordingQueue;
import uk.co.quartzcraft.prism.actionlibs.RecordingTask;
import uk.co.quartzcraft.prism.actions.Handler;
import uk.co.quartzcraft.prism.appliers.PrismProcessType;
import uk.co.quartzcraft.prism.events.BlockStateChange;
import uk.co.quartzcraft.prism.events.PrismBlocksDrainEvent;
import uk.co.quartzcraft.prism.events.PrismBlocksExtinguishEvent;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class PrismMiscEvents implements Listener {

    /**
     * 
     * @param event
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPrismBlocksDrainEvent(final PrismBlocksDrainEvent event) {

        // Get all block changes for this event
        final ArrayList<BlockStateChange> blockStateChanges = event.getBlockStateChanges();
        if( !blockStateChanges.isEmpty() ) {

            // Create an entry for the rollback as a whole
            final Handler primaryAction = ActionFactory.createPrismProcess("prism-process", PrismProcessType.DRAIN,
                    event.onBehalfOf(), "" + event.getRadius());
            final int id = RecordingTask.insertActionIntoDatabase( primaryAction );
            if( id == 0 ) { return; }
            for ( final BlockStateChange stateChange : blockStateChanges ) {

                final BlockState orig = stateChange.getOriginalBlock();
                final BlockState newBlock = stateChange.getNewBlock();

                // Build the action
                RecordingQueue.addToQueue( ActionFactory.createPrismRollback("prism-drain", orig, newBlock, event.onBehalfOf()
                        .getName(), id) );

            }
            // ActionQueue.save();
        }
    }

    /**
     * 
     * @param event
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPrismBlocksExtinguishEvent(final PrismBlocksExtinguishEvent event) {

        // Get all block changes for this event
        final ArrayList<BlockStateChange> blockStateChanges = event.getBlockStateChanges();
        if( !blockStateChanges.isEmpty() ) {

            // Create an entry for the rollback as a whole
            final Handler primaryAction = ActionFactory.createPrismProcess("prism-process", PrismProcessType.EXTINGUISH,
                    event.onBehalfOf(), "" + event.getRadius());
            final int id = RecordingTask.insertActionIntoDatabase( primaryAction );
            if( id == 0 ) { return; }
            for ( final BlockStateChange stateChange : blockStateChanges ) {

                final BlockState orig = stateChange.getOriginalBlock();
                final BlockState newBlock = stateChange.getNewBlock();

                // Build the action
                RecordingQueue.addToQueue( ActionFactory.createPrismRollback("prism-extinguish", orig, newBlock, event.onBehalfOf()
                        .getName(), id) );

            }
            // ActionQueue.save();
        }
    }
}