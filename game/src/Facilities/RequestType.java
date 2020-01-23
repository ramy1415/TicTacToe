/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facilities;

/**
 *
 * @author Mahmoud
 */
public enum RequestType {
    LOGIN,
    LOGIN_SUCCESS,
    LOGIN_FAILURE,
    REGISTER,
    REGISTER_SUCCESS,
    REGISTER_FAILURE,
    ASKTOPLAY,
    ACCEPT, 
    REJECT,MOVE, NAMES,NOTFOUND,LOSE,TIE,CHANGETURN,
    ALREADY_LOGINNED,
    WINNING_GAMES,
    LOSING_GAMES, PLAYING,NOTPLAYING,LEAVE,
    UPDATE_SCORES,
    RETURNNING_SCORES, REMATCH, REMATCHACCEPT
}
