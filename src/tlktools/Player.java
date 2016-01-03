/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tlktools;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author EDDY
 */
public class Player extends Army{
    static List<String> player;
    
    /**
     * To parse a row in the army List, which represents a player.
     * @param s 
     */
    public Player(String s[]) {
        player = new ArrayList<>();
        player.add(s[0]);
        player.add(s[1]);
        player.add(s[2]);
        player.add(s[3]);
        player.add(s[4]);
        player.add(s[5]);
        
    }
    
    public boolean isOn() {
        if (player.get(0).equals("ON")) {
            return true;
        } else {
            return false;
        }
    }
    
    public String getRank() {
        return player.get(1);
    }
    
    public String getPlayerName() {
        return player.get(2);
    }
    
    public int getTroopsStanding() {
        return Integer.parseInt(player.get(3));
    }
    
    public int getTroopsTotal() {
        return Integer.parseInt(player.get(4));
    }
    
    public int getMP() {
        return Integer.parseInt(player.get(5));
    }
    
}
