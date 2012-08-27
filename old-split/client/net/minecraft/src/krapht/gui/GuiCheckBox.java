package net.minecraft.src.krapht.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiButton;
import net.minecraft.src.Tessellator;

public class GuiCheckBox extends GuiButton {
	
	private boolean state = false;
	
	public GuiCheckBox(int par1, int par2, int par3, int par4, int par5, boolean startState) {
		super(par1, par2, par3, par4, par5, "");
		state = startState;
	}

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft minecraft, int par2, int par3)
    {
        if (this.drawButton)
        {
            FontRenderer var4 = minecraft.fontRenderer;
            boolean var5 = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            int var6 = this.getHoverState(var5);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, minecraft.renderEngine.getTexture("/logisticspipes/gui/checkbox-" + (state?"on":"out") + "" + (var6 == 2?"-mouse":"") + ".png"));
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            Tessellator var9 = Tessellator.instance;
            var9.startDrawingQuads();
            var9.addVertexWithUV(xPosition			, yPosition + height	, (double)zLevel, 0	, 1);
            var9.addVertexWithUV(xPosition + width	, yPosition + height	, (double)zLevel, 1	, 1);
            var9.addVertexWithUV(xPosition + width	, yPosition				, (double)zLevel, 1	, 0);
            var9.addVertexWithUV(xPosition			, yPosition				, (double)zLevel, 0	, 0);
            var9.draw();
/*
            drawTexturedModalRect(xPosition  			, yPosition				, 0		, 0 ,0);
            drawTexturedModalRect(xPosition + width / 2	, yPosition				, 0		, 1, 0);
            
            drawTexturedModalRect(xPosition  			, yPosition + height / 2, 0		, 0 ,1);
            drawTexturedModalRect(xPosition + width / 2	, yPosition + height / 2, 0		, 1, 1);
*/
            mouseDragged(minecraft, par2, par3);
        }
    }

	public boolean change() {
		return state = !state;
	}
	
	public boolean getState() {
		return state;
	}
}