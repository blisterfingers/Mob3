package villain.mob3.utils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public final class BlockUtils {

	public static EnumFacing getFacingFromEntity(BlockPos clickedBlock, EntityLivingBase entity){
		return EnumFacing.getFacingFromVector(
				(float)(entity.posX - clickedBlock.getX()),
				(float)(entity.posY - clickedBlock.getY()),
				(float)(entity.posZ - clickedBlock.getZ()));
	}
	
}
