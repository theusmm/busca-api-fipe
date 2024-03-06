package br.com.theusmm.buscafipeapi.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
