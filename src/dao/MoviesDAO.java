package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import factory.ConnectionFactory;
import models.Movie;

public class MoviesDAO implements DAO<Movie> {

    private Connection conn;
    private PreparedStatement pstm;
    public ConnectionFactory connectionFactory;

    public MoviesDAO() {
        this.connectionFactory = new ConnectionFactory();
        this.conn = connectionFactory.createConnection();
    }

    public boolean salvar(Movie movie) {
        String sql = "INSERT INTO movies(titulo, ano, minutos, preco) VALUES (?, ?, ?, ?);";

        try {
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, movie.getTitle());
            pstm.setInt(2, movie.getReleaseYear());
            pstm.setDouble(3, movie.getDuration());
            pstm.setDouble(4, movie.getPrice());

            pstm.execute();
            pstm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public ArrayList<Movie> listar() {
        String sql = "SELECT * FROM movies;";
        ArrayList<Movie> movies = new ArrayList<>();
        ResultSet rset = null;

        try {
            pstm = conn.prepareStatement(sql);
            rset = pstm.executeQuery();

            while (rset.next()) {
                int id = rset.getInt("id");
                String title = rset.getString("titulo");
                int duration = rset.getInt("minutos");
                Double price = rset.getDouble("preco");
                int releaseYear = rset.getInt("ano");


                Movie movie = new Movie(id, title, price, duration, releaseYear);
                movies.add(movie);
            }

            pstm.close();
            rset.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return movies;
    }

    public boolean deletar(Movie movie) {
        String sql = "DELETE FROM movies WHERE id = ?";

        try {
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, movie.getId());

            pstm.execute();
            pstm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}

