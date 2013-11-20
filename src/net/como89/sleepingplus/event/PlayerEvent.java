package net.como89.sleepingplus.event;

import java.util.Collection;

import net.como89.sleepingplus.SleepingPlus;
import net.como89.sleepingplus.data.FileManager;
import net.como89.sleepingplus.data.ManageData;
import net.como89.sleepingplus.data.SleepPlayer;
import net.como89.sleepingplus.task.TaskQuitPlayer;
import net.como89.sleepingplus.task.TaskSleep;
import net.minecraft.server.v1_6_R3.Packet17EntityLocationAction;
import net.minecraft.server.v1_6_R3.Packet18ArmAnimation;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_6_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;

/**
 * @author como89
 * #French - Cette classe permet d'écouter les différents évenements du joueur.
 * #English - This class allows you to listen to the various events of the player.
 * Events Listen :
 * -PlayerJoinEvent
 * -PlayerQuitEvent
 * -PlayerBedEnterEvent
 * -PlayerBedLeaveEvent
 * -PlayerDeathEvent
 * -PlayerInteractEvent
 * -PrepareItemEnchantEvent
 */

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
		Packet18ArmAnimation pa = new Packet18ArmAnimation();
		pa.a = player.getEntityId();
		pa.b = 3;
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(pa);
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		Player player = event.getEntity();
		SleepPlayer sleepPlayer = ManageData.getSleepPlayer(player);
		ManageData.addDramaticFatigue(sleepPlayer);
	}
	
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event)
	{
		Player p = event.getPlayer();
		if(event.getClickedBlock() != null)
		{
			if(event.getClickedBlock().getType() == Material.BED_BLOCK)
			{
				Location location = event.getClickedBlock().getLocation();
				((CraftPlayer)p).getHandle().playerConnection.sendPacket(new Packet17EntityLocationAction(((CraftPlayer)p).getHandle(),0,location.getBlockX(),location.getBlockY(),location.getBlockZ()));
				Bukkit.getPluginManager().callEvent(new PlayerBedEnterEvent(p,event.getClickedBlock()));
			}
		}
	}
	
	public void onPlayerEnchantItem(PrepareItemEnchantEvent event)
	{
		if(plugin.isXpBar())
		{
			event.setCancelled(true);
		}
	}
}
