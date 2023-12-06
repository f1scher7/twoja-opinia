package twojaOpinia.dao;

public interface InterfaceDAO <T, A>
{
	public T getByID(A id);
	public void insert(T input);
}
