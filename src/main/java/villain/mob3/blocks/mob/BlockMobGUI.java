package villain.mob3.blocks.mob;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import villain.mob3.Mob3;

public class BlockMobGUI extends GuiContainer {

	public static final int WIDTH = 176;
	public static final int HEIGHT = 166;
	
	private static final ResourceLocation background = new ResourceLocation(
			Mob3.MODID, "textures/gui/blockmobgui.png");
	
	public BlockMobGUI(BlockMobTileEntity tileEntity, BlockMobContainer container){
		super(container);
		xSize = WIDTH;
		ySize = HEIGHT;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(background);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
