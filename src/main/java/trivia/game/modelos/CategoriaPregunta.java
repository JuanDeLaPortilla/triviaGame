package trivia.game.modelos;

public class CategoriaPregunta {
    //Atributos
    private Long id;
    private String contenido;

    //Constructores
    public CategoriaPregunta() {
    }

    public CategoriaPregunta(Long id, String contenido) {
        this.id = id;
        this.contenido = contenido;
    }

    //Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
