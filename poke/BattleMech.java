
package poke;

import java.lang.invoke.SwitchPoint;
import java.util.*;

public class BattleMech {
    public static ArrayList<Pokemon> myTeam = new ArrayList<Pokemon>();
    public static ArrayList<Pokemon> enemyTeam = new ArrayList<Pokemon>();

    private static int healPotion = 5, enemyHealPotion = 5,
                        ppRestore = 5, enemyPPRestore = 5,
                        fullHeal = 5, enemyFullHeal = 5,
                        switchCooldown = 1;
    private static boolean pokeSwitch = false, skipTurn = false;
    private static AttackMove attackSelected, enemyAttack;

    Random r = new Random();
    
    public static void main(String[] args){
        Pokemon poke1 = new Pokemon(Monsters.GARDEVOIR);
        Pokemon poke2 = new Pokemon(Monsters.DELPHOX);
        Pokemon poke3 = new Pokemon(Monsters.LUCARIO);
        Pokemon poke4 = new Pokemon(Monsters.BLASTOISE);
        Pokemon poke5 = new Pokemon(Monsters.ZAPDOS);
        Pokemon poke6 = new Pokemon(Monsters.SERPERIOR);

        Pokemon enemy1 = new Pokemon(Monsters.HAWLUCHA);
        Pokemon enemy2 = new Pokemon(Monsters.TYRANTRUM);
        Pokemon enemy3 = new Pokemon(Monsters.AURORUS);
        Pokemon enemy4 = new Pokemon(Monsters.GOURGEIST);
        Pokemon enemy5 = new Pokemon(Monsters.GOODRA);
        Pokemon enemy6 = new Pokemon(Monsters.GARDEVOIR);

        BattleMech bm = new BattleMech();
        Scanner sc = new Scanner(System.in);

        myTeam.add(poke1);
        myTeam.add(poke2);
        myTeam.add(poke3);
        myTeam.add(poke4);
        myTeam.add(poke5);
        myTeam.add(poke6);

        enemyTeam.add(enemy1);
        enemyTeam.add(enemy2);
        enemyTeam.add(enemy3);
        enemyTeam.add(enemy4);
        enemyTeam.add(enemy5);
        enemyTeam.add(enemy6);

        
        while(true){
            System.out.println("Starting HP for both Pokemon: ");
            System.out.println(myTeam.get(0).getName() + " " + myTeam.get(0).getBattleHP());
            System.out.println(enemyTeam.get(0).getName() + " " + enemyTeam.get(0).getBattleHP());
            System.out.println();

            System.out.println("Type in the move name you want to use");
            System.out.println("Fight"); 
            System.out.println("Heal");
            System.out.println("Restore PP");
            System.out.println("Cure");
            System.out.println("Switch");

            String playMaker = sc.nextLine();

            switch(playMaker){
                case "Heal":
                    if(healPotion == 0){
                        System.out.println("You ran out of heal potions!");
                        continue;
                    } else {
                        System.out.println("Select Pokemon you want to heal \n");
                        System.out.println(myTeam.get(0).getName());
                        System.out.println(myTeam.get(1).getName());
                        System.out.println(myTeam.get(2).getName());
                        System.out.println(myTeam.get(3).getName());
                        System.out.println(myTeam.get(4).getName());
                        System.out.println(myTeam.get(5).getName());

                        String pokeHealName = sc.nextLine();

                        for(Pokemon poke : myTeam){
                            if(poke.getName().equals(pokeHealName)){
                                bm.healPokemon(poke);
                                healPotion--;
                            }
                        }

                        bm.trainerAI();

                        if(enemyAttack != null){
                            System.out.println(bm.oneSidedFight(enemyTeam.get(0), myTeam.get(0), enemyAttack, "enemy"));
                        }

                        enemyAttack = null;
                    }

                    break;

                case "Restore PP":
                    if(ppRestore == 0){
                        System.out.println("You ran out of PP restores!");
                        continue;
                    } else {
                        System.out.println("Select Pokemon you want to restore PP for \n");
                        System.out.println(myTeam.get(0).getName());
                        System.out.println(myTeam.get(1).getName());
                        System.out.println(myTeam.get(2).getName());
                        System.out.println(myTeam.get(3).getName());
                        System.out.println(myTeam.get(4).getName());
                        System.out.println(myTeam.get(5).getName());

                        String pokeRestoreName = sc.nextLine();
                        
                        for(Pokemon poke : myTeam){
                            if(poke.getName().equals(pokeRestoreName)){
                                System.out.println("Select which move you want to restore PP for \n");
                                System.out.println(poke.getATK1().getName());
                                System.out.println(poke.getATK2().getName());
                                System.out.println(poke.getATK3().getName());
                                System.out.println(poke.getATK4().getName());

                                String moveRestoreName = sc.nextLine();

                                for(AttackMove atk : poke.getMoveSet()){
                                    if(atk.getName().equals(moveRestoreName)){
                                        //atk.addPP(10);
                                        poke.addPP(atk, 10);
                                        ppRestore--;
                                    }
                                }
                            }
                        }

                        bm.trainerAI();

                        if(enemyAttack != null){
                            System.out.println(bm.oneSidedFight(enemyTeam.get(0), myTeam.get(0), enemyAttack, "enemy"));
                        }

                    }

                    enemyAttack = null;

                    break;
                
                case "Cure":
                    if(fullHeal == 0){
                        System.out.println("You ran out of full heals!");
                        continue;
                    } else {
                        System.out.println("Select which pokemon you would like to fully cure \n");
                        System.out.println(myTeam.get(0).getName());
                        System.out.println(myTeam.get(1).getName());
                        System.out.println(myTeam.get(2).getName());
                        System.out.println(myTeam.get(3).getName());
                        System.out.println(myTeam.get(4).getName());
                        System.out.println(myTeam.get(5).getName());

                        String pokeCureName = sc.nextLine();

                        for(Pokemon poke : myTeam){
                            if(poke.getName().equals(pokeCureName)){
                                poke.getStatus()[0] = false;
                                poke.getStatus()[1] = false;
                                poke.getStatus()[2] = false;
                                poke.getStatus()[3] = false;
                                poke.getStatus()[4] = false;
                                poke.getStatus()[5] = false;
                                poke.getStatus()[6] = false;
                                fullHeal--;
                            }
                        }

                        bm.trainerAI();

                        if(enemyAttack != null){
                            System.out.println(bm.oneSidedFight(enemyTeam.get(0), myTeam.get(0), enemyAttack, "enemy"));
                        }
                        //enemyAttack.lowerPP();
                        enemyAttack = null;
                    }

                    break;
                
                case "Switch":
                    System.out.println("Select which pokemon you would like to switch to ");
                    System.out.println(myTeam.get(1).getName());
                    System.out.println(myTeam.get(2).getName());
                    System.out.println(myTeam.get(3).getName());
                    System.out.println(myTeam.get(4).getName());
                    System.out.println(myTeam.get(5).getName());

                    String pokeSwitchName = sc.nextLine();

                    for(Pokemon poke : myTeam){
                        if(poke.getName().equals(pokeSwitchName)){
                            if(poke.fainted()){
                                System.out.println("You can't switch to this pokemon!");
                            } else {
                                System.out.println("You switched " + myTeam.get(0).getName() + " with " + poke.getName());
                                Collections.swap(myTeam, 0, myTeam.indexOf(poke));
                            }
                            
                        }
                    }
                    bm.trainerAI();

                    if(enemyAttack != null){
                        System.out.println(bm.oneSidedFight(enemyTeam.get(0), myTeam.get(0), enemyAttack, "enemy"));
                    }
                    //enemyAttack.lowerPP();
                    enemyAttack = null;

                    break;
                
                default:
                    System.out.println("Select move for your pokemon to use \n");
                    System.out.println(myTeam.get(0).getATK1().getName() + " " + "(" + myTeam.get(0).getATK1().getType() + ") " + myTeam.get(0).checkPP(myTeam.get(0).getATK1()) + "/" + myTeam.get(0).getATK1().getTotalPP());
                    System.out.println(myTeam.get(0).getATK2().getName() + " " + "(" + myTeam.get(0).getATK2().getType() + ") " + myTeam.get(0).checkPP(myTeam.get(0).getATK2()) + "/" + myTeam.get(0).getATK2().getTotalPP());
                    System.out.println(myTeam.get(0).getATK3().getName() + " " + "(" + myTeam.get(0).getATK3().getType() + ") " + myTeam.get(0).checkPP(myTeam.get(0).getATK3()) + "/" + myTeam.get(0).getATK3().getTotalPP());
                    System.out.println(myTeam.get(0).getATK4().getName() + " " + "(" + myTeam.get(0).getATK4().getType() + ") " + myTeam.get(0).checkPP(myTeam.get(0).getATK4()) + "/" + myTeam.get(0).getATK4().getTotalPP());

                    String pokeAttackSelected = sc.nextLine();

                    for(AttackMove atk : myTeam.get(0).getMoveSet()){
                        if(atk.getName().equals(pokeAttackSelected)){
                            attackSelected = atk;
                        }
                    }

                    bm.trainerAI();
                    System.out.println("\n" + enemyAttack);

                    if(enemyAttack != null && attackSelected != null){
                        if(myTeam.get(0).getBattleSPD() >= enemyTeam.get(0).getBattleSPD()){
                            System.out.println(bm.battleOrder(myTeam.get(0), attackSelected, enemyTeam.get(0), enemyAttack));
                        } else {
                            System.out.println(bm.battleOrder(enemyTeam.get(0), enemyAttack, myTeam.get(0), attackSelected));
                        }
                        
                        

                    } else if (enemyAttack == null && attackSelected != null){
                        System.out.println(bm.oneSidedFight(myTeam.get(0), enemyTeam.get(0), attackSelected, "notEnemy"));
                    } else if (enemyAttack != null && attackSelected == null){
                        System.out.println(bm.oneSidedFight(enemyTeam.get(0), myTeam.get(0), enemyAttack, "enemy"));
                    }

                    enemyAttack = null; //reset the move selected for both sides
                    attackSelected = null;

                    break;
            }
            //AttackMove moveSelected = AttackMove.MOONBLAST;

        

            /*bm.trainerAI();
            System.out.println(attackSelected);
            //if player's pokemon has equal or faster speed
            if(attackSelected != null){
                
                if(poke1.getBattleSPD() >= enemy1.getBattleSPD()){
                    System.out.println(bm.battleOrder(poke1, moveSelected, enemy1, attackSelected));
                } else {
                    System.out.println(bm.battleOrder(enemy1, attackSelected, poke1, moveSelected));
                }
            }*/

            if(enemyTeam.get(0).fainted() && enemyTeam.get(1).fainted() && enemyTeam.get(2).fainted() && enemyTeam.get(3).fainted() && enemyTeam.get(4).fainted() && enemyTeam.get(5).fainted()) {
                System.out.println("You have defeated the enemy trainer!");
                break;

            } else if(myTeam.get(0).fainted() && myTeam.get(1).fainted() && myTeam.get(2).fainted() && myTeam.get(3).fainted() && myTeam.get(4).fainted() && myTeam.get(5).fainted()) {
                System.out.println("You have been defeated!");
                break;  

            } else if(myTeam.get(0).fainted()){
                System.out.println("\nSelect which pokemon you would like to switch to");
                System.out.println(myTeam.get(1).getName());
                System.out.println(myTeam.get(2).getName());
                System.out.println(myTeam.get(3).getName());
                System.out.println(myTeam.get(4).getName());
                System.out.println(myTeam.get(5).getName());

                String deadSwitchName = sc.nextLine();

                for(Pokemon poke : myTeam){
                    if(poke.getName().equals(deadSwitchName)){
                        System.out.println("You swapped " + myTeam.get(0).getName() + " to " + poke.getName());
                        Collections.swap(myTeam, 0, myTeam.indexOf(poke));
                    }
                }
            } else if(enemyTeam.get(0).fainted()){
                for(Pokemon poke : enemyTeam){
                    if(!poke.fainted()){
                        Collections.swap(enemyTeam, 0, enemyTeam.indexOf(poke)); //swap current pokemon to next pokemon
                        break;
                    }
                }
    
            } else {
                System.out.println("Ending HP for both Pokemon: ");
                System.out.println(myTeam.get(0).getName() + " " + myTeam.get(0).getBattleHP());
                System.out.println(enemyTeam.get(0).getName() + " " + enemyTeam.get(0).getBattleHP());
                System.out.println();

                System.out.println("Original HP for both Pokemon: ");
                System.out.println(myTeam.get(0).getName() + " " + myTeam.get(0).getTotalHP());
                System.out.println(enemyTeam.get(0).getName() + " " + enemyTeam.get(0).getTotalHP());
                System.out.println();
                System.out.println("----------------------------------------------------------------------------------------------");
                System.out.println();
            }
        }
    }

