package net.como89.sleepingplus.task;

import net.como89.sleepingplus.data.ManageData;
import net.como89.sleepingplus.data.SleepPlayer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author como89
 * #French - Cette classe gère lorsqu'un joueur s'assit sur une chaise. Il vérifie s'il est toujours sur la chaise avant d'enlever les effets de potions et de mettre à zéro son taux de fatigue.
 * #English - This class handles when a player sat on a chair. It checks if it is still on the chair before removing the effects of potions and to zero the rate of fatigue.
 */
public class TaskSitOnChair extends BukkitRunnable{

	private static boolean running = false;
	public TaskSitOnChair(){
		
	}
	
	@Override
	public void run() {
		if(!running)
		{
			running = true;
			for(Player player : Bukkit.getOnlinePlayers()){
				SleepPlayer sleepPlayer = ManageData.getSleepPlayer(player);
				if(sleepPlayer != null){
					if(sleepPlayer.isSitOnChair() && sleepPlayer.getFatigueRate() > 0){
						ManageData.reduceFatigue(sleepPlayer,false);
					}
				}
			}
		}
	}

}
