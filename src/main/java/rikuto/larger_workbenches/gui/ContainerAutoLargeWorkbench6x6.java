package rikuto.larger_workbenches.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import rikuto.larger_workbenches.tileentity.TileEntityAutoLargeWorkbench6x6;

public class ContainerAutoLargeWorkbench6x6 extends Container {

	TileEntityAutoLargeWorkbench6x6 workbench;

	public ContainerAutoLargeWorkbench6x6(InventoryPlayer inventoryPlayer, World world, TileEntityAutoLargeWorkbench6x6 tileEntity) {
		workbench = tileEntity;
		addSlotToContainer(new Slot(tileEntity, 0, 246, 142) {

			public boolean isItemValid(ItemStack itemStack) {
				return false;
			}

			public boolean canTakeStack(EntityPlayer player) {
				ItemStack slotStack = getStack();
				ItemStack playerStack = player.inventory.getItemStack();
				return slotStack != null && slotStack.stackSize > 0 && (playerStack == null || (slotStack.getItem() == playerStack.getItem() && (!playerStack.getHasSubtypes() || playerStack.getItemDamage() == slotStack.getItemDamage()) && ItemStack.areItemStackTagsEqual(playerStack, slotStack)));
			}
		});
		for (int l = 0; l < 6; l++) {
			for (int i1 = 0; i1 < 6; i1++) {
				addSlotToContainer(new Slot(tileEntity, 1 + i1 + l * 6, 39 + i1 * 18, 17 + l * 18));
			}
		}
		for (int l = 0; l < 6; l++) {
			for (int i1 = 0; i1 < 6; i1++) {
				addSlotToContainer(new Slot(tileEntity, 37 + i1 + l * 6, 201 + i1 * 18, 17 + l * 18) {

					public void onPickupFromSlot(EntityPlayer player, ItemStack itemStack) {}

					public ItemStack decrStackSize(int decrement) {
						return null;
					}

					public boolean isItemValid(ItemStack itemStack) {
						return false;
					}

					public boolean canTakeStack(EntityPlayer player) {
						return false;
					}
				});
			}
		}
		for (int l = 0; l < 3; l++) {
			for (int i1 = 0; i1 < 9; i1++) {
				addSlotToContainer(new Slot(inventoryPlayer, i1 + l * 9 + 9, 12 + i1 * 18, 138 + l * 18));
			}
		}
		for (int l = 0; l < 9; l++) {
			addSlotToContainer(new Slot(inventoryPlayer, l, 12 + l * 18, 196));
		}
	}

	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
	}

	public boolean canInteractWith(EntityPlayer player) {
		return workbench.isUseableByPlayer(player);
	}

	public ItemStack slotClick(int slot, int mouseButton, int modifier, EntityPlayer player) {
		if (slot > 36 && slot <= 72) {
			if (modifier == 2)
				return null;
			Slot actualSlot = (Slot)inventorySlots.get(slot);
			boolean slotHasStack = actualSlot.getHasStack();
			ItemStack playerStack = player.inventory.getItemStack();
			if (slotHasStack && playerStack == null) {
				actualSlot.putStack(null);
				return null;
			}
			if (playerStack != null) {
				ItemStack slotStack = playerStack.copy();
				slotStack.stackSize = 0;
				actualSlot.putStack(slotStack);
				return slotStack;
			}
			return null;
		} else
			return super.slotClick(slot, mouseButton, modifier, player);
	}

	public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber) {
		ItemStack itemStack = null;
		Slot slot = (Slot)inventorySlots.get(slotNumber);
		if ((slot != null) && (slot.getHasStack())) {
			ItemStack itemStack1 = slot.getStack();
			itemStack = itemStack1.copy();
			if ((slotNumber >= 0) && (slotNumber <= 36)) {
				if (!mergeItemStack(itemStack1, 73, 109, true))
					return null;
			}
			else if ((slotNumber >= 73) && (slotNumber < 109)) {
				if (!mergeItemStack(itemStack1, 1, 36, false))
					return null;
			} else if (!mergeItemStack(itemStack1, 73, 109, false))
				return null;
			if (itemStack1.stackSize == 0)
				slot.putStack(null);
			slot.onSlotChanged();
		}
		return itemStack;
	}
}