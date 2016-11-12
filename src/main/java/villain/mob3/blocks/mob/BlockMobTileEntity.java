package villain.mob3.blocks.mob;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class BlockMobTileEntity extends TileEntity implements ITickable {

	public static final int SLOT_SIZE = 10;
	public static final int TOTAL_CYCLE_TIME = 100;
	
	// Inventory slots
	private ItemStackHandler itemStackHandler = new ItemStackHandler(SLOT_SIZE){
		@Override
		protected void onContentsChanged(int slot) {
			BlockMobTileEntity.this.markDirty();
		}
	};
	
	private BlockMobType type;
	private short cycleTime = 0; // The amount of ticks taken in the current cycle
	private ItemStack cycleInputStack;
	private ItemStack cycleOutputStack;
	
	public BlockMobTileEntity(){ }
	
	public BlockMobTileEntity(BlockMobType type){
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
		
		type = BlockMobType.values()[compound.getInteger("type")];
		
		itemStackHandler.deserializeNBT(compound);
		
		if(compound.hasKey("cycleTime")){
			cycleTime = compound.getShort("cycleTime");
		}
		if(compound.hasKey("inputStack")){
			cycleInputStack = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("inputStack"));
		}
		if(compound.hasKey("outputStack")){
			cycleOutputStack = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("outputStack"));
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("type", type.ordinal());
		compound.merge(itemStackHandler.serializeNBT());
		compound.setShort("cycleTime", cycleTime);
		if(cycleInputStack != null) compound.setTag("inputStack", cycleInputStack.serializeNBT());
		if(cycleOutputStack != null) compound.setTag("outputStack", cycleOutputStack.serializeNBT());
		return compound;
	}
	
	@Override
	public void update() {
		if(worldObj.isRemote) return;
		
		// Are we mid-cycle?
		boolean upd = false;
		if(cycleInputStack != null){
			cycleTime++;			
			if(cycleTime >= TOTAL_CYCLE_TIME){
				// Complete the current cycle
				completeCycle();
			}
			
			// Update
			upd = true;
		}
		else {
			checkAndStartCycle();
			// Update
			upd = true;
		}
		
		if(upd){
			IBlockState state = worldObj.getBlockState(getPos());
			worldObj.notifyBlockUpdate(getPos(), state, state, 3);
		}
	}
		
	private void checkAndStartCycle(){
		ItemStack inputStack = itemStackHandler.getStackInSlot(0);
		ItemStack outputStack = type.getItemMap().getOutput();
		
		// Is there a valid input stack in the input slot?
		if(inputStack != null && inputStack.getItem() == type.getItemMap().getInputItem() &&
				inputStack.stackSize >= type.getItemMap().getInputAmountPer()){
			// Is there room for the output stack in the output slots?
			if(isRoomForOutput(outputStack)){
				// Start the cycle
				
				// Remove input stack and record
				cycleInputStack = inputStack.splitStack(type.getItemMap().getInputAmountPer());
				if(inputStack.stackSize <= 0) itemStackHandler.setStackInSlot(0, null);
				
				// Record output stack
				cycleOutputStack = outputStack;
				
				// mark dirty?
				markDirty();
			}
		}
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound updateTagDescribingEntityState = getUpdateTag();
		final int METADATA = 0;
		return new SPacketUpdateTileEntity(this.pos, METADATA, updateTagDescribingEntityState);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		NBTTagCompound updateTagDescribingEntityState = pkt.getNbtCompound();
		handleUpdateTag(updateTagDescribingEntityState);
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		return nbtTagCompound;
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		this.readFromNBT(tag);
	}
	
	private boolean isRoomForOutput(ItemStack outputStack){
		ItemStack slotStack;
		for(int i = 1; i < itemStackHandler.getSlots(); i++){
			slotStack = itemStackHandler.getStackInSlot(i);
			if(slotStack == null) return true;
			else if(slotStack.getItem() == outputStack.getItem() && 
				slotStack.getMaxStackSize() - slotStack.stackSize >= outputStack.stackSize){
				return true;
			}
		}
		
		return false;
	}
	
	private void completeCycle(){
		// Remove the current cycling item
		cycleInputStack = null;
		
		// Reset the cycle time
		cycleTime = 0;
		
		// Place the output stack into the output
		ItemStack slotStack;
		for(int i = 1; i < itemStackHandler.getSlots(); i++){
			slotStack = itemStackHandler.getStackInSlot(i);
			if(slotStack == null){
				itemStackHandler.setStackInSlot(i, cycleOutputStack);
				markDirty();
				return;
			}
			else if(slotStack.getItem() ==  cycleOutputStack.getItem() &&
					slotStack.getMaxStackSize() - slotStack.stackSize >= cycleOutputStack.stackSize){
				slotStack.stackSize += cycleOutputStack.stackSize;
				markDirty();
				return;
			}
		}
		
		throw new Error("There was no room to output the items.");
	}
	
	public double getProgress(){
		double fraction = (double)cycleTime / (double)TOTAL_CYCLE_TIME;
		return MathHelper.clamp_double(fraction, 0.0, 1.0);
	}
	
	public BlockMobType getType(){
		return type;
	}
	
	public ItemStackHandler getItemStackHandler(){
		return itemStackHandler;
	}
	
	public ItemStack[] getDrops(){
		ItemStack[] list = new ItemStack[itemStackHandler.getSlots() + 1];
		for(int i = 0; i < itemStackHandler.getSlots(); i++){
			if(itemStackHandler.getStackInSlot(i) != null){
				list[i] = itemStackHandler.getStackInSlot(i);
			}
		}
		if(cycleInputStack != null){
			list[itemStackHandler.getSlots()] = cycleInputStack;
		}
		
		return list;
	}
}
