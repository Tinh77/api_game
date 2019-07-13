package com.example.demo.model;

import com.example.demo.entity.Game;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class GameModel {

    public boolean create(Game game){
        ofy().save().entity(game).now();
        return true;
    }

}
