package net.como89.sleepingplus.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


/**
 * @author como89
 * #French - Cette classe enregistre les données et utilise la classe Encrypter pour encrypter les données.
 * #English - This class stores the data and uses the Encrypter class to encrypt the data.
 */
public class FileManager {

	private SleepPlayer sleepPlayer;
	private Player player;
	
	public FileManager(SleepPlayer sleepPlayer)
	{
		this.sleepPlayer = sleepPlayer;
		this.player = Bukkit.getPlayer(sleepPlayer.getPlayer());
	}
	
	public FileManager(Player player)
	{
		this.player = player;
	}
	
	public void saveData()
	{
		Encrypter enc = new Encrypter("Key here");
		String donnees = "";
			try {
				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("plugins/SleepingPlus/DataPlayer/" + player.getWorld().getName() + "/" + sleepPlayer.getPlayer() +".dat")));
				donnees = sleepPlayer.getFatigueRate()+":"+sleepPlayer.isActive();		
				donnees = enc.encrypt(donnees);
				out.write(donnees);
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public void loadData()
	{
		Encrypter enc = new Encrypter("Key here");
		File file = new File("plugins/SleepingPlus/DataPlayer/" + player.getWorld().getName() + "/"+player.getName()+".dat");
		try
		{
			BufferedReader in
			   = new BufferedReader(new FileReader(file));
			String ligne = "";
			String donnees = "";
			while((ligne = in.readLine())!= null)
			{
				donnees += ligne;
			}
			in.close();
			donnees = enc.decrypt(donnees);
			String [] lignes = donnees.split(":");
			SleepPlayer sleep = null;
			if(lignes.length == 3){
				sleep = new SleepPlayer(player.getName(),Integer.parseInt(lignes[1]),Boolean.parseBoolean(lignes[2]));
			}
			else if(lignes.length == 2){
				sleep = new SleepPlayer(player.getName(),Integer.parseInt(lignes[0]),Boolean.parseBoolean(lignes[1]));
			}
			Data.getListePlayer().get(player.getWorld().getName()).add(sleep);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean checkFile()
	{
		File file = new File("plugins/SleepingPlus/DataPlayer/" + player.getWorld().getName() + "/" + player.getName()+".dat");
		return file.exists();
	}
}
