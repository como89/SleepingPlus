package net.como89.sleepingplus.event;

import java.util.Collection;

import net.como89.sleepingplus.SleepingPlus;
import net.como89.sleepingplus.data.FileManager;
import net.como89.sleepingplus.data.ManageData;
import net.como89.sleepingplus.data.SleepPlayer;
import net.como89.sleepingplus.task.TaskQuitPlayer;
import net.como89.sleepingplus.task.TaskSleep;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;

public class PlayerEvent implements Listener {

	private SleepingPlus plugin;
	
	public PlayerEvent(SleepingPlus plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		FileManager fileMan = new FileManager(player);
		if(fileMan.checkFile())
		{
			fileMan.loadData();
		}
		else if(!ManageData.isExistPlayer(player))
		{
			ManageData.addPlayer(player);
		}
		SleepPlayer sleepPlayer = ManageData.getSleepPlayer(player);
		if(!sleepPlayer.isLogin())
		{
			Collection<PotionEffect> listEffect = sleepPlayer.getPlayer().getActivePotionEffects();
			for(PotionEffect potionE : listEffect)
			{
				sleepPlayer.getPlayer().removePotionEffect(potionE.getType());
			}
			sleepPlayer.logTimeNow();
		}
		else
		{
			sleepPlayer.logout();
		}
	}
	
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		SleepPlayer sleepPlayer = ManageData.getSleepPlayer(player);
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new TaskQuitPlayer(sleepPlayer), 20 * plugin.getTimeExitServer());
		new FileManager(sleepPlayer).saveData();
	}
	
	@EventHandler
	public void onPlayerEnterBed(PlayerBedEnterEvent event)
	{
		Player player = event.getPlayer();
		SleepPlayer sleepPlayer = ManageData.getSleepPlayer(player);
		sleepPlayer.inBed();
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new TaskSleep(sleepPlayer),20 * plugin.getTimeInBed());
	}
	
	@EventHandler
	public void onPlayerLeaveBed(PlayerBedLeaveEvent event)
	{
		Player player = event.getPlayer();
		SleepPlayer sleepPlayer = ManageData.getSleepPlayer(player);
		sleepPlayer.outBed();
	}
	
}
