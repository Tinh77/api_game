package com.example.demo.controller;

import com.example.demo.entity.Game;
import com.example.demo.util.JsonApiObject;
import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class GameController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        List<Game>  list = ofy().load().type(Game.class).list();
        JsonApiObject jsonApiObject = new JsonApiObject();
        jsonApiObject.setStatus(HttpServletResponse.SC_OK);
        jsonApiObject.setMessage("OK");
        jsonApiObject.setGames(list);
        resp.getWriter().println(new Gson().toJson(jsonApiObject));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        BufferedReader reader = req.getReader();
        Game game = new Gson().fromJson(reader, Game.class);
        Key<Game> gameKey = ofy().save().entity(game).now();
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().print(new Gson().toJson(gameKey));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        BufferedReader reader = req.getReader();
        Game updateGame = new Gson().fromJson(reader, Game.class);
        updateGame.getId();
        Game existGame = ofy().load().type(Game.class).id(updateGame.getId()).now();
        if (existGame == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy game.");
            return;
        }
        existGame.setName(updateGame.getName());
        existGame.setRelease_date(updateGame.getRelease_date());
        existGame.setPrice(updateGame.getPrice());
        existGame.setThumbnail(updateGame.getThumbnail());
        existGame.setDecription(updateGame.getDecription());
        existGame.setType(Game.Type.findByValue(updateGame.getType()));

        ofy().save().entity(existGame).now();
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().print(new Gson().toJson(existGame));
    } // Patch


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        Game existGame = ofy().load().type(Game.class).id(id).now();
        if (existGame == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy game.");
            return;
        }
        existGame.setStatus(Game.Status.DELETED);
        Key<Game> key = ofy().save().entity(existGame).now();
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().print(new Gson().toJson(key));
    }
}
