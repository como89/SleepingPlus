package net.como89.sleepingplus.task;

import org.bukkit.scheduler.BukkitRunnable;

import net.como89.sleepingplus.data.ManageData;
import net.como89.sleepingplus.data.SleepPlayer;

/**
 * @author como89
 * #French - Cette classe gère lorsque un joueur quitte le serveur. Il met à zéro le taux de fatigue si le joueur est toujours déconnecter après un certains temps.
 * #English - This class handles when a player leaves the server. It resets the fatigue rate if the player is always disconnect after a certain time.
 */
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
