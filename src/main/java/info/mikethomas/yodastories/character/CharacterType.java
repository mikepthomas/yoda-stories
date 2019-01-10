/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.mikethomas.yodastories.character;

/**
 *
 * @author Mike
 */
public enum CharacterType {

    ENEMY,
    ENEMY_HARD,
    ENEMY_INACTIVE,
    ENEMY_MILD,
    ENEMY_PATROL,
    ENEMY_ROBOT,
    ENEMY_ROBOT_HARD,
    ENEMY_ROBOT_MILD,
    ENEMY_SCARED,
    ENEMY_SIT,
    MAP_ITEM,
    PLAYER,
    UNKNOWN;

    public static CharacterType parse(int type) {
        switch(type) {
            case 0:
                return PLAYER;

            case 1:
                return ENEMY_HARD;

            case 2:
                return ENEMY_MILD;

            case 3:
                return ENEMY;

            case 4:
                return ENEMY_SIT;

            case 6:
                return ENEMY_INACTIVE;

           case 7:
                return ENEMY_ROBOT;

           case 8:
                return ENEMY_ROBOT_MILD;

           case 9:
                return ENEMY_ROBOT_HARD;

            case 10:
                return ENEMY_PATROL;

           case 11:
                return ENEMY_SCARED;

           case 12:
                return MAP_ITEM;

            default:
                return UNKNOWN;
        }
    }
}
