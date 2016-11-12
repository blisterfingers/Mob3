package villain.mob3.blocks.mob;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public enum BlockMobType {

	COW("cow", new ItemMap(Items.WHEAT, 8,
			new Item[]{ Items.LEATHER, Items.BEEF },
			new float[]{ 0.8f, 0.2f })),
	MOOSHROOM("mooshroom", new ItemMap(Items.WHEAT, 8,
			new Item[]{ Item.getItemFromBlock(Blocks.BROWN_MUSHROOM), Items.BEEF },
			new float[]{ 0.8f, 0.2f })),
	SHEEP("sheep", new ItemMap(Items.WHEAT, 8,
			new Item[]{ Item.getItemFromBlock(Blocks.WOOL), Items.MUTTON },
			new float[]{ 0.8f, 0.2f })),
	PIG("pig", new ItemMap(Items.CARROT, 8,
			new Item[]{ Items.PORKCHOP },
			new float[]{ 1f })),
	CREEPER("creeper", new ItemMap(Items.IRON_HELMET, 1,
			new Item[]{ Items.GUNPOWDER },
			new float[]{ 1f })),
	ENDERMAN("enderman", new ItemMap(Item.getItemFromBlock(Blocks.GRASS), 4,
			new Item[]{ Items.ENDER_PEARL },
			new float[]{ 1f }));
	
	private static final Random random = new Random();
	
	private final String name;
	private final ItemMap itemMap;
	
	BlockMobType(String name, ItemMap itemMap){
		this.name = name;
		this.itemMap = itemMap;
	}
	
	@Override
	public String toString() {
		return name;
	}
		
	public String getName(){
		return name;
	}
	
	public ItemMap getItemMap(){
		return itemMap;
	}
	
	public static class ItemMap {
		
		private Item input;
		private int inputAmountPer;
		private Item[] outputs;
		private float[] outputChances;
		
		public ItemMap(Item inputItem, int inputAmountPer, Item[] outputs, float[] outputChances){
			this.input = inputItem;
			this.inputAmountPer = inputAmountPer;
			this.outputs = outputs;
			this.outputChances = outputChances;
		}
		
		public ItemStack getOutput(){
			float ran = random.nextFloat();
			float n = 0f;
			Item outputItem = null;
			for(int i = 0; i < outputs.length; i++){
				n += outputChances[i];
				if(ran <= n){
					outputItem = outputs[i];
					break;
				}
			}
			
			return new ItemStack(outputItem, 1);
		}
		
		public Item getInputItem(){
			return input;
		}
		
		public int getInputAmountPer(){
			return inputAmountPer;
		}
	}
}
