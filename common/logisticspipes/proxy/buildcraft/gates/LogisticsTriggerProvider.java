/** 
 * Copyright (c) Krapht, 2011
 * 
 * "LogisticsPipes" is distributed under the terms of the Minecraft Mod Public 
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */

package logisticspipes.proxy.buildcraft.gates;

import java.util.LinkedList;

import buildcraft.api.transport.IPipeTile;
import buildcraft.transport.Pipe;
import buildcraft.transport.gates.GateDefinition;
import logisticspipes.blocks.LogisticsSolderingTileEntity;
import logisticspipes.blocks.powertile.LogisticsPowerJunctionTileEntity;
import logisticspipes.pipes.PipeItemsCraftingLogistics;
import logisticspipes.pipes.PipeItemsFluidSupplier;
import logisticspipes.pipes.PipeItemsSupplierLogistics;
import logisticspipes.pipes.basic.CoreRoutedPipe;
import logisticspipes.proxy.buildcraft.BuildCraftProxy;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import buildcraft.api.gates.ITrigger;
import buildcraft.api.gates.ITriggerProvider;
import buildcraft.transport.Gate;

public class LogisticsTriggerProvider implements ITriggerProvider {

	@Override
	public LinkedList<ITrigger> getPipeTriggers(IPipeTile pipe) {
		if (pipe instanceof PipeItemsSupplierLogistics || pipe instanceof PipeItemsFluidSupplier) {
			LinkedList<ITrigger> triggers = new LinkedList<ITrigger>();
			triggers.add(BuildCraftProxy.LogisticsFailedTrigger);
			return triggers;
		}
		if(pipe instanceof PipeItemsCraftingLogistics) {
			LinkedList<ITrigger> triggers = new LinkedList<ITrigger>();
			triggers.add(BuildCraftProxy.LogisticsCraftingTrigger);
			return triggers;
		}
        if (pipe instanceof CoreRoutedPipe) {
            LinkedList<ITrigger> triggers = new LinkedList<ITrigger>();
            //Only show this conditional on Gates that can accept parameters
            if (((CoreRoutedPipe) pipe).hasGate()) {
                Gate gate = ((Pipe<?>) pipe).gate;
                if ((gate.logic == GateDefinition.GateLogic.AND || gate.logic == GateDefinition.GateLogic.OR)
                        && gate.material == GateDefinition.GateMaterial.DIAMOND) {
                    triggers.add(BuildCraftProxy.LogisticsHasDestinationTrigger);
                }
            }
            return triggers;
        }
        return null;
	}
	
	@Override
	public LinkedList<ITrigger> getNeighborTriggers(Block block, TileEntity tile) {
		if(tile instanceof LogisticsPowerJunctionTileEntity){
			LinkedList<ITrigger> triggers = new  LinkedList<ITrigger>();
			triggers.add(BuildCraftProxy.LogisticsNeedPowerTrigger);
			return triggers;
		}
		if(tile instanceof LogisticsSolderingTileEntity){
			LinkedList<ITrigger> triggers = new  LinkedList<ITrigger>();
			triggers.add(BuildCraftProxy.LogisticsNeedPowerTrigger);
			return triggers;
		}
		return null;
	}
}
