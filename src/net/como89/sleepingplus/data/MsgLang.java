package net.como89.sleepingplus.data;

import org.bukkit.entity.Player;

/**
 * @author como89
 * #French - Cette classe est une classe pour tous les messages du plugin. En francais et en anglais.
 * #English - This is a class for all posts of the plugin. In French and English.
 */
public class MsgLang {
	
	private static String [] french = {"Vous avez activer le plugin pour vous.","Vous avez désactiver le plugin pour vous.",
		"Vous n'avez pas la permission pour cette commande.","La config est maintenant reload.","Vous n'êtes moins fatigué maintenant.","Votre nombre de point de fatigue est à  <ptFatigue> Points.","Le nombre de point de fatigue de <player> est à <ptFatigue> Points."};
	private static String [] english = {"You activate the plugin for you.","You have disabled the plugin for you.",
		"You do not have permission for this command.","The config is now reload.","You're less tired now.","Your number of fatigue point is <ptFatigue> Points.","The number of point of fatigue for <player> is <ptFatigue> Points."};
	
	public static String getMsg(int index,String lang)
	{
		if(lang.equals("French"))
		{
			return french[index];
		}
		if(lang.equals("English"))
		{
			return english[index];
		}
		return null;
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
