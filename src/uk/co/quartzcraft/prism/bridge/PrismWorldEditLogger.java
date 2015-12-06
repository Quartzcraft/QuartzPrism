package uk.co.quartzcraft.prism.bridge;

import uk.co.quartzcraft.prism.Prism;
import uk.co.quartzcraft.prism.actionlibs.ActionFactory;
import uk.co.quartzcraft.prism.actionlibs.RecordingQueue;

import org.bukkit.Location;
import org.bukkit.World;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.extension.platform.Actor;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.extent.logging.AbstractLoggingExtent;

public class PrismWorldEditLogger extends AbstractLoggingExtent {
	private final Actor player;
	private final World world;

	public PrismWorldEditLogger(Actor player, Extent extent, World world) {
		super(extent);
		this.player = player;
		this.world = world;
	}

	@Override
	protected void onBlockChange(Vector pt, BaseBlock newBlock) {
		if(!Prism.config.getBoolean("prism.tracking.world-edit")) 
			return;

		BaseBlock oldBlock = getBlock(pt);
		
		Location loc = new Location(world, pt.getBlockX(), pt.getBlockY(), pt.getBlockZ());

		RecordingQueue.addToQueue(ActionFactory.createBlockChange("world-edit", loc,
				oldBlock.getId(), 
				(byte) oldBlock.getData(), 
				newBlock.getId(), 
				(byte) newBlock.getData(), player.getName()));
	}
}