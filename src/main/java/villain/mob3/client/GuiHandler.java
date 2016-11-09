package villain.mob3.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import villain.mob3.blocks.mob.BlockMobContainer;
import villain.mob3.blocks.mob.BlockMobGUI;
import villain.mob3.blocks.mob.BlockMobTileEntity;

public class GuiHandler implements IGuiHandler {

	public static final int GUI_BLOCKMOB = 0;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(pos);
		
		switch(ID){
			case GUI_BLOCKMOB:
				return new BlockMobContainer(player.inventory, (BlockMobTileEntity)te);				
		}
		
		return null;
	}	
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(pos);
		
		switch(ID){
			case GUI_BLOCKMOB:
				BlockMobTileEntity blockMobTileEnt = (BlockMobTileEntity)te;
				return new BlockMobGUI(blockMobTileEnt, new BlockMobContainer(player.inventory, blockMobTileEnt));
		}
		
		return null;
	}
	
}