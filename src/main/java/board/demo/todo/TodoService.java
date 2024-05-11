package board.demo.todo;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;


@Service
public class TodoService {


	private TodoRepository todoRepository;

	public TodoService(TodoRepository todoRepository){
		this.todoRepository = todoRepository;
	}

	public List<Todo> findByUsername(String username) {
		return todoRepository.findByUsername(username);
	}

	@Transactional
	public void addTodo(String username, String description, LocalDate targetDate, boolean done) {
		if(description == null || description.length() < 10) {
				throw new IllegalArgumentException("10자 이상 입력해주셔야 등록이 됩니다.");
		}		// ??
		Todo todo = new Todo();
		todo.setUsername(username);
		todo.setDescription(description);
		todo.setTargetDate(targetDate);
		todo.setDone(done);
		todoRepository.save(todo);
	}

	@Transactional
	public void deleteById(int id) {
		todoRepository.deleteById(id);
	}

	public Optional<Todo> findById(int id) {
		return todoRepository.findById(id);
	}

	@Transactional
	public void updateTodo(Todo todo) {
		todoRepository.save(todo);
	}
}