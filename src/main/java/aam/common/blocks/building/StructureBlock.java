package aam.common.blocks.building;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class StructureBlock extends Block
{
	public StructureBlock(Material mat)
	{
		super(mat);
		this.setBlockTextureName("aam:struct");
	}

}
