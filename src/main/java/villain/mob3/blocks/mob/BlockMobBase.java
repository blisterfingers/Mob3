package villain.mob3.blocks.mob;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import villain.mob3.Mob3;
import villain.mob3.client.GuiHandler;
import villain.mob3.utils.BlockUtils;

public class BlockMobBase extends Block implements ITileEntityProvider {

	public static final PropertyDirection FACING = PropertyDirection.create("facing");
		
	private BlockMobType type;
	
	public BlockMobBase(BlockMobType type){
		super(Material.CLOTH);
		this.type = type;
		setUnlocalizedName(Mob3.MODID + ".blockmob" + type.getName());
		setRegistryName("blockmob" + type);
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this), getRegistryName());
		GameRegistry.registerTileEntity(BlockMobTileEntity.class, Mob3.MODID + "_blockmob" + type.getName());
		setCreativeTab(Mob3.creativeTab);
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
				new ModelResourceLocation(getRegistryName(), "inventory"));
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return false;
	}
	
	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		world.setBlockState(pos, state.withProperty(FACING, BlockUtils.getFacingFromEntity(pos, placer)), 2);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, 
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		// Only execute on server
		if(world.isRemote){
			return true;
		}
		
		TileEntity te = world.getTileEntity(pos);
		if(!(te instanceof BlockMobTileEntity)){
			return false;
		}
		
		player.openGui(Mob3.MODID, GuiHandler.GUI_BLOCKMOB, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity te = worldIn.getTileEntity(pos);
		if(te instanceof BlockMobTileEntity){
			BlockMobTileEntity bmte = (BlockMobTileEntity)te;
			BlockUtils.dropItems(worldIn, pos, bmte.getDrops());
		}
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}
	
	public BlockMobType getType(){
		return type;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new BlockMobTileEntity(type);
	}
}
