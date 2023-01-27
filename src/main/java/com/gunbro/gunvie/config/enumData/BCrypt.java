package com.gunbro.gunvie.config.enumData;

public enum BCrypt {
    STRENGTH(10);       //암호화 강도

    int strength;
    private BCrypt(int strength){
        this.strength = strength;
    }
    public int getStrength(){
        return strength;
    }
}
