package com.massivecraft.factions.cmd.arg;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.entity.MPlayerColl;
import com.massivecraft.massivecore.CaseInsensitiveComparator;
import com.massivecraft.massivecore.MassiveCore;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.cmd.arg.ARAbstract;
import com.massivecraft.massivecore.util.IdUtil;

public class ARFaction extends ARAbstract<Faction>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static ARFaction i = new ARFaction();
	public static ARFaction get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public Faction read(String str, CommandSender sender) throws MassiveException
	{
		Faction ret;
		
		// Nothing/Remove targets Wilderness
		if (MassiveCore.NOTHING_REMOVE.contains(str))
		{
			return FactionColl.get().getNone();
		}
		
		// Faction Id Exact
		if (FactionColl.get().containsId(str))
		{
			ret = FactionColl.get().get(str);
			if (ret != null) return ret;
		}
		
		// Faction Name Exact
		ret = FactionColl.get().getByName(str);
		if (ret != null) return ret;
		
		// MPlayer Name Exact
		String id = IdUtil.getId(str);
		MPlayer mplayer = MPlayerColl.get().get(id, false);
		if (mplayer != null)
		{
			return mplayer.getFaction();
		}
		
		throw new MassiveException().addMsg("<b>No faction or player matching \"<p>%s<b>\".", str);
	}

	@Override
	public Collection<String> getTabList(CommandSender sender, String arg)
	{
		Set<String> ret = new TreeSet<String>(CaseInsensitiveComparator.get());
		
		for (Faction faction : FactionColl.get().getAll())
		{
			ret.add(ChatColor.stripColor(faction.getName()));
		}
		
		return ret;
	}
	
}