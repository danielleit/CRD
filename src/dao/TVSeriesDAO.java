package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import factory.ConnectionFactory;
import models.TVSeries;

public class TVSeriesDAO implements DAO<TVSeries> {

    private Connection conn;
    private PreparedStatement pstm;
    public ConnectionFactory connectionFactory;

    public TVSeriesDAO() {
        this.connectionFactory = new ConnectionFactory();
        this.conn = connectionFactory.createConnection();
    }

    public boolean salvar(TVSeries tvSeries) {
        String sql = "INSERT INTO tvseries(titulo, ano, temporadas, episodios, minutos, preco) VALUES (?, ?, ?, ?, ?, ?);";

        try {
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, tvSeries.getTitle());
            pstm.setInt(2, tvSeries.getReleaseYear());
            pstm.setInt(3, tvSeries.getNumberOfSeasons());
            pstm.setInt(4, tvSeries.getNumberOfEpisodes());
            pstm.setDouble(5, tvSeries.getDuration());
            pstm.setDouble(6, tvSeries.getPrice());

            pstm.execute();
            pstm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public ArrayList<TVSeries> listar() {
        String sql = "SELECT * FROM tvseries;";
        ArrayList<TVSeries> tvSeriesArray = new ArrayList<>();
        ResultSet rset = null;

        try {
            pstm = conn.prepareStatement(sql);
            rset = pstm.executeQuery();

            while (rset.next()) {
                int id = rset.getInt("id");
                String title = rset.getString("titulo");
                int numberOFSeasons = rset.getInt("temporadas");
                int numberOfEpisodes = rset.getInt("episodios");
                int duration = rset.getInt("minutos");
                Double price = rset.getDouble("preco");
                int releaseYear = rset.getInt("ano");


                TVSeries tvSeries = new TVSeries(id, title, price, duration, releaseYear, numberOfEpisodes, numberOFSeasons);
                tvSeriesArray.add(tvSeries);
            }

            pstm.close();
            rset.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tvSeriesArray;
    }

    public boolean deletar(TVSeries tvSeries) {
        String sql = "DELETE FROM tvseries WHERE id = ?";

        try {
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, tvSeries.getId());

            pstm.execute();
            pstm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
