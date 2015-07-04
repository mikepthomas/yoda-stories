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
public enum DataType {

    TILE,
    UNKNOWN;

    public static DataType parse(int type) {
        switch(type) {
            case 2:
                System.out.println("Data Type " + type + " = TILE");
                return TILE;

            default:
                System.out.println("Data Type " + type + " = UNKNOWN");
                return UNKNOWN;
        }
    }
}
