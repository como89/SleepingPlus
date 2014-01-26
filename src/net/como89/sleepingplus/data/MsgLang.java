package net.como89.sleepingplus.data;

import org.bukkit.entity.Player;

/**
 * @author como89
 * #French - Cette classe est une classe pour tous les messages du plugin.
 * #English - This is a class for all posts of the plugin.
 */
public class MsgLang {
	
	private static String [] msg;
	
	public static void initialiseMsg(String[] lignes){
		msg = lignes;
	}
	public static String getMsg(int index)
	{
		return msg[index];
	}
	
	public static String convertMsgPoint(String msg,int nbPoint)
	{
		return msg.replace("<ptFatigue>", "" + nbPoint);
	}
	
	public static String convertMsgPlayer(String msg,Player player)
	{
		return msg.replace("<player>", player.getName());
	}
}
