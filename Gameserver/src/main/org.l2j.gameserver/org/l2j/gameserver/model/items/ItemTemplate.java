package org.l2j.gameserver.model.items;

import org.l2j.gameserver.Config;
import org.l2j.gameserver.datatables.ItemTable;
import org.l2j.gameserver.enums.AttributeType;
import org.l2j.gameserver.enums.ItemGrade;
import org.l2j.gameserver.enums.ItemSkillType;
import org.l2j.gameserver.model.ExtractableProduct;
import org.l2j.gameserver.model.WorldObject;
import org.l2j.gameserver.model.PcCondOverride;
import org.l2j.gameserver.model.StatsSet;
import org.l2j.gameserver.model.actor.Creature;
import org.l2j.gameserver.model.ceremonyofchaos.CeremonyOfChaosEvent;
import org.l2j.gameserver.model.commission.CommissionItemType;
import org.l2j.gameserver.model.conditions.Condition;
import org.l2j.gameserver.model.events.ListenersContainer;
import org.l2j.gameserver.model.holders.ItemSkillHolder;
import org.l2j.gameserver.model.interfaces.IIdentifiable;
import org.l2j.gameserver.model.items.enchant.attribute.AttributeHolder;
import org.l2j.gameserver.model.items.type.*;
import org.l2j.gameserver.model.stats.Stats;
import org.l2j.gameserver.model.stats.functions.FuncAdd;
import org.l2j.gameserver.model.stats.functions.FuncSet;
import org.l2j.gameserver.model.stats.functions.FuncTemplate;
import org.l2j.gameserver.network.SystemMessageId;
import org.l2j.gameserver.network.serverpackets.SystemMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static org.l2j.gameserver.util.GameUtils.*;

/**
 * This class contains all informations concerning the item (weapon, armor, etc).<BR>
 * Mother class of :
 * <ul>
 * <li>Armor</li>
 * <li>EtcItem</li>
 * <li>Weapon</li>
 * </ul>
 */
public abstract class ItemTemplate extends ListenersContainer implements IIdentifiable {
    public static final int TYPE1_WEAPON_RING_EARRING_NECKLACE = 0;
    public static final int TYPE1_SHIELD_ARMOR = 1;
    public static final int TYPE1_ITEM_QUESTITEM_ADENA = 4;
    public static final int TYPE2_WEAPON = 0;
    public static final int TYPE2_SHIELD_ARMOR = 1;
    public static final int TYPE2_ACCESSORY = 2;
    public static final int TYPE2_QUEST = 3;
    public static final int TYPE2_MONEY = 4;
    public static final int TYPE2_OTHER = 5;
    public static final int SLOT_NONE = 0x0000;
    public static final int SLOT_UNDERWEAR = 0x0001;
    public static final int SLOT_R_EAR = 0x0002;
    public static final int SLOT_L_EAR = 0x0004;
    public static final int SLOT_LR_EAR = 0x00006;
    public static final int SLOT_NECK = 0x0008;
    public static final int SLOT_R_FINGER = 0x0010;
    public static final int SLOT_L_FINGER = 0x0020;
    public static final int SLOT_LR_FINGER = 0x0030;
    public static final int SLOT_HEAD = 0x0040;
    public static final int SLOT_R_HAND = 0x0080;
    public static final int SLOT_L_HAND = 0x0100;
    public static final int SLOT_GLOVES = 0x0200;
    public static final int SLOT_CHEST = 0x0400;
    public static final int SLOT_LEGS = 0x0800;
    public static final int SLOT_FEET = 0x1000;
    public static final int SLOT_BACK = 0x2000;
    public static final int SLOT_LR_HAND = 0x4000;
    public static final int SLOT_FULL_ARMOR = 0x8000;
    public static final int SLOT_HAIR = 0x010000;
    public static final int SLOT_ALLDRESS = 0x020000;
    public static final int SLOT_HAIR2 = 0x040000;
    public static final int SLOT_HAIRALL = 0x080000;
    public static final int SLOT_R_BRACELET = 0x100000;
    public static final int SLOT_L_BRACELET = 0x200000;
    public static final int SLOT_TALISMAN = 0x400000;
    public static final int SLOT_BELT = 0x10000000;
    public static final int SLOT_BROOCH = 0x20000000;
    public static final int SLOT_BROOCH_JEWEL = 0x40000000;
    public static final long SLOT_AGATHION = 0x3000000000L;
    public static final long SLOT_ARTIFACT_BOOK = 0x20000000000L;
    public static final long SLOT_ARTIFACT = 0x40000000000L;
    public static final int SLOT_WOLF = -100;
    public static final int SLOT_HATCHLING = -101;
    public static final int SLOT_STRIDER = -102;
    public static final int SLOT_BABYPET = -103;
    public static final int SLOT_GREATWOLF = -104;
    public static final int SLOT_MULTI_ALLWEAPON = SLOT_LR_HAND | SLOT_R_HAND;
    protected static final Logger LOGGER = LoggerFactory.getLogger(ItemTemplate.class);
    protected int _type1; // needed for item list (inventory)
    protected int _type2; // different lists for armor, weapon, etc
    protected List<FuncTemplate> _funcTemplates;
    protected List<Condition> _preConditions;
    private int _itemId;
    private int _displayId;
    private String _name;
    private String _icon;
    private int _weight;
    private boolean _stackable;
    private CrystalType _crystalType;
    private int _equipReuseDelay;
    private long _time;
    private int _autoDestroyTime;
    private long _bodyPart;
    private int _referencePrice;
    private int _crystalCount;
    private boolean _sellable;
    private boolean _dropable;
    private boolean _destroyable;
    private boolean _tradeable;
    private boolean _depositable;
    private int _enchantable;
    private boolean _elementable;
    private boolean _questItem;
    private boolean _freightable;
    private boolean _allow_self_resurrection;
    private boolean _is_oly_restricted;
    private boolean _is_coc_restricted;
    private boolean _for_npc;
    private boolean _common;
    private boolean _heroItem;
    private boolean _pvpItem;
    private boolean _immediate_effect;
    private boolean _ex_immediate_effect;
    private int _defaultEnchantLevel;
    private ActionType _defaultAction;
    private Map<AttributeType, AttributeHolder> _elementals = null;
    private List<ItemSkillHolder> _skills;

