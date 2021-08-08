package com.github.alviannn.delibre.sql;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Database {

    private Connection conn;

    public void connect() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/delibre", "root", "");
            System.out.println("Successfully connected to the DB!");
        } catch (Exception e) {
            System.out.println("Connection failure to the DB!");
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public void query(String sql, Object... params) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            ps.execute();
        }
    }

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

    public Results results(String sql, Object... params) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }

        ResultSet rs = ps.executeQuery();
        return new Results(ps, rs);
    }

}
