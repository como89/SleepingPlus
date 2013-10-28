package net.como89.sleepingplus.task;

import org.bukkit.scheduler.BukkitRunnable;

import net.como89.sleepingplus.data.ManageData;
import net.como89.sleepingplus.data.SleepPlayer;

public class TaskQuitPlayer extends BukkitRunnable {

	private SleepPlayer sleepPlayer;
	
	public TaskQuitPlayer(SleepPlayer sleepPlayer)
	{
		this.sleepPlayer = sleepPlayer;
	}
	
	@Override
	public void run() {
		if(sleepPlayer.isLogin() && sleepPlayer.getTimeNoSleep() > 0)
		{
			ManageData.actualiseTime(sleepPlayer, 0);
			sleepPlayer.logout();
		}
		else
		{
			sleepPlayer.login();
		}
	}
}
