package com.pixelart.model;

import java.util.HashSet;
import java.util.Set;


public class UsuarioNormal extends Usuario {
    private boolean puedeCrear;
    private HashSet<Integer> dibujosCreados;
    private HashSet<Integer> dibujosPintados;

    public UsuarioNormal() {
        super();
        this.puedeCrear = true;
        this.dibujosCreados = new HashSet<>();
        this.dibujosPintados = new HashSet<>();
    }

    public UsuarioNormal(int idUsuario, String nombre, String hashContrasenia,
                         String salt, boolean activo, boolean puedeCrear,
                         Set<Integer> dibujosCreados, Set<Integer> dibujosPintados) {
        super(idUsuario, nombre, hashContrasenia, salt, activo, RolUsuario.NORMAL);
        this.puedeCrear = puedeCrear;
        this.dibujosCreados = new HashSet<>(dibujosCreados);
        this.dibujosPintados = new HashSet<>(dibujosPintados);
    }

    // Getters
    public boolean getPuedeCrear() {
        return puedeCrear;
    }

    public Set<Integer> getDibujosCreados() {
        return new HashSet<>(dibujosCreados);
    }

    public Set<Integer> getDibujosPintados() {
        return new HashSet<>(dibujosPintados);
    }

    // Setters
    public void setPuedeCrear(boolean puedeCrear) {
        this.puedeCrear = puedeCrear;
    }

    public void setDibujosCreados(Set<Integer> dibujosCreados) {
        this.dibujosCreados = new HashSet<>(dibujosCreados);
    }

    public void setDibujosPintados(Set<Integer> dibujosPintados) {
        this.dibujosPintados = new HashSet<>(dibujosPintados);
    }

    // Métodos de lógica de negocio - Dibujos Creados
    public boolean ingresarIdDibujoCreado(int idDibujoCreado) {
        if (!puedeCrear) {
            return false;
        }
        return dibujosCreados.add(idDibujoCreado);
    }

    public boolean eliminarDibujoCreado(int idDibujoCreado) {
        return dibujosCreados.remove(idDibujoCreado);
    }

    public boolean buscarDibujoCreado(int idDibujo) {
        return dibujosCreados.contains(idDibujo);
    }

    // Métodos de lógica de negocio - Dibujos Pintados
    public boolean ingresarIdDibujoPintado(int idDibujoPintado) {
        return dibujosPintados.add(idDibujoPintado);
    }

    public boolean eliminarDibujoPintado(int idDibujoPintado) {
        return dibujosPintados.remove(idDibujoPintado);
    }

    public boolean buscarDibujoPintado(int idDibujo) {
        return dibujosPintados.contains(idDibujo);
    }

    public int contarDibujosCreados() {
        return dibujosCreados.size();
    }

    public int contarDibujosPintados() {
        return dibujosPintados.size();
    }

    @Override
    public String toString() {
        return "UsuarioNormal{" +
                "id=" + getIdUsuario() +
                ", nombre='" + getNombre() + '\'' +
                ", puedeCrear=" + puedeCrear +
                ", dibujosCreados=" + dibujosCreados.size() +
                ", dibujosPintados=" + dibujosPintados.size() +
                '}';
    }
}