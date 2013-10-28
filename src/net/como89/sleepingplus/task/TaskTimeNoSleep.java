package net.como89.sleepingplus.task;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.como89.sleepingplus.data.Effect;
import net.como89.sleepingplus.data.ManageData;
import net.como89.sleepingplus.data.SleepPlayer;

public class TaskTimeNoSleep extends BukkitRunnable {

	public static boolean running = false;
	
	public TaskTimeNoSleep()
	{
	}

	@Override
	public void run() {
		if(!running)
		{
			running = true;
			for(Player player : Bukkit.getOnlinePlayers())
			{
				SleepPlayer sleepPlayer = ManageData.getSleepPlayer(player);
				if(sleepPlayer != null)
				{
					if(sleepPlayer.isActive())
					{
					ManageData.actualiseTime(sleepPlayer, System.currentTimeMillis());
					System.out.println(sleepPlayer.getPlayer().getName() + ":" +sleepPlayer.getFatigueRate());
					System.out.println(sleepPlayer.getTimeNoSleep());
					ArrayList<Effect> listEffect = ManageData.getListEffect(sleepPlayer.getFatigueRate());
					ManageData.appliesEffect(listEffect, sleepPlayer.getPlayer());
					}
				}
			}
			running = false;
		}
	}
	
	
}
