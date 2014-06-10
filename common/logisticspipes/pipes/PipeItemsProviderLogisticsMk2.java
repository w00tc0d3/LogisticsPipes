package logisticspipes.pipes;

import logisticspipes.textures.Textures;
import logisticspipes.textures.Textures.TextureType;

public class PipeItemsProviderLogisticsMk2 extends PipeItemsProviderLogistics {

	public PipeItemsProviderLogisticsMk2(int itemID) {
		super(itemID);
	}

	@Override
	public TextureType getCenterTexture() {
		return Textures.LOGISTICSPIPE_PROVIDERMK2_TEXTURE;
	}

	@Override
	protected int neededEnergy() {
		return 2;
	}

	@Override
	protected int itemsToExtract() {
		return 128;
	}

	@Override
	protected int stacksToExtract() {
		return 8;
	}

	@Override
	public ItemSendMode getItemSendMode() {
		return ItemSendMode.Fast;
	}
}
