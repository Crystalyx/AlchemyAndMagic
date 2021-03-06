package aam.common.skills;

import aam.common.soul.Soul;
import net.minecraft.util.ResourceLocation;

public class Skill
{
	public Skill(String name, int level, int gainLevel, Soul affinity, SkillAction act, ResourceLocation text, boolean clientn)
	{
		this.name = name;
		this.level = level;
		this.gainLevel = gainLevel;
		this.affinity = affinity;
		this.act = act;
		texture = text;
		clientN = clientn;
	}

	public Skill(String name, int level, int gainLevel, SkillAction act, ResourceLocation text, boolean clientn)
	{
		this.name = name;
		this.level = level;
		this.gainLevel = gainLevel;
		this.act = act;
		texture = text;
		clientN = clientn;
	}

	public int level;
	public String name;
	public int gainLevel;
	public Soul affinity = Soul.Normal;
	public SkillAction act;
	public ResourceLocation texture;
	public boolean clientN = false;

}
