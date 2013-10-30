package net.como89.sleepingplus;

import net.como89.sleepingplus.data.ManageData;
import net.como89.sleepingplus.data.MsgLang;
import net.como89.sleepingplus.data.SleepPlayer;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author como89
 * #French - Cette classe est la gestion des commandes du plugin.
 * #English - This class is the command management of the plugin.
 */
public class Commands implements CommandExecutor{

	private SleepingPlus plugin;
	
	public Commands(SleepingPlus plugin)
	{
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender cmd, Command cmds, String label,
			String[] args) {
		if(!(cmd instanceof Player))
		{
			cmd.sendMessage("Your not a player!");
			return true;
		}
		
		Player player = (Player) cmd;
		
		if(label.equalsIgnoreCase("sp"))
		{
			if(args.length == 1)
			{
				if(args[0].equalsIgnoreCase("active"))
				{
					SleepPlayer sleepPlayer = ManageData.getSleepPlayer(player);
					if(!player.isOp() && (plugin.isPermit() && !SleepingPlus.perm.has(player,"sleepingplus.com.active")))
					{
						player.sendMessage(ChatColor.RED + "[SleepingPlus] - " + MsgLang.getMsg(2, SleepingPlus.getLangage()));
						return true;
					}
					if(!sleepPlayer.isActive())
					{
					sleepPlayer.activer();
					player.sendMessage(ChatColor.GREEN + "[SleepingPlus] - " + MsgLang.getMsg(0, SleepingPlus.getLangage()));
					return true;
					}
					sleepPlayer.desactiver();
					player.sendMessage(ChatColor.GREEN + "[SleepingPlus] - " + MsgLang.getMsg(1, SleepingPlus.getLangage()));
					return true;
				}
				if(args[0].equalsIgnoreCase("reload"))
				{
					if(!player.isOp() && (plugin.isPermit() && !SleepingPlus.perm.has(player,"sleepingplus.com.reload")))
					{
						player.sendMessage(ChatColor.RED + "[SleepingPlus] - " + MsgLang.getMsg(2, SleepingPlus.getLangage()));
						return true;
					}
					plugin.loadConfig();
					player.sendMessage(ChatColor.GREEN + "[SleepingPlus] - " + MsgLang.getMsg(3, SleepingPlus.getLangage()));
					return true;
				}
			}
		}
		return false;
	}

	
	
}
