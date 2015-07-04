/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zachtronics.yodastories.character;

/**
 *
 * @author Mike
 */
public class GameCharacter {

    private String name;
    private CharacterType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(CharacterType type) {
        this.type = type;
    }
}
