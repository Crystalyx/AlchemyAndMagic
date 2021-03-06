package aam.api.fluent;

import aam.api.abstraction.MeleeWeapon;
import aam.common.items.ModItems;
import aam.utils.EnumRarity;
import net.minecraft.item.Item;

public class PreparedMeleeWeapon
{
    public boolean isHammer = false;
    public int minSlots = 0;
    public int maxSlots = 0;
    public boolean fixedSlots = false;
    public EnumRarity rarity = EnumRarity.Common;
    public int repairs = 3;
    public int durability = 500;
    public Item repairItem = null;
    public int repairItemMeta = 0;
    public double knockback = 0;
    public float reach = 4;

    private float baseDamage = 5;
    private boolean bypassesArmor = false;
    public String textureName = "";
    public String name = "";

    public PreparedMeleeWeapon withSlotsRanged(int _min, int _max)
    {
        this.minSlots = _min;
        this.maxSlots = _max;
        this.fixedSlots = false;
        return this;
    }

    public PreparedMeleeWeapon withSlotsFixed(int _slots)
    {
        this.maxSlots = _slots;
        this.fixedSlots = true;
        return this;
    }

    public PreparedMeleeWeapon withRarity(EnumRarity _rar)
    {
        this.rarity = _rar;
        return this;
    }

    public PreparedMeleeWeapon withRepairs(int _rep)
    {
        this.repairs = _rep;
        return this;
    }

    public PreparedMeleeWeapon withDurability(int _dur)
    {
        this.durability = _dur;
        return this;
    }

    public PreparedMeleeWeapon withRepairItem(Item _item, int _meta)
    {
        this.repairItem = _item;
        this.repairItemMeta = _meta;
        return this;
    }

    public PreparedMeleeWeapon withKnockback(double _knock)
    {
        this.knockback = _knock;
        return this;
    }

    public PreparedMeleeWeapon withReach(int _reach)
    {
        this.reach = _reach;
        return this;
    }

    public PreparedMeleeWeapon withBaseDamage(float _baseDamage)
    {
        this.baseDamage = _baseDamage;
        return this;
    }

    public PreparedMeleeWeapon withBaseDamage(double _baseDamage)
    {
        this.baseDamage = (float) _baseDamage;
        return this;
    }

    public PreparedMeleeWeapon setBypassesArmor()
    {
        this.bypassesArmor = true;
        return this;
    }

    public PreparedMeleeWeapon setBypassesArmor(boolean _bypassesArmor)
    {
        this.bypassesArmor = _bypassesArmor;
        return this;
    }

    public PreparedMeleeWeapon withTexture(String _textureName)
    {
        this.textureName = _textureName;
        return this;
    }

    public PreparedMeleeWeapon withName(String _name)
    {
        this.name = ModItems.idn+_name;
        return this;
    }

    public MeleeWeapon register()
    {
        MeleeWeapon ms = new MeleeWeapon(name, minSlots, maxSlots);
        ms.baseDamage=baseDamage;
        ms.bypassesArmor=bypassesArmor;
        ms.rarity=rarity;
        ms.texture = textureName;
        ms.durability=durability;
        ms.repairs =repairs;
        ms.repairItem=repairItem;
        ms.repairItemMeta = repairItemMeta;
        ModItems.melee.add(ms);
        ModItems.registerItem(ms, ModItems.meleetab);
        return ms;
    }

    public MeleeWeapon compile()
    {
        MeleeWeapon ms = new MeleeWeapon(name, minSlots, maxSlots);
        ms.baseDamage=baseDamage;
        ms.bypassesArmor=bypassesArmor;
        ms.rarity=rarity;
        ms.texture = textureName;
        ms.durability=durability;
        ms.repairs =repairs;
        ms.repairItem=repairItem;
        ms.repairItemMeta = repairItemMeta;
        return ms;
    }

    public PreparedMeleeWeapon asHammer(boolean _isHammer)
    {
        this.isHammer = _isHammer;
        return this;
    }

    public static PreparedMeleeWeapon newInstance()
    {
        return new PreparedMeleeWeapon();
    }
}