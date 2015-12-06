package uk.co.quartzcraft.prism.commands;

import uk.co.quartzcraft.prism.Prism;
import uk.co.quartzcraft.prism.commandlibs.CallInfo;
import uk.co.quartzcraft.prism.commandlibs.Executor;
import uk.co.quartzcraft.prism.commandlibs.SubHandler;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class WhatCommand extends Executor {

    /**
     * 
     * @param prism
     */
    public WhatCommand(Prism prism) {
        super( prism, "command", "prism" );
        setupCommands();
    }

    /**
     *
     */
    private void setupCommands() {
        /**
         * /what
         */
        addSub( "what", "prism.what" ).setHandler( new SubHandler() {
            @Override
            public void handle(CallInfo call) {
                final ItemStack item = call.getPlayer().getItemInHand();

                call.getPlayer().sendMessage( Prism.messenger.playerHeaderMsg( "Item Profile:" ) );

                String line1 = ChatColor.WHITE + "Name: " + ChatColor.DARK_AQUA
                        + item.getType().toString().toLowerCase();
                line1 += ChatColor.WHITE + " Prism Alias: " + ChatColor.DARK_AQUA
                        + Prism.getItems().getAlias( item.getTypeId(), item.getDurability() );
                line1 += ChatColor.WHITE + " ID: " + ChatColor.DARK_AQUA + item.getTypeId() + ":"
                        + item.getDurability();

                call.getPlayer().sendMessage( Prism.messenger.playerMsg( line1 ) );
                call.getPlayer().sendMessage(
                        Prism.messenger.playerMsg( ChatColor.WHITE + "Full Display Name: " + ChatColor.DARK_AQUA
                                + me.botsko.elixr.ItemUtils.getItemFullNiceName( item, Prism.getItems() ) ) );

            }

            @Override
            public List<String> handleComplete(CallInfo call) {
                return null;
            }
        } );
    }
}
