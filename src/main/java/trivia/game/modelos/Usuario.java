package trivia.game.modelos;

import com.google.gson.annotations.Expose;

public class Usuario {

    //Atributos
    @Expose
    private Long id;
    @Expose
    private String nombre;
    private String correo;
    private String pass;
    private int esAdmin;

    //Constructores
    public Usuario() {
    }

    public Usuario(Long id, String nombre, String correo, String pass, int esAdmin) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.pass = pass;
        this.esAdmin = esAdmin;
    }

    public Usuario(String nombre, String correo, String pass, int esAdmin) {
        this.nombre = nombre;
        this.correo = correo;
        this.pass = pass;
        this.esAdmin = esAdmin;
    }

    //Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getEsAdmin() {
        return esAdmin;
    }

    public void setEsAdmin(int esAdmin) {
        this.esAdmin = esAdmin;
    }
}
