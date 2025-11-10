package com.pixelart.model;

import java.util.Objects;


public abstract class Usuario {
    private int idUsuario;
    private String nombre;
    private String hashContrasenia;
    private String salt;
    private boolean activo;
    private RolUsuario rolUsuario;

    public Usuario() {
        this.idUsuario = 0;
        this.nombre = "";
        this.hashContrasenia = "";
        this.salt = "";
        this.activo = true;
        this.rolUsuario = RolUsuario.NORMAL;
    }

    public Usuario(int idUsuario, String nombre, String hashContrasenia,
                   String salt, boolean activo, RolUsuario rolUsuario) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.hashContrasenia = hashContrasenia;
        this.salt = salt;
        this.activo = activo;
        this.rolUsuario = rolUsuario;
    }

    // Getters
    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getHashContrasenia() {
        return hashContrasenia;
    }

    public String getSalt() {
        return salt;
    }

    public boolean getActivo() {
        return activo;
    }

    public RolUsuario getRolUsuario() {
        return rolUsuario;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void setRolUsuario(RolUsuario rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return idUsuario == usuario.idUsuario;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", activo=" + activo +
                ", rol=" + rolUsuario +
                '}';
    }
}