    private int _useSkillDisTime;
    private int _reuseDelay;
    private int _sharedReuseGroup;

    private CommissionItemType _commissionItemType;
    private boolean _isBlessed;

    /**
     * Constructor of the ItemTemplate that fill class variables.<BR>
     * <BR>
     *
     * @param set : StatsSet corresponding to a set of couples (key,value) for description of the item
     */
    protected ItemTemplate(StatsSet set) {
        set(set);
    }

    public void set(StatsSet set) {
        _itemId = set.getInt("item_id");
        _displayId = set.getInt("displayId", _itemId);
        _name = set.getString("name");
        _icon = set.getString("icon", null);
        _weight = set.getInt("weight", 0);
        _equipReuseDelay = set.getInt("equip_reuse_delay", 0) * 1000;

        _time = set.getInt("time", -1);
        _autoDestroyTime = set.getInt("auto_destroy_time", -1) * 1000;
        _bodyPart = ItemTable.SLOTS.get(set.getString("bodypart", "none"));
        _referencePrice = set.getInt("price", 0);
        _crystalType = set.getEnum("crystal_type", CrystalType.class, CrystalType.NONE);
        _crystalCount = set.getInt("crystal_count", 0);
        _stackable = set.getBoolean("is_stackable", false);
        _sellable = set.getBoolean("is_sellable", true);
        _dropable = set.getBoolean("is_dropable", true);
        _destroyable = set.getBoolean("is_destroyable", true);
        _tradeable = set.getBoolean("is_tradable", true);
        _depositable = set.getBoolean("is_depositable", true);
        _elementable = set.getBoolean("element_enabled", false);
        _enchantable = set.getInt("enchant_enabled", 0);
        _questItem = set.getBoolean("is_questitem", false);
        _freightable = set.getBoolean("is_freightable", false);
        _allow_self_resurrection = set.getBoolean("allow_self_resurrection", false);
        _is_oly_restricted = set.getBoolean("is_oly_restricted", false);
        _is_coc_restricted = set.getBoolean("is_coc_restricted", false);
        _for_npc = set.getBoolean("for_npc", false);
        _isBlessed = set.getBoolean("blessed", false);

        _immediate_effect = set.getBoolean("immediate_effect", false);
        _ex_immediate_effect = set.getBoolean("ex_immediate_effect", false);

        _defaultAction = set.getEnum("default_action", ActionType.class, ActionType.NONE);
        _useSkillDisTime = set.getInt("useSkillDisTime", 0);
        _defaultEnchantLevel = set.getInt("enchanted", 0);
        _reuseDelay = set.getInt("reuse_delay", 0);
        _sharedReuseGroup = set.getInt("shared_reuse_group", 0);
        _commissionItemType = set.getEnum("commissionItemType", CommissionItemType.class, CommissionItemType.OTHER_ITEM);
        _common = ((_itemId >= 11605) && (_itemId <= 12361));
        _heroItem = ((_itemId >= 6611) && (_itemId <= 6621)) || ((_itemId >= 9388) && (_itemId <= 9390)) || (_itemId == 6842);
        _pvpItem = ((_itemId >= 10667) && (_itemId <= 10835)) || ((_itemId >= 12852) && (_itemId <= 12977)) || ((_itemId >= 14363) && (_itemId <= 14525)) || (_itemId == 14528) || (_itemId == 14529) || (_itemId == 14558) || ((_itemId >= 15913) && (_itemId <= 16024)) || ((_itemId >= 16134) && (_itemId <= 16147)) || (_itemId == 16149) || (_itemId == 16151) || (_itemId == 16153) || (_itemId == 16155) || (_itemId == 16157) || (_itemId == 16159) || ((_itemId >= 16168) && (_itemId <= 16176)) || ((_itemId >= 16179) && (_itemId <= 16220));
    }

