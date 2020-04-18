package com.app.application;

import connection.DbConnection;

import javax.persistence.EntityManager;

public class App {
    public static void main(String[] args) {

        EntityManager entityManager = DbConnection.getInstance().getEntityManagerFactory().createEntityManager();

    }
}
