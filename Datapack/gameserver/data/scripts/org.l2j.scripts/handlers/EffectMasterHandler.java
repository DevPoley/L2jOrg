package handlers;

import handlers.effecthandlers.*;
import handlers.effecthandlers.stat.StatHpBased;
import handlers.effecthandlers.stat.TransferDamageToPlayer;
import handlers.effecthandlers.stat.VitalStatModify;
import org.l2j.gameserver.engine.skill.api.SkillEffectFactory;
import org.l2j.gameserver.handler.EffectHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ServiceLoader;

/**
 * Effect Master handler.
 * @author NosBit
 */
public final class EffectMasterHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(EffectMasterHandler.class);

    public static void main(String[] args) {
        var handler = EffectHandler.getInstance();
        var loader = EffectMasterHandler.class.getClassLoader();

        ServiceLoader.load(SkillEffectFactory.class, loader).forEach(handler::registerFactory);

        handler.registerFactory("BlockTarget", BlockTarget::new);
        handler.registerFactory("Bluff", Bluff::new);
        handler.registerFactory("BuffBlock", BuffBlock::new);
        handler.registerFactory("CallParty", CallParty::new);
        handler.registerFactory("call-pc", CallPc::new);
        handler.registerFactory("call-skill", CallSkill::new);
        handler.registerFactory("CheapShot", CheapShot::new);
        handler.registerFactory("ChameleonRest", ChameleonRest::new);
        handler.registerFactory("ChangeFace", ChangeFace::new);
        handler.registerFactory("ChangeHairColor", ChangeHairColor::new);
        handler.registerFactory("ChangeHairStyle", ChangeHairStyle::new);
        handler.registerFactory("Confuse", Confuse::new);
        handler.registerFactory("ConsumeBody", ConsumeBody::new);
        handler.registerFactory("ConvertItem", ConvertItem::new);
        handler.registerFactory("cp", Cp::new);
        handler.registerFactory("CpHeal", CpHeal::new);
        handler.registerFactory("CpHealPercent", CpHealPercent::new);
        handler.registerFactory("stat-position-based", StatPositionBased::new);
        handler.registerFactory("stat-hp-based", StatHpBased::new);
        handler.registerFactory("damage-block", DamageBlock::new);
        handler.registerFactory("damage-by-attack", DamageByAttack::new);
        handler.registerFactory("damage-over-time", DamOverTime::new);
        handler.registerFactory("DeathLink", DeathLink::new);
        handler.registerFactory("DebuffBlock", DebuffBlock::new);
        handler.registerFactory("defence-trait", DefenceTrait::new);
        handler.registerFactory("DeleteHate", DeleteHate::new);
        handler.registerFactory("DeleteHateOfMe", DeleteHateOfMe::new);
        handler.registerFactory("Detection", Detection::new);
        handler.registerFactory("DisableTargeting", DisableTargeting::new);
        handler.registerFactory("Disarm", Disarm::new);
        handler.registerFactory("DispelAll", DispelAll::new);
        handler.registerFactory("dispel-by-category", DispelByCategory::new);
        handler.registerFactory("dispel", DispelBySlot::new);
        handler.registerFactory("dispel-myself", DispelBySlotMyself::new);
        handler.registerFactory("dispel-probability", DispelBySlotProbability::new);
        handler.registerFactory("energy-attack", EnergyAttack::new);
        handler.registerFactory("EnlargeAbnormalSlot", EnlargeAbnormalSlot::new);
        handler.registerFactory("enlarge-slot", EnlargeSlot::new);
        handler.registerFactory("escape", Escape::new);
        handler.registerFactory("FakeDeath", FakeDeath::new);
        handler.registerFactory("fatal-blow", FatalBlow::new);
        handler.registerFactory("Fear", Fear::new);
        handler.registerFactory("feed", Feed::new);
        handler.registerFactory("Flag", Flag::new);
        handler.registerFactory("FocusMomentum", FocusMomentum::new);
        handler.registerFactory("FocusMaxMomentum", FocusMaxMomentum::new);
        handler.registerFactory("FocusSouls", FocusSouls::new);
        handler.registerFactory("GetAgro", GetAgro::new);
        handler.registerFactory("GiveClanReputation", GiveClanReputation::new);
        handler.registerFactory("GiveSp", GiveSp::new);
        handler.registerFactory("GiveXp", GiveXp::new);
        handler.registerFactory("acquire-xp-sp", GiveExpAndSp::new);
        handler.registerFactory("Grow", Grow::new);
        handler.registerFactory("HairAccessorySet", HairAccessorySet::new);
        handler.registerFactory("headquarter", HeadquarterCreate::new);
        handler.registerFactory("Heal", Heal::new);
        handler.registerFactory("HealOverTime", HealOverTime::new);
        handler.registerFactory("HealPercent", HealPercent::new);
        handler.registerFactory("Hide", Hide::new);
        handler.registerFactory("hp", Hp::new);
        handler.registerFactory("HpByLevel", HpByLevel::new);
        handler.registerFactory("HpCpHeal", HpCpHeal::new);
        handler.registerFactory("hp-drain", HpDrain::new);
        handler.registerFactory("IgnoreDeath", IgnoreDeath::new);
        handler.registerFactory("ImmobilePetBuff", ImmobilePetBuff::new);
        handler.registerFactory("knockback", KnockBack::new);
        handler.registerFactory("lethal", Lethal::new);
        handler.registerFactory("Lucky", Lucky::new);
        handler.registerFactory("magical-attack", MagicalAttack::new);
        handler.registerFactory("MagicalAttackByAbnormal", MagicalAttackByAbnormal::new);
        handler.registerFactory("magical-attack-by-abnormal", MagicalAttackByAbnormalSlot::new);
        handler.registerFactory("magical-attack-mp", MagicalAttackMp::new);
        handler.registerFactory("magical-attack-range", MagicalAttackRange::new);
        handler.registerFactory("MagicalSoulAttack", MagicalSoulAttack::new);
        handler.registerFactory("magic-cost", MagicMpCost::new);
        handler.registerFactory("ManaDamOverTime", ManaDamOverTime::new);
        handler.registerFactory("ManaHeal", ManaHeal::new);
        handler.registerFactory("ManaHealByLevel", ManaHealByLevel::new);
        handler.registerFactory("ManaHealOverTime", ManaHealOverTime::new);
        handler.registerFactory("ManaHealPercent", ManaHealPercent::new);
        handler.registerFactory("vital-stat-modify", VitalStatModify::new);
        handler.registerFactory("vital-modify", ModifyVital::new);
        handler.registerFactory("mp", Mp::new);
        handler.registerFactory("MpConsumePerLevel", MpConsumePerLevel::new);
        handler.registerFactory("Mute", Mute::new);
        handler.registerFactory("NoblesseBless", NoblesseBless::new);
        handler.registerFactory("OpenChest", OpenChest::new);
        handler.registerFactory("OpenCommonRecipeBook", OpenCommonRecipeBook::new);
        handler.registerFactory("OpenDoor", OpenDoor::new);
        handler.registerFactory("OpenDwarfRecipeBook", OpenDwarfRecipeBook::new);
        handler.registerFactory("Passive", Passive::new);
        handler.registerFactory("physical-attack", PhysicalAttack::new);
        handler.registerFactory("physical-attack-hp-link", PhysicalAttackHpLink::new);
        handler.registerFactory("PhysicalAttackMute", PhysicalAttackMute::new);
        handler.registerFactory("PhysicalMute", PhysicalMute::new);
        handler.registerFactory("PhysicalShieldAngleAll", PhysicalShieldAngleAll::new);
        handler.registerFactory("physical-soul-attack", PhysicalSoulAttack::new);
        handler.registerFactory("PkCount", PkCount::new);
        handler.registerFactory("PolearmSingleTarget", PolearmSingleTarget::new);
        handler.registerFactory("ProtectionBlessing", ProtectionBlessing::new);
        handler.registerFactory("pull-back", PullBack::new);
        handler.registerFactory("RandomizeHate", RandomizeHate::new);
        handler.registerFactory("RealDamage", RealDamage::new);
        handler.registerFactory("RebalanceHP", RebalanceHP::new);
        handler.registerFactory("ReduceDamage", ReduceDamage::new);
        handler.registerFactory("reduce-drop-penalty", ReduceDropPenalty::new);
        handler.registerFactory("Relax", Relax::new);
        handler.registerFactory("resist-abnormal-by-category", ResistAbnormalByCategory::new);
        handler.registerFactory("resist-dispel-by-category", ResistDispelByCategory::new);
        handler.registerFactory("restoration", Restoration::new);
        handler.registerFactory("random-restoration", RestorationRandom::new);
        handler.registerFactory("Resurrection", Resurrection::new);
        handler.registerFactory("ResurrectionSpecial", ResurrectionSpecial::new);
        handler.registerFactory("reuse", Reuse::new);
        handler.registerFactory("reuse-skill", ReuseSkillById::new);
        handler.registerFactory("Root", Root::new);
        handler.registerFactory("clan-system-message", SendSystemMessageToClan::new);
        handler.registerFactory("servitor-share", ServitorShare::new);
        handler.registerFactory("SilentMove", SilentMove::new);
        handler.registerFactory("skill-critical", SkillCritical::new);
        handler.registerFactory("skill-evasion", SkillEvasion::new);
        handler.registerFactory("SkillTurning", SkillTurning::new);
        handler.registerFactory("soul-blow", SoulBlow::new);
        handler.registerFactory("soul-eating", SoulEating::new);
        handler.registerFactory("speed", Speed::new);
        handler.registerFactory("Spoil", Spoil::new);
        handler.registerFactory("stat-by-move-type", StatByMoveType::new);
        handler.registerFactory("stat", StatUp::new);
        handler.registerFactory("steal-abnormal", StealAbnormal::new);
        handler.registerFactory("summon", Summon::new);
        handler.registerFactory("summon-agathion", SummonAgathion::new);
        handler.registerFactory("summon-cubic", SummonCubic::new);
        handler.registerFactory("summon-npc", SummonNpc::new);
        handler.registerFactory("SummonPet", SummonPet::new);
        handler.registerFactory("Sweeper", Sweeper::new);
        handler.registerFactory("take-castle", TakeCastle::new);
        handler.registerFactory("TakeCastleStart", TakeCastleStart::new);
        handler.registerFactory("TalismanSlot", TalismanSlot::new);
        handler.registerFactory("TargetCancel", TargetCancel::new);
        handler.registerFactory("TargetMe", TargetMe::new);
        handler.registerFactory("TargetMeProbability", TargetMeProbability::new);
        handler.registerFactory("teleport", Teleport::new);
        handler.registerFactory("TeleportToTarget", TeleportToTarget::new);
        handler.registerFactory("TransferDamageToPlayer", TransferDamageToPlayer::new);
        handler.registerFactory("TransferHate", TransferHate::new);
        handler.registerFactory("transformation", Transformation::new);
        handler.registerFactory("trigger-skill-by-attack", TriggerSkillByAttack::new);
        handler.registerFactory("trigger-skill-by-avoid", TriggerSkillByAvoid::new);
        handler.registerFactory("trigger-skill-by-damage", TriggerSkillByDamage::new);
        handler.registerFactory("trigger-skill-by-magic", TriggerSkillByMagicType::new);
        handler.registerFactory("trigger-skill-by-skill", TriggerSkillBySkill::new);
        handler.registerFactory("two-hand-blunt-bonus", TwoHandedBluntBonus::new);
        handler.registerFactory("two-hand-sword-bonus", TwoHandedSwordBonus::new);
        handler.registerFactory("Unsummon", Unsummon::new);
        handler.registerFactory("UnsummonAgathion", UnsummonAgathion::new);
        handler.registerFactory("vampiric-attack", VampiricAttack::new);
        LOGGER.info("Loaded {} effect handlers.", EffectHandler.getInstance().size());
    }
}
