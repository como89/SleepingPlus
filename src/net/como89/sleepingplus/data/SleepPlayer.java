package net.como89.sleepingplus.data;

import org.bukkit.entity.Player;

public class SleepPlayer {
	
	private Player player;
	private long timeNoSleep;
	private int fatigueRate;
	private long logTime;
	private boolean inBed;
	private boolean active;
	private boolean login;
	
	public SleepPlayer(Player player)
	{
		this.player = player;
		timeNoSleep = 0;
		logTime = 0;
		inBed = false;
		active = false;
		login = false;
		fatigueRate = 0;
	}
	
	public SleepPlayer(Player player,long timeNoSleep,int fatigueRate,boolean active)
	{
		this.player = player;
		this.timeNoSleep = timeNoSleep;
		this.logTime = 0;
		this.inBed = false;
		this.active = active;
		this.login = false;
		this.fatigueRate = fatigueRate;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public boolean isLogin()
	{
		return login;
	}
	
	public int getFatigueRate()
	{
		return fatigueRate;
	}
	
	public boolean isActive()
	{
		return active;
	}
	
	public void activer()
	{
		active = true;
	}
	
	public void desactiver()
	{
		active = false;
	}
	
	public void login()
	{
		login = true;
	}
	
	public void logout()
	{
		login = false;
	}
	
	public long getTimeNoSleep()
	{
		return timeNoSleep;
	}
	
	void ajustTimeNoSleep(long time)
	{
		timeNoSleep = time;
	}
	
	public void outBed()
	{
		inBed = false;
	}
	
	public void inBed()
	{
		inBed = true;
	}
	
	public boolean isInBed()
	{
		return inBed;
	}
	
	public long getLogTime()
	{
		return logTime;
	}
	
	public void logTimeNow()
	{
		logTime = System.currentTimeMillis();
	}
	
	void ajustFatigueRate(int fatigueRate)
	{
		if(this.fatigueRate <= fatigueRate)
		{
		this.fatigueRate = fatigueRate;
		}
	}
	
	void zeroFatigueRate()
	{
		this.fatigueRate = 0;
	}
}