    /**
     * Returns the itemType.
     *
     * @return Enum
     */
    public abstract ItemType getItemType();

    /**
     * Verifies if the item is a magic weapon.
     *
     * @return {@code true} if the weapon is magic, {@code false} otherwise
     */
    public boolean isMagicWeapon() {
        return false;
    }

    /**
     * @return the _equipReuseDelay
     */
    public int getEquipReuseDelay() {
        return _equipReuseDelay;
    }


    /**
     * Returns the time of the item
     *
     * @return long
     */
    public final long getTime() {
        return _time;
    }

    /**
     * @return the auto destroy time of the item in seconds: 0 or less - default
     */
    public final int getAutoDestroyTime() {
        return _autoDestroyTime;
    }

    /**
     * Returns the ID of the item
     *
     * @return int
     */
    @Override
    public final int getId() {
        return _itemId;
    }

    /**
     * Returns the ID of the item
     *
     * @return int
     */
    public final int getDisplayId() {
        return _displayId;
    }

    public abstract int getItemMask();


    /**
     * Returns the type 2 of the item
     *
     * @return int
     */
    public final int getType2() {
        return _type2;
    }

    /**
     * Returns the weight of the item
     *
     * @return int
     */
    public final int getWeight() {
        return _weight;
    }

    /**
     * Returns if the item is crystallizable
     *
     * @return boolean
     */
    public final boolean isCrystallizable() {
        return (_crystalType != CrystalType.NONE) && (_crystalCount > 0);
    }

    /**
     * @return return General item grade (No S80, S84, R95, R99)
     */
    public ItemGrade getItemGrade() {
        return ItemGrade.valueOf(_crystalType);
    }

    /**
     * Return the type of crystal if item is crystallizable
     *
     * @return CrystalType
     */
    public final CrystalType getCrystalType() {
        return _crystalType;
    }

    /**
     * Return the ID of crystal if item is crystallizable
     *
     * @return int
     */
    public final int getCrystalItemId() {
        return _crystalType.getCrystalId();
    }

    /**
     * @return the quantity of crystals for crystallization.
     */
    public final int getCrystalCount() {
        return _crystalCount;
    }

