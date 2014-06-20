package net.como89.sleepingplus.task;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.como89.sleepingplus.data.Effect;
import net.como89.sleepingplus.data.ManageData;
import net.como89.sleepingplus.data.SleepPlayer;


/**
 * @author como89
 * #French - Cette classe est appeller à toutes les secondes pour changer le taux de fatigue de tous les joueurs sur le serveur.
 * #English - This class is call every second to change the fatigue rate of all players on the server.
 */
public class TaskTimeNoSleep extends BukkitRunnable {

	private static boolean running = false;
	private ManageData manData;
	private int second;
	public TaskTimeNoSleep(ManageData manData)
	{
		this.manData = manData;
		second = 0;
	}

	@Override
	public void run() {
		if(!running)
		{
			running = true;
			for(Player player : Bukkit.getOnlinePlayers()){
				SleepPlayer sleepPlayer = manData.getSleepPlayer(player);
				if(sleepPlayer != null){
					ArrayList<Effect> listEffect = manData.getListEffect(sleepPlayer.getFatigueRate());
					if(listEffect.size() > 0){
						manData.appliesEffect(listEffect, Bukkit.getPlayer(sleepPlayer.getPlayer()));
					}
					if(sleepPlayer.isActive() && second >= manData.getPlugin().getTimeNoSleep()){
						manData.addFatigue(sleepPlayer, false,listEffect.size());
					}
				}
			}
			if(second >= manData.getPlugin().getTimeNoSleep()){
				second = 0;
			}
			else{
			second++;
			}
			running = false;
		}
	}
	
	
}
