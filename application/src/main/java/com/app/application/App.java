package com.app.application;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import connection.DbConnection;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {

        EntityManager entityManager = DbConnection.getInstance().getEntityManagerFactory().createEntityManager();
    }

}


