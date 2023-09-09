package br.dev.marco.usecase;

import io.smallrye.mutiny.Uni;

import java.rmi.NoSuchObjectException;

public interface Command<T,V> {
    Uni<V> execute(T t) throws NoSuchObjectException, NoSuchFieldException;
}
