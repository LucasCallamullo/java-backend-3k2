package com.frc.isi.museo.servicios.interfaces;

public interface ILookUpOrPersistService<T> {

    T getOrCreateAutor(String descripcion);

}

public interface ILookUpOrPersistService<T> {

    T getOrCreateById(T id);
    T getOrCreateByName(T name);

}
