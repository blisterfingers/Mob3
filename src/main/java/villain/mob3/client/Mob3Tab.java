package villain.mob3.client;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import villain.mob3.Mob3;
import villain.mob3.ModBlocks;

public class Mob3Tab extends CreativeTabs {

	public Mob3Tab(){
		super(Mob3.MODID);
		setBackgroundImageName("item_search.png");
	}
	
	@Override
	public Item getTabIconItem() {
		return Item.getItemFromBlock(ModBlocks.blockMobCow);
	}
	
	@Override
	public boolean hasSearchBar() {
		return true;
	}
	
}
