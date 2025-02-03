package godxero.repository;

import java.util.List;

public interface CrudRepository <T, I> extends SuperRepository {
	boolean save (T entity);
	T search (I id);
	boolean delete (I id);
	boolean update (T entity);
	List<T> getAll ();
}
