/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zachtronics.yodastories.puzzle;

import tiled.core.Tile;

/**
 *
 * @author Mike
 */
public class Puzzle {

    private PuzzleType type;
    private RewardType reward;
    private String startText;
    private String endText;
    private String rewardText;
    private Tile item;

    public PuzzleType getType() {
        return type;
    }

    public void setType(PuzzleType type) {
        this.type = type;
    }

    public RewardType getReward() {
        return reward;
    }

    public void setReward(RewardType reward) {
        this.reward = reward;
    }

    public String getStartText() {
        return startText;
    }

    public void setStartText(String startText) {
        this.startText = startText;
    }

    public String getEndText() {
        return endText;
    }

    public void setEndText(String endText) {
        this.endText = endText;
    }

    public String getRewardText() {
        return rewardText;
    }

    public void setRewardText(String rewardText) {
        this.rewardText = rewardText;
    }

    public Tile getItem() {
        return item;
    }

    public void setItem(Tile item) {
        this.item = item;
    }
}
