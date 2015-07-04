/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zachtronics.yodastories.zone.action;

/**
 *
 * @author Mike
 */
public enum ActionType {

    AREA_PORTAL,
    HEAL,
    HEAL_NOT_REQUIRED,
    PUZZLE_COMPLETE,
    TALK,
    UNKNOWN;

    public static ActionType parse(int type) {
        switch(type) {
            case 1:
                System.out.println("Action Type " + type + " = AREA_PORTAL");
                return AREA_PORTAL;

            case 2:
                System.out.println("Action Type " + type + " = PUZZLE_COMPLETE");
                return PUZZLE_COMPLETE;

            case 3:
                System.out.println("Action Type " + type + " = TALK");
                return TALK;

            case 4:
                System.out.println("Action Type " + type + " = HEAL");
                return HEAL;

            case 5:
                System.out.println("Action Type " + type + " = HEAL_NOT_REQUIRED");
                return HEAL_NOT_REQUIRED;

            default:
                System.out.println("Action Type " + type + " = UNKNOWN");
                return UNKNOWN;
        }
    }
}
