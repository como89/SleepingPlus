package net.como89.sleepingplus.data;

import java.util.ArrayList;
import java.util.Collection;

import net.como89.sleepingplus.SleepingPlus;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author como89
 * #French - Cette classe manipule les données des deux ArrayList dans Data.Elle permet aussi d'effectuer certaine action comme l'application des effets de potions.
 * #English - This class handles the data from both ArrayList Data. It also allows you to perform some action such as applying the effects of potions.
 */
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
	
	public static void clearEffect()
	{
		Data.getListeEffect().clear();
	}
	
	public synchronized static SleepPlayer getSleepPlayer(Player player)
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
	
	public static synchronized void reduceFatigue(SleepPlayer sleepPlayer,boolean disconnected){
		if(sleepPlayer != null){
			sleepPlayer.removeFatigueRate(1);
			if(sleepPlayer.getFatigueRate() == 0){
				removeEffect(getListEffect(sleepPlayer.getFatigueRate()),sleepPlayer.getPlayer());
				sleepPlayer.getPlayer().sendMessage(ChatColor.GREEN + "[SleepingPlus] - " + MsgLang.getMsg(4));
				new FileManager(sleepPlayer).saveData();
			}
			if(plugin.isXpBar())
			{
				sleepPlayer.getPlayer().setLevel(sleepPlayer.getFatigueRate());
			}
		}
	}
	
	public static synchronized void addFatigue(SleepPlayer sleepPlayer,boolean dramatic){
		if(sleepPlayer != null){
			if(dramatic){
				sleepPlayer.addFatigueRate(plugin.getNbRatWithDeath());
			}
			else{
			sleepPlayer.addFatigueRate(1);
			}
			ArrayList<Effect> listEffect = getListEffect(sleepPlayer.getFatigueRate());
			if(listEffect.size() > 0){
				appliesEffect(listEffect, sleepPlayer.getPlayer());
			}
			if(plugin.isXpBar())
			{
				sleepPlayer.getPlayer().setLevel(sleepPlayer.getFatigueRate());
			}
		}
	}
	
	private static void appliesEffect(ArrayList<Effect> listEffect,Player player)
	{
		Collection <PotionEffect> listeEffetOnPlayer = player.getActivePotionEffects();
		boolean PotionCorrect = true;
		PotionEffect potion = null;
		for(Effect effect : listEffect)
		{
			potion = new PotionEffect(PotionEffectType.getByName(effect.getNom()),effect.getNbTemps() * 20,effect.getLevelEffect());
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
	
	public static void removeEffect(ArrayList<Effect> listEffect,Player player){
		Collection <PotionEffect> listeEffetOnPlayer = player.getActivePotionEffects();
		boolean PotionCorrect = false;
		PotionEffect potion = null;
		for(Effect effect : listEffect)
		{
			potion = new PotionEffect(PotionEffectType.getByName(effect.getNom()),effect.getNbTemps() * 20,effect.getLevelEffect());
			for(PotionEffect potions : listeEffetOnPlayer)
			{
				if(potion.getType() == potions.getType())
				{
					PotionCorrect = true;
					break;
				}
			}
			
			if(PotionCorrect && potion != null)
			{
				player.removePotionEffect(potion.getType());
			}
		}
	}
}
