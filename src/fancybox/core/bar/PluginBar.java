package fancybox.core.bar;

import fancybox.lib.entry.EntryOnBar;

import java.util.ArrayList;

/**
 * a bar storing all plugin and provides entries for all plugin.
 * @author Rock Chn
 * @create 2020/12/14
 * @edit 2020/12/14
 */
public class PluginBar {
	//stored all entries of plugins
	ArrayList<EntryOnBar> entriesOnBar=new ArrayList<>();
	public void registerEntry(EntryOnBar entryOnBar){
		entriesOnBar.add(entryOnBar);
	}
}