    /**
     * @param enchantLevel
     * @return the quantity of crystals for crystallization on specific enchant level
     */
    public final int getCrystalCount(int enchantLevel) {
        if (enchantLevel > 3) {
            switch (_type2) {
                case TYPE2_SHIELD_ARMOR:
                case TYPE2_ACCESSORY: {
                    return _crystalCount + (_crystalType.getCrystalEnchantBonusArmor() * ((3 * enchantLevel) - 6));
                }
                case TYPE2_WEAPON: {
                    return _crystalCount + (_crystalType.getCrystalEnchantBonusWeapon() * ((2 * enchantLevel) - 3));
                }
                default: {
                    return _crystalCount;
                }
            }
        } else if (enchantLevel > 0) {
            switch (_type2) {
                case TYPE2_SHIELD_ARMOR:
                case TYPE2_ACCESSORY: {
                    return _crystalCount + (_crystalType.getCrystalEnchantBonusArmor() * enchantLevel);
                }
                case TYPE2_WEAPON: {
                    return _crystalCount + (_crystalType.getCrystalEnchantBonusWeapon() * enchantLevel);
                }
                default: {
                    return _crystalCount;
                }
            }
        } else {
            return _crystalCount;
        }
    }

    /**
     * @return the name of the item.
     */
    public final String getName() {
        return _name;
    }


    public Collection<AttributeHolder> getAttributes() {
        return _elementals != null ? _elementals.values() : null;
    }

    /**
     * Sets the base elemental of the item.
     *
     * @param holder the element to set.
     */
    public void setAttributes(AttributeHolder holder) {
        if (_elementals == null) {
            _elementals = new LinkedHashMap<>(3);
            _elementals.put(holder.getType(), holder);
        } else {
            final AttributeHolder attribute = getAttribute(holder.getType());
            if (attribute != null) {
                attribute.setValue(holder.getValue());
            } else {
                _elementals.put(holder.getType(), holder);
            }
        }
    }

    public AttributeHolder getAttribute(AttributeType type) {
        return _elementals != null ? _elementals.get(type) : null;
    }

    /**
     * @return the part of the body used with the item.
     */
    public final long getBodyPart() {
        return _bodyPart;
    }

    /**
     * @return the type 1 of the item.
     */
    public final int getType1() {
        return _type1;
    }

    /**
     * @return {@code true} if the item is stackable, {@code false} otherwise.
     */
    public final boolean isStackable() {
        return _stackable;
    }

    /**
     * @return {@code true} if the item can be equipped, {@code false} otherwise.
     */
    public boolean isEquipable() {
        return (_bodyPart != 0) && !(getItemType() instanceof EtcItemType);
    }

    /**
     * @return the price of reference of the item.
     */
    public final int getReferencePrice() {
        return _referencePrice;
    }

    /**
     * @return {@code true} if the item can be sold, {@code false} otherwise.
     */
    public final boolean isSellable() {
        return _sellable;
    }

    /**
     * @return {@code true} if the item can be dropped, {@code false} otherwise.
     */
    public final boolean isDropable() {
        return _dropable;
    }

    /**
     * @return {@code true} if the item can be destroyed, {@code false} otherwise.
     */
    public final boolean isDestroyable() {
        return _destroyable;
    }

    /**
     * @return {@code true} if the item can be traded, {@code false} otherwise.
     */
    public final boolean isTradeable() {
        return _tradeable;
    }

    /**
     * @return {@code true} if the item can be put into warehouse, {@code false} otherwise.
     */
    public final boolean isDepositable() {
        return _depositable;
    }

    /**
     * This method also check the enchant blacklist.
     *
     * @return {@code true} if the item can be enchanted, {@code false} otherwise.
     */
    public final int isEnchantable() {
        return Arrays.binarySearch(Config.ENCHANT_BLACKLIST, _itemId) < 0 ? _enchantable : 0;
    }

    /**
     * @return {@code true} if the item can be elemented, {@code false} otherwise.
     */
    public final boolean isElementable() {
        return _elementable;
    }

    /**
     * Returns if item is common
     *
     * @return boolean
     */
    public final boolean isCommon() {
        return _common;
    }

    /**
     * Returns if item is hero-only
     *
     * @return
     */
    public final boolean isHeroItem() {
        return _heroItem;
    }

    /**
     * Returns if item is pvp
     *
     * @return
     */
    public final boolean isPvpItem() {
        return _pvpItem;
    }

    public boolean isPotion() {
        return getItemType() == EtcItemType.POTION;
    }

    public boolean isElixir() {
        return getItemType() == EtcItemType.ELIXIR;
    }

    public boolean isScroll() {
        return getItemType() == EtcItemType.SCROLL;
    }

    public List<FuncTemplate> getFunctionTemplates() {
        return _funcTemplates != null ? _funcTemplates : Collections.emptyList();
    }

