package br.dev.marco.usecase;

import io.smallrye.mutiny.Uni;

public interface Command<T,V> {
    Uni<V> execute(T t);
}