    //method used for move priority
    private String battleOrder(Pokemon first, AttackMove firstMove, Pokemon second, AttackMove secondMove){
        String str = first.getName() + " used " + firstMove.getName();
        double firstDamage = calculateAttack(first, second, firstMove), secondDamage = calculateAttack(second, first, secondMove);

        str += "\n" + battleStatusPrint(first);

        if(!skipTurn){
            str += "\n" + first.getName() + " deals " + firstDamage + " damage to " + second.getName();
            second.takeDamage(firstDamage);

            //check type effectiveness to print messages
            double d = typeEffectiveness(second, firstMove);

            if(d == 0){
                str += "\nIt has no effect on " + second.getName();
            } else if (d < 1){
                str += "\n It's not very effective";
            } else if (d > 1){
                str += "\nIt's super effective";
            }

            if(second.fainted()){
                str += "\n" + second.getName() + " fainted";
                str+=damageStatusPrint(first);
                first.lowerPP(firstMove);
                return str + "\n";
            }

            //only apply status if the attack has effect on the defending pokemon
            if(d != 0){
                if(rollStatusEffect(firstMove)){
                    applyStatus(firstMove, second, first);
                    if (firstMove.getStatusEffect() != Status.NORMAL && firstMove.getStatusEffect() != Status.IMMUNE){
                        str += "\n" + firstMove.getStatusEffect() + " is applied on " + second.getName();
                    }
                }
            }

        } else {
            skipTurn = false;
        }

        str += "\n" + battleStatusPrint(second);

        if(!skipTurn){

            str += "\n" + second.getName() + " used " + secondMove.getName();

            str += "\n" + second.getName() + " deals " + secondDamage + " damage to " + first.getName();
            first.takeDamage(secondDamage);

            double f = typeEffectiveness(first, secondMove);

            if(f == 0){
                str += "\nIt has no effect on " + first.getName();
            } else if (f < 1){
                str += "\n It's not very effective";
            } else if (f > 1){
                str += "\nIt's super effective";
            }

            if(first.fainted()){
                str += "\n" + first.getName() + " fainted \n";
                str += damageStatusPrint(second); //second pokemon takes damage regardless if it kills another pokemon

                first.lowerPP(firstMove);
                second.lowerPP(secondMove);
                /*if(myTeam.get(0).getBattleSPD() >= enemyTeam.get(0).getBattleSPD()){
                    firstMove.lowerPP();
                } else {
                    secondMove.lowerPP();
                }

                enemyAttack.lowerEnemyPP();*/
                return str + "\n";
            }

            if(f != 0){
                if(rollStatusEffect(secondMove)){
                    applyStatus(secondMove, first, second);
                    if(secondMove.getStatusEffect() != Status.NORMAL && secondMove.getStatusEffect() != Status.IMMUNE){
                        str += "\n" + secondMove.getStatusEffect() + " is applied on " + first.getName();
                    }
                    //TODO
                }
            }
        } else {
            skipTurn = false;
        }

        first.lowerPP(firstMove);
        second.lowerPP(secondMove);
        /*if(myTeam.get(0).getBattleSPD() >= enemyTeam.get(0).getBattleSPD()){
            firstMove.lowerPP();
        } else {
            secondMove.lowerPP();
        }*/

        //enemyAttack.lowerEnemyPP();

        System.out.println(damageStatusPrint(first));
        System.out.println(damageStatusPrint(second));

        return str + "\n";
    }

