package net.como89.sleepingplus.data;

/**
 * @author como89
 * #French - Cette classe est une classe pour tous les messages du plugin. En francais et en anglais.
 * #English - This is a class for all posts of the plugin. In French and English.
 */
public class MsgLang {
	
	private static String [] french = {"Vous avez activer le plugin pour vous.","Vous avez désactiver le plugin pour vous.",
		"Vous n'avez pas la permission pour cette commande.","La config est maintenant reload.","Vous n'êtes moins fatigué maintenant."};
	private static String [] english = {"You activate the plugin for you.","You have disabled the plugin for you.",
		"You do not have permission for this command.","The config is now reload.","You're less tired now."};
	
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
}
