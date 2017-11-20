package app.guillen.com.munidenunciasapp.models;

/**
 * Created by guillen on 13/11/17.
 */

public class Usuario {

    public int id;
    public String username;
    public String nombres;
    public String correo;
    public String estado;

    public Usuario(){

    }

    public Usuario(int id, String username, String nombres, String correo, String estado) {
        this.id = id;
        this.username = username;
        this.nombres = nombres;
        this.correo = correo;
        this.estado = estado;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }




}