    private final String oneSidedFight(Pokemon attacker, Pokemon defender, AttackMove used, String attackerSide){
        String str = attacker.getName() + " used (one-side) " + used.getName();
        double damage = calculateAttack(attacker, defender, used);
        battleStatusPrint(attacker);

        if(!skipTurn){
            str += "\n" + attacker.getName() + " deals " + damage + " to " + defender.getName();
            defender.takeDamage(damage);

            double e = typeEffectiveness(defender, used);

            if(e == 0){
                str += "\nIt has no effect on " + defender.getName();
            } else if(e < 1){
                str += "\nIt's not very effective";
            } else if(e > 1){
                str += "\nIt's super effective";
            }

            if(e != 0){
                if(rollStatusEffect(used)){
                    applyStatus(used, defender, attacker);
                    if(used.getStatusEffect() != Status.NORMAL){
                        str += "\n" + used.getStatusEffect() + " is applied on " + defender.getName();
                    }
                }
            }

            if(attackerSide.equals("enemy")){
                //used.lowerEnemyPP();
                attacker.lowerPP(used);
                System.out.println("Lowered enemy pp");
            } else {
                //used.lowerPP();
                attacker.lowerPP(used);
            }
            
        } else {
            skipTurn = false;
        }

        System.out.println(damageStatusPrint(attacker));

        if(defender.fainted()){
            str += "\n" + defender.getName() + " fainted";
            return str;
        }

        
        System.out.println(damageStatusPrint(defender));

        return str;
    }

