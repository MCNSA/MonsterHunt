package com.matejdro.bukkit.monsterhunt.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.matejdro.bukkit.monsterhunt.MonsterHunt;
import com.matejdro.bukkit.monsterhunt.Settings;
import com.matejdro.bukkit.monsterhunt.Util;


public class HuntHelpCommand extends BaseMHCommand {
	
	public HuntHelpCommand()
	{
		permission = "help";
		desc = "List all possible commands";
		needPlayer = false;
	}


	public void run(CommandSender sender, String[] args) {		
		int page = 1;
		if (args.length > 0 && Util.isInteger(args[0])) page = Integer.parseInt(args[0]);
		List<String> komandes = new ArrayList<String>();

		for (Entry<String, BaseMHCommand> e : MonsterHunt.commands.entrySet())
		{
			if (Util.hasPermission(sender, "monsterhunt.command." + e.getValue().permission))
				komandes.add(Settings.globals.getCommandDescription(e.getKey(), "hunt", e.getValue().desc));
		}  		
		String[] komande = komandes.toArray(new String[0]);
		Arrays.sort(komande);
		
		int maxpage = (int) Math.ceil((double) komande.length / (sender instanceof Player ? 15.0 : 30.0));
		
		if (page > maxpage)
			page = maxpage;
		
		Util.Message("List of all commands:", sender);
		Util.Message("&8Page " + String.valueOf(page) + " of " + String.valueOf(maxpage), sender);

		for (int i = (page - 1) * 15; i < page * 15; i++)
		{
			if (komande.length < i + 1 || i < 0) break;	
			Util.Message(komande[i], sender);
		}   		
	}
	

}
