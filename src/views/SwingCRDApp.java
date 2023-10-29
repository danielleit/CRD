package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import models.Movie;
import models.TVSeries;
import dao.MoviesDAO;
import dao.TVSeriesDAO;

public class SwingCRDApp {
    private List<Movie> movies = new ArrayList<>();
    private List<TVSeries> tvSeriesArray = new ArrayList<>();
    private MoviesDAO movieDAO = new MoviesDAO();
    private TVSeriesDAO tvSeriesDAO = new TVSeriesDAO();

    private JFrame frame;

    public SwingCRDApp() {
        frame = new JFrame("CRD App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);

        JLabel titleLabel = new JLabel("Create Read Delete");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JButton cadastrarButton = new JButton("Cadastrar");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(0, 10, 0, 10);
        buttonPanel.add(cadastrarButton, constraints);

        JButton listarButton = new JButton("Listar");
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.insets = new Insets(0, 10, 0, 10);
        buttonPanel.add(listarButton, constraints);

        JButton removerButton = new JButton("Remover");
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.insets = new Insets(0, 10, 0, 10);
        buttonPanel.add(removerButton, constraints);

        JButton sobreButton = new JButton("Sobre");
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.SOUTHEAST;
        buttonPanel.add(sobreButton, constraints);

        sobreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAboutDialog();
            }
        });

        cadastrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showEntityCreationDialog();
            }
        });

        listarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showEntityListDialog();
            }
        });

        removerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showEntityRemovalDialog();
            }
        });

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void showEntityCreationDialog() {
        String[] entities = { "Movie", "TVSeries" };
        String selectedEntity = (String) JOptionPane.showInputDialog(frame, "Selecione a entidade a ser cadastrada:",
                "Cadastrar Entidade", JOptionPane.QUESTION_MESSAGE, null, entities, entities[0]);

        if (selectedEntity != null) {
            EntityCreationDialog dialog = new EntityCreationDialog(selectedEntity);
            dialog.pack();
            dialog.setVisible(true);
        }
    }

    private void showEntityListDialog() {
        String[] entities = { "Movie", "TVSeries" };
        String selectedEntity = (String) JOptionPane.showInputDialog(frame, "Selecione a entidade a ser listada:",
                "Listar Entidade", JOptionPane.QUESTION_MESSAGE, null, entities, entities[0]);

        movies = movieDAO.listar();
        tvSeriesArray = tvSeriesDAO.listar();
        if (selectedEntity != null) {
            JTable table = new JTable();
            DefaultTableModel model = (DefaultTableModel) table.getModel();

            model.addColumn("Título");
            model.addColumn("Ano de Lançamento");
            model.addColumn("Duração (minutos)");
            model.addColumn("Preço por Dia");

            if (selectedEntity.equals("Movie")) {
                for (Movie movie : movies) {
                    model.addRow(new Object[] {
                            movie.getTitle(),
                            movie.getReleaseYear(),
                            movie.getDuration(),
                            movie.getPrice()
                    });
                }
            } else if (selectedEntity.equals("TVSeries")) {
                model.addColumn("Temporadas");
                model.addColumn("Episódios");

                for (TVSeries tvSeries : tvSeriesArray) {
                    model.addRow(new Object[] {
                            tvSeries.getTitle(),
                            tvSeries.getReleaseYear(),
                            tvSeries.getDuration(),
                            tvSeries.getPrice(),
                            tvSeries.getNumberOfSeasons(),
                            tvSeries.getNumberOfEpisodes()
                    });
                }
            }

            JScrollPane scrollPane = new JScrollPane(table);
            JOptionPane.showMessageDialog(frame, scrollPane, "Listagem", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void showEntityRemovalDialog() {
        String[] entities = { "Movie", "TVSeries" };
        String selectedEntity = (String) JOptionPane.showInputDialog(frame, "Selecione a entidade a ser removida:",
                "Remover Entidade", JOptionPane.QUESTION_MESSAGE, null, entities, entities[0]);

        movies = movieDAO.listar();
        tvSeriesArray = tvSeriesDAO.listar();

        if (selectedEntity != null) {
            String[] entityNames;
            if (selectedEntity.equals("Movie")) {
                entityNames = movies.stream().map(Movie::getTitle).toArray(String[]::new);
            } else {
                entityNames = tvSeriesArray.stream().map(TVSeries::getTitle).toArray(String[]::new);
            }

            String selectedName = (String) JOptionPane.showInputDialog(frame, "Selecione a entidade a ser removida:",
                    "Remover Entidade", JOptionPane.QUESTION_MESSAGE, null, entityNames, entityNames[0]);

            if (selectedName != null) {
                if (selectedEntity.equals("Movie")) {
                    for (Movie movie : movies) {
                        if (movie.getTitle().equals(selectedName)) {
                            movieDAO.deletar(movie);
                        }
                    }
                    JOptionPane.showMessageDialog(frame, "Filme removido com sucesso!");
                } else {
                    for (TVSeries tvSeries : tvSeriesArray) {
                        if (tvSeries.getTitle().equals(selectedName)) {
                            tvSeriesDAO.deletar(tvSeries);
                        }
                    }
                    JOptionPane.showMessageDialog(frame, "Série de TV removida com sucesso!");
                }
            }
        }
    }

    private void showAboutDialog() {
        JOptionPane.showMessageDialog(frame,
                "Autor: \nDaniel Leite de Barros Teixeira",
                "Sobre",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private class EntityCreationDialog extends JDialog {
        private JTextField titleField;
        private JTextField releaseYearField;
        private JTextField durationMinutesField;
        private JTextField pricePerDayField;
        private JTextField seasonsField;
        private JTextField episodesField;

        public EntityCreationDialog(String entityType) {
            setTitle("Cadastro de " + entityType);
            setLayout(new GridLayout(0, 2));

            JLabel titleLabel = new JLabel("Título:");
            titleField = new JTextField();
            add(titleLabel);
            add(titleField);

            JLabel releaseYearLabel = new JLabel("Ano de Lançamento:");
            releaseYearField = new JTextField();
            add(releaseYearLabel);
            add(releaseYearField);

            JLabel durationMinutesLabel = new JLabel("Duração (minutos):");
            durationMinutesField = new JTextField();
            add(durationMinutesLabel);
            add(durationMinutesField);

            JLabel pricePerDayLabel = new JLabel("Preço por Dia:");
            pricePerDayField = new JTextField();
            add(pricePerDayLabel);
            add(pricePerDayField);

            if (entityType.equals("TVSeries")) {
                JLabel seasonsLabel = new JLabel("Quantidade de Temporadas:");
                seasonsField = new JTextField();
                add(seasonsLabel);
                add(seasonsField);

                JLabel episodesLabel = new JLabel("Quantidade de Episódios:");
                episodesField = new JTextField();
                add(episodesLabel);
                add(episodesField);
            }

            JButton cadastrarButton = new JButton("Cadastrar");
            add(new JLabel());
            add(cadastrarButton);

            cadastrarButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String title = titleField.getText();
                    int releaseYear = Integer.parseInt(releaseYearField.getText());
                    int durationMinutes = Integer.parseInt(durationMinutesField.getText());
                    double pricePerDay = Double.parseDouble(pricePerDayField.getText());

                    if (entityType.equals("Movie")) {
                        Movie movie = new Movie(title, pricePerDay, durationMinutes, releaseYear);
                        movieDAO.salvar(movie);
                        JOptionPane.showMessageDialog(EntityCreationDialog.this, "Filme cadastrado com sucesso!");
                    } else if (entityType.equals("TVSeries")) {
                        int seasons = Integer.parseInt(seasonsField.getText());
                        int episodes = Integer.parseInt(episodesField.getText());
                        TVSeries tvSeries = new TVSeries(title, pricePerDay, durationMinutes, releaseYear, episodes,
                                seasons);
                        tvSeriesDAO.salvar(tvSeries);
                        JOptionPane.showMessageDialog(EntityCreationDialog.this, "Série de TV cadastrada com sucesso!");
                    }

                    EntityCreationDialog.this.dispose();
                }
            });
        }
    }
}
