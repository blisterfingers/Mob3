package villain.mob3.utils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

public final class BlockUtils {

	public static EnumFacing getFacingFromEntity(BlockPos clickedBlock, EntityLivingBase entity){
		return EnumFacing.getFacingFromVector(
				(float)(entity.posX - clickedBlock.getX()),
				(float)(entity.posY - clickedBlock.getY()),
				(float)(entity.posZ - clickedBlock.getZ()));
	}
	
	public static void dropInventoryItems(World world, BlockPos pos, IItemHandler inventory){
		for(int i = 0; i < inventory.getSlots(); i++){
			ItemStack itemStack = inventory.getStackInSlot(i);
			if(itemStack != null){
				InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
			}
		}
	}
	
	public static void dropItems(World world, BlockPos pos, ItemStack[] items){
		for(int i = 0; i < items.length; i++){
			if(items[i] != null) InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), items[i]);			
		}
	}
	
}
