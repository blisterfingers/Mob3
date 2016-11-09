package villain.mob3;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import villain.mob3.blocks.mob.BlockMobBase;

public final class ModBlocks {
	
	public static BlockMobBase blockMobCow;
	public static BlockMobBase blockMobMooshroom;
	public static BlockMobBase blockMobSheep;
	public static BlockMobBase blockMobPig;
	public static BlockMobBase blockMobCreeper;
	public static BlockMobBase blockMobEnderman;

	public static void init(){
		blockMobCow = new BlockMobBase(BlockMobBase.TYPE_COW);
		blockMobMooshroom = new BlockMobBase(BlockMobBase.TYPE_MOOSHROOM);
		blockMobSheep = new BlockMobBase(BlockMobBase.TYPE_SHEEP);
		blockMobPig = new BlockMobBase(BlockMobBase.TYPE_PIG);
		blockMobCreeper = new BlockMobBase(BlockMobBase.TYPE_CREEPER);
		blockMobEnderman = new BlockMobBase(BlockMobBase.TYPE_ENDERMAN);
	}
	
	@SideOnly(Side.CLIENT)
	public static void initModels(){
		blockMobCow.initModel();
		blockMobMooshroom.initModel();
		blockMobSheep.initModel();
		blockMobPig.initModel();
		blockMobCreeper.initModel();
		blockMobEnderman.initModel();
	}
	
	
	@SideOnly(Side.CLIENT)
	public static void initItemModels(){
		
	}
}
