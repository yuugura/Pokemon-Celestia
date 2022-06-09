
package poke;

import java.util.*;

public class BattleMech {
    public static Pokemon[] myTeam = new Pokemon[5];
    public static Pokemon[] enemyTeam = new Pokemon[5];
    
    public static void main(String[] args){
        Pokemon poke1 = new Pokemon(Monsters.GARDEVOIR);
        Pokemon enemy = new Pokemon(Monsters.DELPHOX);
        BattleMech bm = new BattleMech();

        System.out.println("Starting HP for both Pokemon: ");
        System.out.println("Gardevoir: " + poke1.getBattleHP());
        System.out.println("Delphox: " + enemy.getBattleHP());
        System.out.println();

        //if player's pokemon has equal or faster speed
        if(poke1.getBattleSPD() >= enemy.getBattleSPD()){
            System.out.println(bm.battleOrder(poke1, AttackMove.MOONBLAST, enemy, AttackMove.FLAMETHROWER));
        } else {
            System.out.println(bm.battleOrder(enemy, AttackMove.FLAMETHROWER, poke1, AttackMove.MOONBLAST));
        }

        System.out.println("Ending HP for both Pokemon: ");
        System.out.println("Gardevoir " + poke1.getBattleHP());
        System.out.println("Delphox: " + enemy.getBattleHP());
        System.out.println();

        System.out.println("Original HP for both Pokemon: ");
        System.out.println("Gardevoir: " + Monsters.GARDEVOIR.getHP());
        System.out.println("Delphox: " + Monsters.DELPHOX.getHP());
    }

    //method used for move priority
    private String battleOrder(Pokemon first, AttackMove firstMove, Pokemon second, AttackMove secondMove){
        String str = first.getName() + " used " + firstMove.getName();
        double firstDamage = calculateAttack(first, second, firstMove), secondDamage = calculateAttack(second, first, secondMove);

        str += "\n" + first.getName() + " deals " + firstDamage + " damage to " + second.getName();
        second.takeDamage(firstDamage);

        //check type effectiveness to print messages
        double d = typeEffectiveness(second, firstMove);

        if(d == 0){
            str += "\nIt has no effect on " + second.getName();
        } else if (d < 1){
            str += "\n It's not very effective";
        } else {
            str += "\nIt's super effective";
        }

        if(second.fainted()){
            str += "\n" + second.getName() + " fainted";
            return str + "\n";
        }

        //only apply status if the attack has effect on the defending pokemon
        if(d != 0){
            if(rollStatusEffect(firstMove)){
                applyStatus(firstMove, second);
                str += "\n" + firstMove.getStatusEffect() + " is applied on " + second.getName();
            }
        }

        str += "\n" + second.getName() + " used " + secondMove.getName();

        str += "\n" + second.getName() + " deals " + secondDamage + " damage to " + first.getName();
        first.takeDamage(secondDamage);

        double f = typeEffectiveness(first, secondMove);

        if(f == 0){
            str += "\nIt has no effect on " + first.getName();
        } else if (f < 1){
            str += "\n It's not very effective";
        } else {
            str += "\nIt's super effective";
        }

        if(first.fainted()){
            str += "\n" + first.getName() + " fainted";
            return str + "\n";
        }

        if(f != 0){
            if(rollStatusEffect(secondMove)){
                applyStatus(secondMove, first);
                //TODO
            }
        }

        return str + "\n";
    }

    public double calculateAttack(Pokemon attacker, Pokemon defender, AttackMove used){
        double power = used.getPower();
        double battleATK = attacker.getBattleATK();
        double battleDEF = attacker.getBattleDEF();
        //double def = defender.getDEF();
        double armorpen = battleATK / battleDEF;
        double damageTaken = 0.5 * power * armorpen * typeEffectiveness(defender, used); //https://gamepress.gg/pokemongo/damage-mechanics\

        double finalDMG = Math.floor(damageTaken) + 1;

        return finalDMG;

    }

    //calculate the type effectiveness multiplier
    public double typeEffectiveness(Pokemon defender, AttackMove used){
        double typeEff = 1;

       // for (Type t : defender.getTypeA()){
            if(used.getType().isSuperEffectiveAgainst(defender.getTypeA())){
                //System.out.println("It was super effective!");
                typeEff *= 2;
            } else if (used.getType().isNotVeryEffectiveAgainst(defender.getTypeA())){
                //System.out.println("It was not very effective...");
                typeEff *= 0.5;
            } else if (used.getType().hasNoEffectOn(defender.getTypeA())){
                //System.out.println("It had no effect!");
                typeEff *= 0;
            }
        //}   

        //for pokemon that only have a single type
        if(defender.getTypeB() == Type.NULLTYPE){
            return typeEff;
        }

        //secondary types
        //for (Type t : defender.getTypeB()){
            if(used.getType().isSuperEffectiveAgainst(defender.getTypeB())){
                typeEff *= 2;
            } else if (used.getType().isNotVeryEffectiveAgainst(defender.getTypeB())){
                typeEff *= 0.5;
            } else if (used.getType().hasNoEffectOn(defender.getTypeB())){
                typeEff *= 0;
            }
        //}
        return typeEff;
    } 

    private void applyStatus(AttackMove used, Pokemon defender){
        final Status s = used.getStatusEffect();
        
        switch(s){
            case POISON:
                defender.getStatus()[0] = true;
                break;
            case PARALYZE:
                defender.getStatus()[1] = true;
                break;
            case BURN:
                defender.getStatus()[2] = true;
                break;
            case FREEZE:
                defender.getStatus()[3] = true;
                break;
            case SLEEP: 
                defender.getStatus()[4] = true;
                break;
            case IMMUNE:
                defender.getStatus()[5] = true;
                break;
            case SEED:
                defender.getStatus()[6] = true;
                break;
            default: //NORMAL status effect
                break;
        }
    }

    //used to check if status effect is applied
    private boolean rollStatusEffect(AttackMove used){
        Random r = new Random();
        int randInt = r.nextInt(100) + 1; //generate number from 1-100

        if(randInt <= used.getStatChance()){ //ex: There is a 30% chance of getting a number within 1-30 inside 100 numbers
            return true;
        }

        return false;
    }
   
}