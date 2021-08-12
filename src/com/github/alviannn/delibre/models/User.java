package com.github.alviannn.delibre.models;


import java.sql.Date;
import java.util.Arrays;
import java.util.Optional;

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
        ID("ID", "id", false),
        USERNAME("Username", "name", true),
        REGISTER_DATE("Register Date", "registerDate", false);

        private final String name, column;
        private final boolean searchable;

        Field(String name, String column, boolean searchable) {
            this.name = name;
            this.column = column;
            this.searchable = searchable;
        }

        public String getName() {
            return name;
        }

        public String getColumn() {
            return column;
        }

        public boolean isSearchable() {
            return searchable;
        }

        public static String[] getFieldNames() {
            return Arrays.stream(Field.values()).map(Field::getName).toArray(String[]::new);
        }

        public static Field fromName(String name) {
            Optional<Field> first = Arrays.stream(values())
                    .filter(f -> f.name.equals(name))
                    .findFirst();

            return first.orElse(null);
        }

    }

}
