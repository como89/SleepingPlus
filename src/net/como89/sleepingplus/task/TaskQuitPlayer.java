package net.como89.sleepingplus.task;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import net.como89.sleepingplus.data.ManageData;
import net.como89.sleepingplus.data.SleepPlayer;

/**
 * @author como89
 * #French - Cette classe gère lorsque un joueur quitte le serveur. Il met à zéro le taux de fatigue si le joueur est toujours déconnecter après un certains temps.
 * #English - This class handles when a player leaves the server. It resets the fatigue rate if the player is always disconnect after a certain time.
 */
public class TaskQuitPlayer extends BukkitRunnable {

	private static boolean running = false;
	private ManageData manData;
	public TaskQuitPlayer(ManageData manData)
	{
		this.manData = manData;
	}
	
	@Override
	public void run() {
		if(!running)
		{
			running = true;
			for(OfflinePlayer player : Bukkit.getOfflinePlayers()){
				SleepPlayer sleepPlayer = manData.getSleepPlayer(player.getPlayer());
				if(sleepPlayer != null){
					if(!sleepPlayer.isLogin() && sleepPlayer.getFatigueRate() > 0){
						manData.reduceFatigue(sleepPlayer,true);
					}
				}
			}
		}
	}
}
