package trivia.game.modelos;

public class Pregunta {
    //Atributos
    private Long id;
    private CategoriaPregunta categoria;
    private String contenido;

    //Constructores
    public Pregunta() {
    }

    public Pregunta(Long id, CategoriaPregunta categoria, String contenido) {
        this.id = id;
        this.categoria = categoria;
        this.contenido = contenido;
    }

    //Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoriaPregunta getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaPregunta categoria) {
        this.categoria = categoria;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