    private final String battleStatusPrint(Pokemon user){
        String str ="";
        //paralyze
        if(user.getStatus()[1]){
            if(r.nextInt(4) + 1 == 3){ //25% chance of paralysis
                str += "\n" + user.getName() + " is paralyzed!";
                skipTurn = true;
            }
        //frozen
        } else if(user.getStatus()[3]){
            if(r.nextInt(100) + 1 <= 20){ //10% chance to break out of frozen
                str += "\n" + user.getName() + " is no longer frozen!";
                skipTurn = false;
                user.getStatus()[3] = false;
            } else {
                str += "\n" + user.getName() + " is frozen!";
                skipTurn = true;
            }
        //sleep
        } else if(user.getStatus()[4]){
            if(user.getSleepCounter() > 0){ //if sleep timer hasnt gone down yet
                str += "\n" + user.getName() + " is asleep!";
                user.lowerSleepCounter();
                skipTurn = true;
            } else {
                str += "\n" + user.getName() + " awakened from its slumber!";
                user.getStatus()[4] = false;
                skipTurn = false;
            }
        //immune
        } 
        //work on other status effects

        return str;
    }

    private final String damageStatusPrint(Pokemon user){
        String str = "";
        //multiple if statements because the effects can stack
        if(user.getStatus()[0]){
            double poisonDamage = Math.floor(user.getTotalHP() / 16);
            str += user.getName() + " is poisoned!";
            user.takeDamage(poisonDamage);
        } 

        if(user.getStatus()[2]){
            double burnDamage = Math.floor(user.getTotalHP() / 8);
            str += user.getName() + " is burnt!";
            user.takeDamage(burnDamage);
        }

        return str;


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

    private void applyStatus(AttackMove used, Pokemon defender, Pokemon attacker){
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
                attacker.getStatus()[5] = true;
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
        
        int randInt = r.nextInt(100) + 1; //generate number from 1-100

        if(randInt <= used.getStatChance()){ //ex: There is a 30% chance of getting a number within 1-30 inside 100 numbers
            return true;
        }

        return false;
    }

    private void healPokemon(Pokemon pokemonHealed){
        pokemonHealed.gainHP();
    }


    //https://hackaday.com/wp-content/uploads/2014/05/pokemon-decisiontree.png
    private void trainerAI(){
        double threshold = enemyTeam.get(0).getTotalHP() * 0.4; //find 40% of maximum hp
        boolean moveAvailable = false, healthSwitch = false;
        
        
        if(enemyTeam.get(0).getBattleHP() <= threshold){ //if fielded pokemon is below 40% health
            System.out.println("Below Threshold!");
            if (enemyHealPotion > 0 && !enemyTeam.get(0).healedOnce()){ //if enemy still have potions
                healPokemon(enemyTeam.get(0));
                System.out.println("\nEnemy healed " + enemyTeam.get(0).getName());
                enemyHealPotion--;
                enemyTeam.get(0).setHealedOnce();

                if (switchCooldown > 0){
                    switchCooldown--;
                }

            } else if (switchCooldown == 0){
                //switch to pokemon that will take the least damage

                //search for pokemon that will take least damage based on pokemon's type
                for(Pokemon p : enemyTeam){
                    if(!p.fainted() && !p.getName().equals(enemyTeam.get(0).getName()) && p.getBattleHP() > threshold){
                        if (p.getTypeA().isSuperEffectiveAgainst(myTeam.get(0).getTypeA())
                            || p.getTypeB().isSuperEffectiveAgainst(myTeam.get(0).getTypeA())
                            || p.getTypeA().isSuperEffectiveAgainst(myTeam.get(0).getTypeB())
                            || p.getTypeB().isSuperEffectiveAgainst(myTeam.get(0).getTypeB())){
                            System.out.println(enemyTeam.get(0).getName() + " switch with " + p.getName() + " healthSwitch!");
                            Collections.swap(enemyTeam, 0, enemyTeam.indexOf(p)); //switch current pokemon to pokemon that will take the least damage
                            
                            healthSwitch = true;

                            switchCooldown++;
                            break;
                        }
                    }
                }

                //desperate times, desperate measures
                if(!healthSwitch){
                    double max = enemyTeam.get(0).getBattleHP();

                    if(enemyTeam.get(1).getBattleHP() > max){
                        max = enemyTeam.get(1).getBattleHP();
                    } 
                    if (enemyTeam.get(2).getBattleHP() > max){
                        max = enemyTeam.get(2).getBattleHP();
                    } 
                    if (enemyTeam.get(3).getBattleHP() > max){
                        max = enemyTeam.get(3).getBattleHP();
                    } 
                    if (enemyTeam.get(4).getBattleHP() > max){
                        max = enemyTeam.get(4).getBattleHP();
                    } 
                    if (enemyTeam.get(5).getBattleHP() > max){
                        max = enemyTeam.get(5).getBattleHP();
                    }

                    for (Pokemon p : enemyTeam){
                        if (max == p.getBattleHP() && !p.getName().equals(enemyTeam.get(0).getName())){
                            System.out.println(enemyTeam.get(0).getName() + " switch with " + p.getName());
                            Collections.swap(enemyTeam, 0, enemyTeam.indexOf(p));
                            switchCooldown++;
                        }
                    }

                    healthSwitch = false;
                } 
            //if can't switch due to cooldown, find most damage move and full send
            } else {

                switchCooldown--;
                enemyAttack = maxDamageAttack();
            }
        } else {
            //loop through current pokemon's available moveset
            for(AttackMove mov : enemyTeam.get(0).getMoveSet()){
                if(typeEffectiveness(myTeam.get(0), mov) >= 1){ //if move is able to do neutral or supereffective damage
                    if(enemyTeam.get(0).checkPP(mov) > 0){
                        enemyAttack = mov;
                        //mov.lowerPP();
                        enemyTeam.get(0).lowerPP(mov);
                        moveAvailable = true;

                        if(switchCooldown > 0){
                            switchCooldown--;
                        }
                        break;
                    }
                }
            }

            //if all moves are not effective against opponent, switch to healthy pokemon that has effective moves
            if(!moveAvailable && switchCooldown == 0){
                for(Pokemon p : enemyTeam){
                    if (p.getTypeA().isSuperEffectiveAgainst(myTeam.get(0).getTypeA())
                        || p.getTypeB().isSuperEffectiveAgainst(myTeam.get(0).getTypeA())
                        || p.getTypeA().isSuperEffectiveAgainst(myTeam.get(0).getTypeB())
                        || p.getTypeB().isSuperEffectiveAgainst(myTeam.get(0).getTypeB())){
                            if(p.getBattleHP() > (p.getTotalHP() * 0.4)){
                                System.out.println(enemyTeam.get(0).getName() + " switch with " + p.getName() + " moveSwitch");
                                Collections.swap(enemyTeam, 0, enemyTeam.indexOf(p));

                                if(switchCooldown < 1){
                                    switchCooldown++;
                                }
                            } else {
                                enemyAttack = maxDamageAttack();

                                if(switchCooldown > 0){
                                    switchCooldown--;
                                }
                            }
                        }
                }
            //find move that does most damage and full send
            } else if (!moveAvailable && switchCooldown > 0){
                enemyAttack = maxDamageAttack();

                switchCooldown--;

            }
        }

        //reset conditions
        healthSwitch = false;
        moveAvailable = false;
    }

    private AttackMove maxDamageAttack(){
        System.out.println("Calculating Max Attack");
        double maxDamage = calculateAttack(enemyTeam.get(0), myTeam.get(0), enemyTeam.get(0).getATK1());
        AttackMove maxMove = null;

        if(enemyTeam.get(0).checkPP(enemyTeam.get(0).getATK1()) > 0){
            maxMove = enemyTeam.get(0).getATK1();
        }

        if(calculateAttack(enemyTeam.get(0), myTeam.get(0), enemyTeam.get(0).getATK2()) > maxDamage && enemyTeam.get(0).checkPP(enemyTeam.get(0).getATK2()) > 0){
            maxDamage = calculateAttack(enemyTeam.get(0), myTeam.get(0), enemyTeam.get(0).getATK2());
            maxMove = enemyTeam.get(0).getATK2();
        }
        if(calculateAttack(enemyTeam.get(0), myTeam.get(0), enemyTeam.get(0).getATK3()) > maxDamage && enemyTeam.get(0).checkPP(enemyTeam.get(0).getATK3()) > 0){
            maxDamage = calculateAttack(enemyTeam.get(0), myTeam.get(0), enemyTeam.get(0).getATK3());
            maxMove = enemyTeam.get(0).getATK3();
        }
        if(calculateAttack(enemyTeam.get(0), myTeam.get(0), enemyTeam.get(0).getATK4()) > maxDamage && enemyTeam.get(0).checkPP(enemyTeam.get(0).getATK4()) > 0){
            maxDamage = calculateAttack(enemyTeam.get(0), myTeam.get(0), enemyTeam.get(0).getATK4());
            maxMove = enemyTeam.get(0).getATK4();
        }   
        
        return maxMove;
    }
   
}