package godxero.repository;

import java.util.List;

public interface CrudRepository <T> extends SuperRepository {
	boolean save (T entity);
	T search (Integer id);
	boolean delete (Integer id);
	boolean update (T entity);
	List<T> getAll ();
}
