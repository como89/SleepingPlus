package net.como89.sleepingplus.event;

import net.como89.sleepingplus.SleepingPlus;
import net.como89.sleepingplus.data.ManageData;
import net.como89.sleepingplus.data.SleepPlayer;
import net.como89.sleepingplus.task.TaskSitOnChair;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.cnaude.chairs.api.PlayerChairSitEvent;
import com.cnaude.chairs.api.PlayerChairUnsitEvent;

/**
 * @author como89
 * #French - Cette classe permet d'écouter les différents évenements du plugin ChairReloaded.
 * #English - This class allows you to listen to the various events of the plugin ChairReloaded.
 * Events Listen :
 * -ChairSitEvent
 * -ChairUnSitEvent
 */
public class ChairEvent implements Listener{

	private SleepingPlus plugin;
	
	public ChairEvent(SleepingPlus plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void sitEvent(PlayerChairSitEvent e){
		Player p = e.getPlayer();
		SleepPlayer sleepPlayer = ManageData.getSleepPlayer(p);
		sleepPlayer.sitOnChair();
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new TaskSitOnChair(sleepPlayer), 20 * plugin.getTimeOnChair());
	}
	
	@EventHandler
	public void unSitEvent(PlayerChairUnsitEvent e){
		Player p = e.getPlayer();
		SleepPlayer sleepPlayer = ManageData.getSleepPlayer(p);
		sleepPlayer.unSit();
	}
}
