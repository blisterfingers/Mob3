package villain.mob3;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import villain.mob3.blocks.mob.BlockMobBase;
import villain.mob3.blocks.mob.BlockMobType;

public final class ModBlocks {
	
	public static BlockMobBase blockMobCow;
	public static BlockMobBase blockMobMooshroom;
	public static BlockMobBase blockMobSheep;
	public static BlockMobBase blockMobPig;
	public static BlockMobBase blockMobCreeper;
	public static BlockMobBase blockMobEnderman;

	public static void init(){
		blockMobCow = new BlockMobBase(BlockMobType.COW);
		blockMobMooshroom = new BlockMobBase(BlockMobType.MOOSHROOM);
		blockMobSheep = new BlockMobBase(BlockMobType.SHEEP);
		blockMobPig = new BlockMobBase(BlockMobType.PIG);
		blockMobCreeper = new BlockMobBase(BlockMobType.CREEPER);
		blockMobEnderman = new BlockMobBase(BlockMobType.ENDERMAN);
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
