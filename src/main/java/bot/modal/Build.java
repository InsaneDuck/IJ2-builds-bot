package bot.modal;

import lombok.Data;

@Data
public class Build
{
    String command;
    String name;
    String buildType;
    String thumbnail;
    String attack;
    String health;
    String defense;
    String criticalAttackDamage;
    String criticalAttackChance;
    String lethalAttackChance;
    String fastAttackChance;
    String armourPierce;
    String be;
    String stunResist;
    String damageOverTimeResist;
    String criticalAttackChanceResist;
    String talents;
    String gears;
    String description;
    String buildCredits;

    @Override
    public String toString()
    {
        return "```" +
                "\ncommand           " + command +
                "\nName              " + name +
                "\nBuild Type        " + buildType +
                "\nThumbnail         " + thumbnail +
                "\nAttack            " + attack +
                "\nHealth            " + health +
                "\nDefense           " + defense +
                "\nCAD               " + criticalAttackDamage +
                "\nCAC               " + criticalAttackChance +
                "\nLAC               " + lethalAttackChance +
                "\nFAC               " + fastAttackChance +
                "\nArmour Pierce     " + armourPierce +
                "\nbe                " + be +
                "\nStun Resist       " + stunResist +
                "\nDOT Resist        " + damageOverTimeResist +
                "\nCAC Resist        " + criticalAttackChanceResist +
                "\nTalents           " + talents +
                "\nGears             " + gears +
                "\nDescription       " +
                "\n\n"+description + "\n"+
                "\nBuild Credits     " + buildCredits +
                "\n```";
    }
}