    /**
     * Add the FuncTemplate f to the list of functions used with the item
     *
     * @param template : FuncTemplate to add
     */
    public void addFunctionTemplate(FuncTemplate template) {
        switch (template.getStat()) {
            case FIRE_RES:
            case FIRE_POWER: {
                setAttributes(new AttributeHolder(AttributeType.FIRE, (int) template.getValue()));
                break;
            }
            case WATER_RES:
            case WATER_POWER: {
                setAttributes(new AttributeHolder(AttributeType.WATER, (int) template.getValue()));
                break;
            }
            case WIND_RES:
            case WIND_POWER: {
                setAttributes(new AttributeHolder(AttributeType.WIND, (int) template.getValue()));
                break;
            }
            case EARTH_RES:
            case EARTH_POWER: {
                setAttributes(new AttributeHolder(AttributeType.EARTH, (int) template.getValue()));
                break;
            }
            case HOLY_RES:
            case HOLY_POWER: {
                setAttributes(new AttributeHolder(AttributeType.HOLY, (int) template.getValue()));
                break;
            }
            case DARK_RES:
            case DARK_POWER: {
                setAttributes(new AttributeHolder(AttributeType.DARK, (int) template.getValue()));
                break;
            }
        }

        if (_funcTemplates == null) {
            _funcTemplates = new ArrayList<>();
        }
        _funcTemplates.add(template);
    }

    public final void attachCondition(Condition c) {
        if (_preConditions == null) {
            _preConditions = new ArrayList<>();
        }
        _preConditions.add(c);
    }

    public List<Condition> getConditions() {
        return _preConditions;
    }

    /**
     * Method to retrieve skills linked to this item armor and weapon: passive skills etcitem: skills used on item use <-- ???
     *
     * @return Skills linked to this item as SkillHolder[]
     */
    public final List<ItemSkillHolder> getAllSkills() {
        return _skills;
    }

    /**
     * @param condition
     * @return {@code List} of {@link ItemSkillHolder} if item has skills and matches the condition, {@code null} otherwise
     */
    public final List<ItemSkillHolder> getSkills(Predicate<ItemSkillHolder> condition) {
        return _skills != null ? _skills.stream().filter(condition).collect(Collectors.toList()) : null;
    }

    /**
     * @param type
     * @return {@code List} of {@link ItemSkillHolder} if item has skills, {@code null} otherwise
     */
    public final List<ItemSkillHolder> getSkills(ItemSkillType type) {
        return nonNull(_skills) ? _skills.stream().filter(sk -> sk.getType() == type).collect(Collectors.toList()) : Collections.emptyList();
    }

    /**
     * Executes the action on each item skill with the specified type (If there are skills at all)
     *
     * @param type
     * @param action
     */
    public final void forEachSkill(ItemSkillType type, Consumer<ItemSkillHolder> action) {
        if (_skills != null) {
            _skills.stream().filter(sk -> sk.getType() == type).forEach(action);
        }
    }

    public void addSkill(ItemSkillHolder holder) {
        if (_skills == null) {
            _skills = new ArrayList<>();
        }
        _skills.add(holder);
    }

    public boolean checkCondition(Creature activeChar, WorldObject object, boolean sendMessage) {
        if (activeChar.canOverrideCond(PcCondOverride.ITEM_CONDITIONS) && !Config.GM_ITEM_RESTRICTION) {
            return true;
        }

        // Don't allow hero equipment and restricted items during Olympiad
        if ((isOlyRestrictedItem() || _heroItem) && (isPlayer(activeChar) && activeChar.getActingPlayer().isInOlympiadMode())) {
            if (isEquipable()) {
                activeChar.sendPacket(SystemMessageId.YOU_CANNOT_EQUIP_THAT_ITEM_IN_A_OLYMPIAD_MATCH);
            } else {
                activeChar.sendPacket(SystemMessageId.YOU_CANNOT_USE_THAT_ITEM_IN_A_OLYMPIAD_MATCH);
            }
            return false;
        }

        if (_is_coc_restricted && (isPlayer(activeChar) && (activeChar.getActingPlayer().isOnEvent(CeremonyOfChaosEvent.class)))) {
            activeChar.sendPacket(SystemMessageId.YOU_CANNOT_USE_THIS_ITEM_IN_THE_TOURNAMENT);
            return false;
        }

        if (!isConditionAttached()) {
            return true;
        }

        final Creature target = isCreature(object) ? (Creature) object : null;
        for (Condition preCondition : _preConditions) {
            if (preCondition == null) {
                continue;
            }

            if (!preCondition.test(activeChar, target, null, null)) {
                if (isSummon(activeChar)) {
                    activeChar.sendPacket(SystemMessageId.THIS_PET_CANNOT_USE_THIS_ITEM);
                    return false;
                }

                if (sendMessage) {
                    final String msg = preCondition.getMessage();
                    final int msgId = preCondition.getMessageId();
                    if (msg != null) {
                        activeChar.sendMessage(msg);
                    } else if (msgId != 0) {
                        final SystemMessage sm = SystemMessage.getSystemMessage(msgId);
                        if (preCondition.isAddName()) {
                            sm.addItemName(_itemId);
                        }
                        activeChar.sendPacket(sm);
                    }
                }
                return false;
            }
        }
        return true;
    }

