package aam.api.interfaces;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTSave
{
	public void saveToNBT(NBTTagCompound tag);

	public void loadFromNBT(NBTTagCompound tag);

}
