package net.como89.sleepingplus.event;

import java.util.Collection;

import net.como89.sleepingplus.SleepingPlus;
import net.como89.sleepingplus.data.FileManager;
import net.como89.sleepingplus.data.ManageData;
import net.como89.sleepingplus.data.SleepPlayer;
import net.como89.sleepingplus.nms.NMSCLASS;
import net.como89.sleepingplus.task.TaskQuitPlayer;
import net.como89.sleepingplus.task.TaskSleep;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
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
 * -BlockExpEvent
 * -InventoryClickEvent
 * -BlockBreakEvent
 */

public class PlayerEvent implements Listener {

	private SleepingPlus plugin;
	private NMSCLASS netminecraftclass;
	
	public PlayerEvent(SleepingPlus plugin,NMSCLASS netminecraftclass)
	{
		this.plugin = plugin;
		this.netminecraftclass = netminecraftclass;
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
		if(!sleepPlayer.isActive() && plugin.isActiveFatigue())
		{
			sleepPlayer.activer();
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
		if(plugin.isActiveBedAtDay())
		{
			netminecraftclass.outBed(player);
		}
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
					if(plugin.isActiveBedAtDay())
					{
					Location loc = event.getClickedBlock().getLocation();
					netminecraftclass.inBed(p, loc);
					Bukkit.getPluginManager().callEvent(new PlayerBedEnterEvent(p,event.getClickedBlock()));
					// bloc msg "You can sleep only at night"
					event.setCancelled(true);
					}
				}
		}
	}
	
	@EventHandler
	public void onPlayerInventoryClick(InventoryClickEvent event)
	{
		if(plugin.isXpBar())
		{
			/**
			 * This part of code, is not by me.(como89)
			 * Credit : Zelnehlun
			 */
			if(!event.isCancelled())
			{
				HumanEntity human = event.getWhoClicked();
				if(human instanceof Player)
				{
					Inventory inv = event.getInventory();
					if(inv instanceof AnvilInventory)
					{
						InventoryView view = event.getView();
						int rawSlot = event.getRawSlot();
						
						if(rawSlot == view.convertSlot(rawSlot))
						{
							if(rawSlot == 2)
							{
								ItemStack item = event.getCurrentItem();
								if(item != null)
								{
									event.setCancelled(true);
								}
							}
						}
					}
				}
			}
			/**
			 * END part of code
			 */
		}
	}
	
	@EventHandler
	public void onPlayerBreakBlock(BlockBreakEvent event)
	{
		if(plugin.isXpBar())
		{
			if(event.getExpToDrop() != 0)
			{
				event.setExpToDrop(0);
			}
		}
	}
	
	@EventHandler
	public void onDropXpEvent(BlockExpEvent event)
	{
		if(plugin.isXpBar())
		{
			if(event.getExpToDrop() != 0)
			{
				event.setExpToDrop(0);
			}
		}
	}
	
	@EventHandler
	public void onPlayerEnchantItem(PrepareItemEnchantEvent event)
	{
		if(plugin.isXpBar())
		{
			event.setCancelled(true);
		}
	}
}
