package aam.commands;

import aam.common.soul.Trait;
import aam.network.packages.AlchemicalDispatcher;
import aam.utils.Logger;
import aam.utils.PlayerDataHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;

public class CommandSoulLevel extends CommandBase
{

    /**
     * Return the required permission level for this command.
     */
    @Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    @Override
    public String getCommandName()
    {
        return StatCollector.translateToLocal("aam.command.soullevel.name");
    }

    @Override
    public String getCommandUsage(ICommandSender p)
    {
        return StatCollector.translateToLocal("aam.command.soullevel.usage");
    }

    @Override
    public void processCommand(ICommandSender s, String[] args)
    {
        if (s instanceof EntityPlayer)
        {
            EntityPlayer p = (EntityPlayer) s;
            PlayerDataHandler ph = PlayerDataHandler.get(p);
            if (args.length == 0)
            {
                Logger.chat(p,"Usage: " + StatCollector.translateToLocal("aam.command.soullevel.usage"));
                return;
            }
            if (args[0].toLowerCase().equals("add"))
            {
                if (args.length == 2)
                {
                    float dlevel = Float.parseFloat(args[1]);
                    ph.addTraitBase(Trait.Level, dlevel);
                    ph.addTraitBase(Trait.Soul, dlevel);
                } else
                    ph.setTraitBase(Trait.Level, 1 + ph.getTraitBase(Trait.Level));
            }
            if (args[0].toLowerCase().equals("set"))
            {
                if (args.length == 2)
                {
                    int l = Integer.parseInt(args[1]);
                    ph.setTraitBase(Trait.Level, l);
                    ph.setTraitBase(Trait.Soul, l * 100);
                }
            }
            if (args[0].toLowerCase().equals("resetsoul"))
            {
                ph.replenishSoul();
            }
            if (ph.getTraitBase(Trait.Level) <= 0)
            {
                ph.setTraitBase(Trait.Level, 1);
            }
            AlchemicalDispatcher.syncPlayer(p);
        }
    }
}
