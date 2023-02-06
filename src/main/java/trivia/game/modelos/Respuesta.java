package trivia.game.modelos;

public class Respuesta {
    //Atributos
    private Long id;
    private Pregunta pregunta;
    private String contenido;
    private int esCorrecta;

    //Constructores
    public Respuesta() {
    }

    public Respuesta(Long id, Pregunta pregunta, String contenido, int esCorrecta) {
        this.id = id;
        this.pregunta = pregunta;
        this.contenido = contenido;
        this.esCorrecta = esCorrecta;
    }

    //Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public int getEsCorrecta() {
        return esCorrecta;
    }

    public void setEsCorrecta(int esCorrecta) {
        this.esCorrecta = esCorrecta;
    }
}
