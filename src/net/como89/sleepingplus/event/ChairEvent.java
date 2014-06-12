package net.como89.sleepingplus.event;

import net.como89.sleepingplus.data.ManageData;
import net.como89.sleepingplus.data.SleepPlayer;

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

	private ManageData manData;
	
	public ChairEvent(ManageData manData){
		this.manData = manData;
	}
	
	@EventHandler
	public void sitEvent(PlayerChairSitEvent e){
		Player p = e.getPlayer();
		SleepPlayer sleepPlayer = manData.getSleepPlayer(p);
		sleepPlayer.sitOnChair();
	}
	
	@EventHandler
	public void unSitEvent(PlayerChairUnsitEvent e){
		Player p = e.getPlayer();
		SleepPlayer sleepPlayer = manData.getSleepPlayer(p);
		sleepPlayer.unSit();
	}
}
