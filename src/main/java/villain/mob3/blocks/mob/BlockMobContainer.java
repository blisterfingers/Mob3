package villain.mob3.blocks.mob;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class BlockMobContainer extends Container {

	private BlockMobTileEntity te;
	
	public BlockMobContainer(IInventory playerInventory, BlockMobTileEntity te){
		this.te = te;
		
		addOwnSlots();
		addPlayerSlots(playerInventory);
	}
	
	private void addOwnSlots(){
		IItemHandler itemHandler = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		
		// Input slot
		addSlotToContainer(new SlotItemHandler(itemHandler, 0, 8, 36));
		
		// Output slots
		for(int row = 0; row < 3; row++){
			for(int col = 0; col < 3; col++){
				addSlotToContainer(new SlotItemHandler(itemHandler, 
						(3 * row) + col + 1, 
						92 + (col * 18), 
						18 + (row * 18)));
			}
		}
	}
	
	private void addPlayerSlots(IInventory playerInventory){
		// Slots for the hotbar
		for(int col = 0; col < 9; ++col){
			int x = 8 + (col * 18);
			int y = 142;
			this.addSlotToContainer(new Slot(playerInventory, col, x, y));
		}
		
		// Slots for the main inventory
		for(int row = 0; row < 3; ++row){
			for(int col = 0; col < 9; ++col){
				int x = 8 + (col * 18);
				int y = 84 + (row * 18);
				this.addSlotToContainer(new Slot(playerInventory, (row * 9) + col + 9, x, y));
			}
		}
	}
	
	public ItemStack transferStackInSlot(EntityPlayer player, int index){
		ItemStack itemStack = null;
		
		// If we're trying to transfer into a slot that isn't input..
		if(index != 0) return null;
		
		Slot inputSlot = inventorySlots.get(0);
		if(inputSlot != null && inputSlot.getHasStack()){
			ItemStack inputStack = inputSlot.getStack();
			itemStack = inputStack.copy();
			
			if(!mergeItemStack(inputStack, BlockMobTileEntity.SLOT_SIZE, inventorySlots.size(), true)){
				return null;
			}
			else if(!mergeItemStack(inputStack, 0, BlockMobTileEntity.SLOT_SIZE, false)){
				return null;
			}
			
			if(inputStack.stackSize == 0){
				inputSlot.putStack(null);
			}
			else {
				inputSlot.onSlotChanged();
			}
		}
		
		return itemStack;
	}
	
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return te.canInteractWith(player);
	}
}
