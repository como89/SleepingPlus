package net.como89.sleepingplus.data;

import java.util.ArrayList;
import java.util.Collection;

import net.como89.sleepingplus.SleepingPlus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author como89
 * #French - Cette classe manipule les données des deux ArrayList dans Data.Elle permet aussi d'effectuer certaine action comme l'application des effets de potions.
 * #English - This class handles the data from both ArrayList Data. It also allows you to perform some action such as applying the effects of potions.
 */
public class ManageData {
	
	private SleepingPlus plugin;
	
	public ManageData(SleepingPlus plugins)
	{
		plugin = plugins;
	}
	
	public SleepingPlus getPlugin(){
		return plugin;
	}
	
	public boolean isExistPlayer(Player player)
	{
		for(String world : Data.getListePlayer().keySet()){
			if(world.equals(player.getWorld().getName())){
				for(SleepPlayer sleepPlayer : Data.getListePlayer().get(world)){
					if(sleepPlayer.getPlayer().equals(player.getName()))
					{
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void addWorld(String world){
		Data.getListePlayer().put(world, new ArrayList<SleepPlayer>());
	}
	
	public void addPlayer(Player player)
	{
		if(player != null)
		{
		String worldName = player.getWorld().getName();
			if(checkIfWorldExist(worldName)){
			Data.getListePlayer().get(worldName).add(new SleepPlayer(player.getName()));
			}
		}
	}
	
	public void addEffect(Effect effect)
	{
		if(effect != null)
		{
			Data.getListeEffect().add(effect);
		}
	}
	
	public void clearEffect()
	{
		Data.getListeEffect().clear();
	}
	
	public SleepPlayer getSleepPlayer(Player player)
	{
		if(player != null){
			for(String world : Data.getListePlayer().keySet()){
				World worldPlayer = player.getWorld();
					if(world.equals(worldPlayer.getName())){
						for(SleepPlayer sleepPlayer : Data.getListePlayer().get(world)){
							if(sleepPlayer.getPlayer().equals(player.getName()))
							{
								return sleepPlayer;
							}
						}
					}
			}
		}
		return null;
	}
	
	public SleepPlayer getSleepPlayer(String playerName){
		for(String world : Data.getListePlayer().keySet()){
			for(SleepPlayer sleepPlayer : Data.getListePlayer().get(world)){
				if(sleepPlayer.getPlayer().equals(playerName)){
					return sleepPlayer;
				}
			}
		}
		return null;
	}
	
	public ArrayList<Effect> getListEffect(int tauxFatigue)
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
	
	public void reduceFatigue(SleepPlayer sleepPlayer,boolean disconnected){
		if(sleepPlayer != null){
			if(disconnected){
				sleepPlayer.removeFatigueRate(sleepPlayer.getFatigueRate());
			}
			else {
			sleepPlayer.removeFatigueRate(plugin.getNbFatigueRate());
			}
			if(sleepPlayer.getFatigueRate() == 0 && !disconnected){
				removeEffect(getListEffect(sleepPlayer.getFatigueRate()),Bukkit.getPlayer(sleepPlayer.getPlayer()));
				Bukkit.getPlayer(sleepPlayer.getPlayer()).sendMessage(ChatColor.GREEN + "[SleepingPlus] - " + MsgLang.getMsg(4));
				new FileManager(sleepPlayer).saveData();
			}
			if(plugin.isXpBar())
			{
				Bukkit.getPlayer(sleepPlayer.getPlayer()).setLevel(sleepPlayer.getFatigueRate());
			}
		}
	}
	
	public void addFatigue(SleepPlayer sleepPlayer,boolean dramatic,int nbEffect){
		if(sleepPlayer != null){
			if(dramatic){
				sleepPlayer.addFatigueRate(plugin.getNbRatWithDeath());
			}
			else{
			sleepPlayer.addFatigueRate(plugin.getNbFatigueRate());
			}
			
			int damageFatigue = plugin.getDamageFatigue();
			if(damageFatigue != -1 && nbEffect == Data.getListeEffect().size()){
				Player player = Bukkit.getPlayer(sleepPlayer.getPlayer());
				player.damage(damageFatigue);
			}
			
			if(plugin.isXpBar())
			{
				Bukkit.getPlayer(sleepPlayer.getPlayer()).setLevel(sleepPlayer.getFatigueRate());
			}
		}
	}
	
	private boolean checkIfWorldExist(String world){
		for(String worldName : Data.getListePlayer().keySet()){
			if(world.equals(worldName)){
				return true;
			}
		}
		return false;
	}
	
	public void appliesEffect(ArrayList<Effect> listEffect,Player player)
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
	
	public void removeEffect(ArrayList<Effect> listEffect,Player player){
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
