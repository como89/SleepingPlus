package net.como89.sleepingplus.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.bukkit.entity.Player;

import net.como89.sleepingplus.task.TaskTimeNoSleep;

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
	}
	
	public FileManager(Player player)
	{
		this.player = player;
	}
	
	public void saveData()
	{
		Encrypter enc = new Encrypter("testing89");
		String donnees = "";
			try {
				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("plugins/SleepingPlus/DataPlayer/"+sleepPlayer.getPlayer().getName()+".dat")));
				donnees = sleepPlayer.getTimeNoSleep()+":"+sleepPlayer.getFatigueRate()+":"+sleepPlayer.isActive();		
				donnees = enc.encrypt(donnees);
				out.write(donnees);
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public void loadData()
	{
		Encrypter enc = new Encrypter("testing89");
		File file = new File("plugins/SleepingPlus/DataPlayer/"+player.getName()+".dat");
		try
		{
			TaskTimeNoSleep.running = true;
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
			SleepPlayer sleep = new SleepPlayer(player,Long.parseLong(lignes[0]),Integer.parseInt(lignes[1]),Boolean.parseBoolean(lignes[2]));
			Data.getListePlayer().add(sleep);
			TaskTimeNoSleep.running = false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean checkFile()
	{
		File file = new File("plugins/SleepingPlus/DataPlayer/"+player.getName()+".dat");
		return file.exists();
	}
}
