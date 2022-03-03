package com.utn.tp4.utilitario;
import java.util.ArrayList;

public interface IConnectable <T> {
    public ArrayList<T> getAll() throws Exception;
    public T get(int id) throws Exception;
    public int getCount() throws Exception;
    public int insert(T obj) throws Exception;
}
