package logisticspipes.pipes.upgrades;

import logisticspipes.pipes.PipeBlockRequestTable;
import logisticspipes.pipes.basic.CoreRoutedPipe;

public class CraftingMonitoringUpgrade implements IPipeUpgrade {

	@Override
	public boolean needsUpdate() {
		return false;
	}

	@Override
	public boolean isAllowed(CoreRoutedPipe pipe) {
		return pipe instanceof PipeBlockRequestTable;
	}
}
