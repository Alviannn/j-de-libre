package com.github.alviannn.delibre.sql;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Database {

    private Connection conn;

    /**
     * Handles opening a connection to the database (default values are hardcoded)
     */
    public void connect() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/delibre", "root", "");
    }

    public Connection getConnection() {
        return conn;
    }

    /**
     * Executes an SQL query without expecting a result (usually for something like INSERT statement)
     * With this we can save up some lines when using database
     *
     * @param sql    the SQL query
     * @param params the parameters (to replace all `?` accordingly)
     */
    public void query(String sql, Object... params) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            ps.execute();
        }
    }

    /**
     * Executes SQL queries from a file
     * With this we can save up some lines when using database
     *
     * @param file the file containing the SQL queries (to run multiple queries split them by using `;`)
     */
    public void query(File file) throws FileNotFoundException, SQLException {
        if (!file.exists()) {
            throw new FileNotFoundException("Cannot find file " + file.getAbsolutePath() + "!");
        }

        StringBuilder builder = new StringBuilder();
        List<String> queries = new ArrayList<>();

        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                String line = scan.nextLine().trim();
                builder.append(line);

                if (line.endsWith(";")) {
                    queries.add(builder.toString());
                    builder.setLength(0);
                }
            }
        }

        for (String query : queries) {
            this.query(query);
        }
    }

    /**
     * Executes an SQL query and expects for a result (usually for something like SELECT statement)
     * With this we can save up some lines when using database
     *
     * @param sql    the SQL query
     * @param params the parameters (to replace all `?` accordingly)
     * @return The result object, it has all needed attributes there like the {@link PreparedStatement} and {@link ResultSet}
     */
    public Results results(String sql, Object... params) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }

        ResultSet rs = ps.executeQuery();
        return new Results(ps, rs);
    }

}
