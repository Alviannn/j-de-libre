package com.github.alviannn.delibre.models;


import java.sql.Date;

public class User {

    private final int id;
    private final String name, password;
    private final Date registeredDate;
    private final boolean admin;

    public User(int id, String name, String password, Date registeredDate, boolean admin) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.registeredDate = registeredDate;
        this.admin = admin;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public boolean isAdmin() {
        return admin;
    }

    public enum Field {
        ID("ID"),
        USERNAME("Username"),
        REGISTER_DATE("Register Date");

        private final String name;

        Field(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static String[] getFieldNames() {
            Field[] types = values();
            String[] names = new String[types.length];

            for (int i = 0; i < types.length; i++) {
                names[i] = types[i].getName();
            }

            return names;
        }
    }

}
