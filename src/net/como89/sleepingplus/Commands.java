package net.como89.sleepingplus;

import net.como89.sleepingplus.data.ManageData;
import net.como89.sleepingplus.data.MsgLang;
import net.como89.sleepingplus.data.SleepPlayer;

import org.bukkit.Bukkit;
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
	private ManageData manData;
	
	public Commands(SleepingPlus plugin,ManageData manData)
	{
		this.plugin = plugin;
		this.manData = manData;
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
		
		if(label.equalsIgnoreCase("spp"))
		{
			if(args.length == 1)
			{
				if(args[0].equalsIgnoreCase("toggle"))
				{
					SleepPlayer sleepPlayer = manData.getSleepPlayer(player);
					if(plugin.isPermit() && (!player.isOp() && !SleepingPlus.perm.has(player,"sleepingplus.com.toggle")))
					{
						player.sendMessage(ChatColor.RED + "[SleepingPlus] - " + MsgLang.getMsg(2));
						return true;
					}
					if(!sleepPlayer.isActive())
					{
					sleepPlayer.activer();
					player.sendMessage(ChatColor.GREEN + "[SleepingPlus] - " + MsgLang.getMsg(0));
					return true;
					}
					sleepPlayer.desactiver();
					player.sendMessage(ChatColor.GREEN + "[SleepingPlus] - " + MsgLang.getMsg(1));
					return true;
				}
				if(args[0].equalsIgnoreCase("reload"))
				{
					if(plugin.isPermit() && (!player.isOp() && !SleepingPlus.perm.has(player,"sleepingplus.com.reload")))
					{
						player.sendMessage(ChatColor.RED + "[SleepingPlus] - " + MsgLang.getMsg(2));
						return true;
					}
					plugin.loadConfig();
					player.sendMessage(ChatColor.GREEN + "[SleepingPlus] - " + MsgLang.getMsg(3));
					return true;
				}
			}
			if(args.length <= 2 && args.length > 0){
				if(args[0].equalsIgnoreCase("infoS"))
				{
					if(plugin.isPermit() && (!player.isOp() && !SleepingPlus.perm.has(player,"sleepingplus.com.infos")))
					{
						player.sendMessage(ChatColor.RED + "[SleepingPlus] - " + MsgLang.getMsg(2));
						return true;
					}
					if(args.length == 1)
					{
						SleepPlayer sleepPlayer = manData.getSleepPlayer(player);
						if(sleepPlayer != null)
						{
							player.sendMessage(ChatColor.GRAY + MsgLang.convertMsgPoint(MsgLang.getMsg(5),sleepPlayer.getFatigueRate()));
						}
						return true;
					}
					if(args.length == 2)
					{
						Player p = Bukkit.getPlayer(args[1]);
						SleepPlayer sleepPlayer = manData.getSleepPlayer(p);
						if(sleepPlayer != null)
						{
							player.sendMessage(ChatColor.GRAY + MsgLang.convertMsgPlayer(MsgLang.convertMsgPoint(MsgLang.getMsg(6),sleepPlayer.getFatigueRate()),p));
						}
						return true;
					}
				}
			}
		}
		return false;
	}

	
	
}
