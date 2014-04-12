package net.como89.sleepingplus.task;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.como89.sleepingplus.data.ManageData;
import net.como89.sleepingplus.data.SleepPlayer;

/**
 * @author como89
 * #French - Cette classe gère lorsqu'un joueur entre dans un lit. Il vérifie s'il est toujours dans le lit avant d'enlever les effets de potions et de mettre à zéro son taux de fatigue.
 * #English - This class handles when a player enters a bed. It checks if it is still in the bed before removing the effects of potions and to zero the rate of fatigue.
 */
public class TaskSleep extends BukkitRunnable {
	
	private static boolean running = false;
	public TaskSleep()
	{
		
	}
	
	@Override
	public void run() {
		if(!running)
		{
			running = true;
			for(Player player : Bukkit.getOnlinePlayers()){
				SleepPlayer sleepPlayer = ManageData.getSleepPlayer(player);
				if(sleepPlayer != null){
					if(sleepPlayer.isInBed() && sleepPlayer.getFatigueRate() > 0)
					{
						ManageData.reduceFatigue(sleepPlayer,false);
					}
				}
			}
		}
	}

}
