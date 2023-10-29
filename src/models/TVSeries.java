package models;

public class TVSeries extends MediaItem {
    private int numberOfEpisodes;
    private int numberOfSeasons;

    public TVSeries(int id, String title, double price, int duration, int releaseYear, int numberOfEpisodes, int numberOFSeasons) {
        super(id, title, price, duration, releaseYear);
        this.numberOfEpisodes = numberOfEpisodes;
        this.numberOfSeasons = numberOFSeasons;
    }

    public TVSeries(String title, double price, int duration, int releaseYear, int numberOfEpisodes, int numberOFSeasons) {
        super(title, price, duration, releaseYear);
        this.numberOfEpisodes = numberOfEpisodes;
        this.numberOfSeasons = numberOFSeasons;        
    }
    
    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }
    
    @Override
    public String toString(){
        return "ID: " + this.getId() + " | Titulo: " + this.getTitle() + " | Preco por Episodio (diario): R$" + this.getPrice() + " | Duracao Total: " + this.getDuration() + " mins | Ano de Lancamento: " + this.getReleaseYear() + " | Temporadas: " + numberOfSeasons + " | Episodios: " + numberOfEpisodes;
    }
}