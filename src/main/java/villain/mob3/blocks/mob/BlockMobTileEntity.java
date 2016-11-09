package villain.mob3.blocks.mob;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class BlockMobTileEntity extends TileEntity {

	public static final int SLOT_SIZE = 10;
	
	// Inventory slots
	private ItemStackHandler itemStackHandler = new ItemStackHandler(SLOT_SIZE){
		@Override
		protected void onContentsChanged(int slot) {
			BlockMobTileEntity.this.markDirty();
		}
	};
	
	private String type;
	
	public BlockMobTileEntity(String type){
		this.type = type;
	}
	
	public boolean canInteractWith(EntityPlayer player){
		return !isInvalid() && player.getDistanceSq(pos.add(0.5, 0.5, 0.5)) <= 64.0;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
			return true;
		}
		return super.hasCapability(capability, facing);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
			return (T)itemStackHandler;
		}
		return super.getCapability(capability, facing);
	}
	
	@Override
	public void readFromNBT(net.minecraft.nbt.NBTTagCompound compound) {
		super.readFromNBT(compound);
		if(compound.hasKey("items")){
			itemStackHandler.deserializeNBT((NBTTagCompound)compound.getTag("items"));
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag("items", itemStackHandler.serializeNBT());
		return compound;
	}
	
	public String getType(){
		return type;
	}
}
