package logisticspipes.utils.gui;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import logisticspipes.utils.gui.LogisticsBaseGuiScreen.Colors;
import net.minecraft.client.gui.FontRenderer;

import org.lwjgl.input.Keyboard;

import scala.Char;

public class SearchBar {
	protected String searchinput1 = "";
	protected String searchinput2 = "";
	protected boolean editsearch = true;
	protected boolean editsearchb = false;
	protected boolean displaycursor = true;
	protected long oldSystemTime = 0;
	protected int searchWidth = 150;
	
    private final FontRenderer fontRenderer;
    private final LogisticsBaseGuiScreen screen;
    private int left, top, heigth, width;
    
    public SearchBar(FontRenderer fontRenderer, LogisticsBaseGuiScreen screen, int left, int top, int width, int heigth) {
    	this.fontRenderer = fontRenderer;
    	this.screen = screen;
    	this.left = left;
    	this.top = top;
    	this.width = width;
    	this.heigth = heigth;
    	this.searchWidth = width - 10;
    }
	
    public void reposition(int left, int top, int width, int heigth) {
    	this.left = left;
    	this.top = top;
    	this.width = width;
    	this.heigth = heigth;
    }
    
	public void renderSearchBar() {
		if(editsearch) {
			screen.drawRect(left + 0, top - 2, left + width - 0, top + heigth - 0, Colors.Black);
			screen.drawRect(left + 1, top - 1, left + width - 1, top + heigth - 1, Colors.White);
		} else {
			screen.drawRect(left + 1, top - 1, left + width - 1, top + heigth - 1, Colors.Black);
		}
		screen.drawRect(left + 2, top - 0, left + width - 2, top + heigth - 2, Colors.DarkGrey);
		
		fontRenderer.drawString(searchinput1 + searchinput2, left + 5, top + 3, 0xFFFFFF);
		if(editsearch) {
			int linex = left + 5 + fontRenderer.getStringWidth(searchinput1);
			if(System.currentTimeMillis() - oldSystemTime > 500) {
				displaycursor = !displaycursor;
				oldSystemTime = System.currentTimeMillis();
			}
			if(displaycursor) {
				screen.drawRect(linex, top + 1, linex + 1, top + heigth - 3, Colors.White);
			}
		}
	}
	
	/**
	 * @return Boolean, true if click was handled.
	 */
	public boolean handleClick(int x, int y, int k) {
		if (x >= left + 2 && x < left + width - 2 && y >= top && y < top + heigth) {
			editsearch = true;
			if(k == 1) {
				searchinput1 = "";
				searchinput2 = "";
			}
			return true;
		} else if(editsearch) {
			editsearch = false;
			return true;
		}
		return false;
	}

	/**
	 * @return Boolean, true if key was handled.
	 */
	public boolean handleKey(char c, int i) {
		if(!editsearch) return false;
		if (c == 13 || i == 28) { //Enter
			editsearch = false;
		} else if (c == 8 || (i == 14 && System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0)) { //Backspace
			if (searchinput1.length() > 0)
				searchinput1 = searchinput1.substring(0, searchinput1.length() - 1);
		} else if(i == 203) { //Left
			if(searchinput1.length() > 0) {
				searchinput2 = searchinput1.substring(searchinput1.length() - 1) + searchinput2;
				searchinput1 = searchinput1.substring(0, searchinput1.length() - 1);
			}
		} else if(i == 205) { //Right
			if(searchinput2.length() > 0) {
				searchinput1 += searchinput2.substring(0,1);
				searchinput2 = searchinput2.substring(1);
			}
		} else if(i == 199) { //Home
			searchinput2 = searchinput1 + searchinput2;
			searchinput1 = "";
		} else if(i == 207) { //End
			searchinput1 = searchinput1 + searchinput2;
			searchinput2 = "";
		} else if(i == 211) { //Del
			if (searchinput2.length() > 0) {
				searchinput2 = searchinput2.substring(1);
			}
		} else if (i == 47 && Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) { //Ctrl-v
			searchinput1 = searchinput1 + getClipboardString();
		} else if (!Character.isISOControl(c)) {
			if (fontRenderer.getStringWidth(searchinput1 + c + searchinput2) <= searchWidth) {
				searchinput1 += c;
			}
		} else {
			//ignore this key/character
		}
		return true;
	}
	
	public String getContent() {
		return searchinput1 + searchinput2;
	}
	
	public boolean isEmpty() {
		return searchinput1.isEmpty() && searchinput2.isEmpty();
	}
	
	private static String getClipboardString() {
		try {
			Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents((Object)null);
			if(transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				return (String)transferable.getTransferData(DataFlavor.stringFlavor); 
			}
		} catch(Exception exception) {}
		return "";
	}
}
