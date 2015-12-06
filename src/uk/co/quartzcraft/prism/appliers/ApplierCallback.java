package uk.co.quartzcraft.prism.appliers;

import org.bukkit.command.CommandSender;

public interface ApplierCallback {
    public void handle(CommandSender sender, ApplierResult result);
}
