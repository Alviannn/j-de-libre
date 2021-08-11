package com.github.alviannn.delibre.models;


import java.sql.Date;
import java.util.Arrays;

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
        ID("ID", false),
        USERNAME("Username"),
        REGISTER_DATE("Register Date", false);

        private final String name;
        private final boolean searchable;

        Field(String name, boolean searchable) {
            this.name = name;
            this.searchable = searchable;
        }

        Field(String name) {
            this(name, true);
        }

        public String getName() {
            return name;
        }

        public boolean isSearchable() {
            return searchable;
        }

        public static String[] getFieldNames() {
            return Arrays.stream(Field.values()).map(Field::getName).toArray(String[]::new);
        }

    }

}
