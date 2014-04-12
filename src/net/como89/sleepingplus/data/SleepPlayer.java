package net.como89.sleepingplus.data;

import org.bukkit.entity.Player;

/**
 * @author como89
 * #French - Cette classe est la gestion des données concernant un joueur.
 * #English - This class is the data management of the player.
 */
public class SleepPlayer {
	
	private Player player;
	private int fatigueRate;
	private boolean inBed;
	private boolean active;
	private boolean login;
	private boolean sitDown;
	
	public SleepPlayer(Player player)
	{
		this.player = player;
		inBed = false;
		active = false;
		login = false;
		sitDown = false;
		fatigueRate = 0;
	}
	
	public SleepPlayer(Player player,int fatigueRate,boolean active)
	{
		this.player = player;
		this.inBed = false;
		this.active = active;
		this.login = false;
		this.sitDown = false;
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
	
	public void sitOnChair(){
		sitDown = true;
	}
	
	public void unSit(){
		sitDown = false;
	}
	
	public boolean isSitOnChair(){
		return sitDown;
	}
	
	void addFatigueRate(int fatigueRate){
		this.fatigueRate += fatigueRate;
	}
	
	void removeFatigueRate(int fatigueRate){
		this.fatigueRate -= fatigueRate;
	}
}
