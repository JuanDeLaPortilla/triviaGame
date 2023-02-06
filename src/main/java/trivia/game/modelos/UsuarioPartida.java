package trivia.game.modelos;

public class UsuarioPartida {
    //Atributos
    private Usuario usuario;
    private Partida partida;
    private int puntaje;

    //Constructores
    public UsuarioPartida() {
    }

    public UsuarioPartida(Usuario usuario, Partida partida, int puntaje) {
        this.usuario = usuario;
        this.partida = partida;
        this.puntaje = puntaje;
    }

    //Getters y Setters
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
}
