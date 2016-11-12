package villain.mob3.blocks.mob;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import villain.mob3.Mob3;

public class BlockMobGUI extends GuiContainer {

	public static final int WIDTH = 176;
	public static final int HEIGHT = 166;
	
	public static final int PROGRESS_BAR_TEXTURE_X = 176;
	public static final int PROGRESS_BAR_TEXTURE_Y = 0;
	public static final int PROGRESS_BAR_WIDTH = 48;
	public static final int PROGRESS_BAR_HEIGHT = 6;
	
	private static final ResourceLocation background = new ResourceLocation(
			Mob3.MODID, "textures/gui/blockmobgui.png");
	
	private BlockMobTileEntity te;
	
	public BlockMobGUI(BlockMobTileEntity tileEntity, BlockMobContainer container){
		super(container);
		this.te = tileEntity;
		xSize = WIDTH;
		ySize = HEIGHT;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(background);
		
		// Background
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		// Draw progress bar
		int progressBarWidth = (int)(te.getProgress() * (double)PROGRESS_BAR_WIDTH);
		drawTexturedModalRect(guiLeft + 34, guiTop + 41, PROGRESS_BAR_TEXTURE_X, PROGRESS_BAR_TEXTURE_Y,
				progressBarWidth, PROGRESS_BAR_HEIGHT);
	}
}
