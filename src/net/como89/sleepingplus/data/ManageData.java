package net.como89.sleepingplus.data;

import java.util.ArrayList;
import java.util.Collection;

import net.como89.sleepingplus.SleepingPlus;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ManageData {
	
	private static SleepingPlus plugin;
	
	public ManageData(SleepingPlus plugins)
	{
		plugin = plugins;
	}
	
	public static boolean isExistPlayer(Player player)
	{
		for(SleepPlayer sleepPlayer : Data.getListePlayer())
		{
			if(sleepPlayer.getPlayer().equals(player))
			{
				return true;
			}
		}
		return false;
	}
	
	public static void addPlayer(Player player)
	{
		if(player != null)
		{
		Data.getListePlayer().add(new SleepPlayer(player));
		}
	}
	
	public static void removePlayer(SleepPlayer player)
	{	
		if(player != null)
		{
		Data.getListePlayer().remove(player);
		}
	}
	
	public static void addEffect(Effect effect)
	{
		if(effect != null)
		{
			Data.getListeEffect().add(effect);
		}
	}
	
	public static SleepPlayer getSleepPlayer(Player player)
	{
		for(SleepPlayer sleepPlayer : Data.getListePlayer())
		{
			if(sleepPlayer.getPlayer().equals(player))
			{
				return sleepPlayer;
			}
		}
		return null;
	}
	
	private static int calculTauxFatigue(long timeNoSleep)
	{
		if(timeNoSleep != 0)
		{
		long timeSecond = timeNoSleep / 1000;
		return (int) timeSecond / plugin.getTimeNoSleep();
		}
		else
		{
			return 0;
		}
	}
	
	public static ArrayList<Effect> getListEffect(int tauxFatigue)
	{
		ArrayList<Effect> listEffect = new ArrayList<Effect>();
		for(Effect effect : Data.getListeEffect())
		{
			if(effect.getNbFatigueRate() <= tauxFatigue)
			{
				listEffect.add(effect);
			}
		}
		return listEffect;
	}
	
	public static synchronized void actualiseTime(SleepPlayer sleepPlayer,long time)
	{
		if(time > 0 && sleepPlayer != null)
		{
		long diff = time - sleepPlayer.getLogTime();
		sleepPlayer.ajustTimeNoSleep(diff);
		sleepPlayer.ajustFatigueRate(calculTauxFatigue(sleepPlayer.getTimeNoSleep()));
		}
		else if(sleepPlayer != null)
		{
			sleepPlayer.ajustTimeNoSleep(0);
			sleepPlayer.zeroFatigueRate();
			sleepPlayer.logTimeNow();
		}
	}
	
	public static void appliesEffect(ArrayList<Effect> listEffect,Player player)
	{
		Collection <PotionEffect> listeEffetOnPlayer = player.getActivePotionEffects();
		boolean PotionCorrect = true;
		PotionEffect potion = null;
		for(Effect effect : listEffect)
		{
			potion = new PotionEffect(PotionEffectType.getByName(effect.getNom()),effect.getNbTemps() * 20,2);
			for(PotionEffect potions : listeEffetOnPlayer)
			{
				if(potion.getType() == potions.getType())
				{
					PotionCorrect = false;
					break;
				}
			}
			if(PotionCorrect && potion != null)
			{
				player.addPotionEffect(potion);
			}
		}
	}
}
