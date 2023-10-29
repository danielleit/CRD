package dao;

import java.util.List;

public interface DAO<T> {
    
    boolean salvar(T t);
    
    boolean deletar(T t);
    
    List<T> listar();
    
}