    public boolean isConditionAttached() {
        return (_preConditions != null) && !_preConditions.isEmpty();
    }

    public boolean isQuestItem() {
        return _questItem;
    }

    public boolean isFreightable() {
        return _freightable;
    }

    public boolean isAllowSelfResurrection() {
        return _allow_self_resurrection;
    }

    public boolean isOlyRestrictedItem() {
        return _is_oly_restricted || Config.LIST_OLY_RESTRICTED_ITEMS.contains(_itemId);
    }

    /**
     * @return {@code true} if item cannot be used in Ceremony of Chaos games.
     */
    public boolean isCocRestrictedItem() {
        return _is_coc_restricted;
    }

    public boolean isForNpc() {
        return _for_npc;
    }


    /**
     * @return {@code true} if the item is blessed, {@code false} otherwise.
     */
    public final boolean isBlessed() {
        return _isBlessed;
    }

    /**
     * Returns the name of the item followed by the item ID.
     *
     * @return the name and the ID of the item
     */
    @Override
    public String toString() {
        return _name + "(" + _itemId + ")";
    }

    /**
     * Verifies if the item has effects immediately.<br>
     * <i>Used for herbs mostly.</i>
     *
     * @return {@code true} if the item applies effects immediately, {@code false} otherwise
     */
    public boolean hasExImmediateEffect() {
        return _ex_immediate_effect;
    }

    /**
     * Verifies if the item has effects immediately.
     *
     * @return {@code true} if the item applies effects immediately, {@code false} otherwise
     */
    public boolean hasImmediateEffect() {
        return _immediate_effect;
    }

    /**
     * @return the _default_action
     */
    public ActionType getDefaultAction() {
        return _defaultAction;
    }

    public int useSkillDisTime() {
        return _useSkillDisTime;
    }

    /**
     * Gets the item reuse delay time in seconds.
     *
     * @return the reuse delay time
     */
    public int getReuseDelay() {
        return _reuseDelay;
    }

    /**
     * Gets the shared reuse group.<br>
     * Items with the same reuse group will render reuse delay upon those items when used.
     *
     * @return the shared reuse group
     */
    public int getSharedReuseGroup() {
        return _sharedReuseGroup;
    }

    public CommissionItemType getCommissionItemType() {
        return _commissionItemType;
    }

    /**
     * Usable in HTML windows.
     *
     * @return the icon link in client files
     */
    public String getIcon() {
        return _icon;
    }

    public int getDefaultEnchantLevel() {
        return _defaultEnchantLevel;
    }

    public boolean isPetItem() {
        return getItemType() == EtcItemType.PET_COLLAR;
    }

    /**
     * @param extractableProduct
     */
    public void addCapsuledItem(ExtractableProduct extractableProduct) {
    }

    public double getStats(Stats stat, double defaultValue) {
        if (_funcTemplates != null) {
            final FuncTemplate template = _funcTemplates.stream().filter(func -> (func.getStat() == stat) && ((func.getFunctionClass() == FuncAdd.class) || (func.getFunctionClass() == FuncSet.class))).findFirst().orElse(null);
            if (template != null) {
                return template.getValue();
            }
        }
        return defaultValue;
    }
}
