package bootspring.dao;

import org.springframework.data.repository.CrudRepository;

import bootspring.model.Task;


public interface TaskRepository extends CrudRepository<Task, Integer> {

}
