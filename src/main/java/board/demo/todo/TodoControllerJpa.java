package board.demo.todo;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@SessionAttributes("name") // 세션에 name 속성을 저장. 사용자 이름을 세션에 유지

public class TodoControllerJpa {

	private final TodoService todoService; // TodoService 주입.

	@Autowired // 생성자 주입
	public TodoControllerJpa(TodoService todoService) {
		this.todoService = todoService;
	}

	@GetMapping("/list-todos") // "/list-todos" 경로로 GET 요청.
	public String listAllTodos(ModelMap model) {
		String username = getLoggedInUsername(); // 현재 로그인한 사용자의 이름.
		System.out.println(username); //
		List<Todo> todos = todoService.findByUsername(username); // 해당 사용자의 모든 할 일을 조회.
		model.addAttribute("todos", todos); // 조회된 to do list를 모델에 추가.
		return "listTodos"; // "listTodos" 뷰를 반환
	}

	@GetMapping("/add-todo") // "/add-todo" 경로로 GET 요청
	public String showNewTodoPage(ModelMap model) {
		System.out.println(model);
		model.addAttribute("todo", new Todo()); // Todo 객체를 모델에 추가.
		return "todo"; // "todo" 뷰를 반환하여 사용자에게 할 일 추가 폼으로 이동.
	}

	@PostMapping("/add-todo") // "/add-todo" 경로로 POST 요칭.
	public String addNewTodo(ModelMap model, Todo todo, BindingResult result) {
		System.out.println(model);
		if (result.hasErrors()) { // 폼 검증에서 에러가 발생하면,
			System.out.println("ERROR"); // 에러 메시지
			return "todo"; // todo 뷰를 반환하여 에러 메시지
		}
		todo.setUsername(getLoggedInUsername()); // Todo 객체에 사용자 이름을 설정.
		todoService.addTodo(todo.getUsername(), todo.getDescription(), todo.getTargetDate(), todo.isDone());
		return "redirect:/list-todos"; // 목록 페이지로 리다이렉션.
	}

	@GetMapping("/delete-todo") // "/delete-todo" 경로로 GET 요청.
	public String deleteTodo(@RequestParam int id) { // 요청에서 id 파라미터 받기
		todoService.deleteById(id); // 해당 id를 가진 할 일을 삭제
		System.out.println("DELETE"); // 콘솔에 삭제 알림을 출력
		return "redirect:/list-todos"; // 할 일 목록 페이지로 리다이렉션
	}

	@GetMapping("/update-todo") // "/update-todo" 경로로 GET 요청
	public String showUpdateTodoPage(@RequestParam int id, ModelMap model) {
		Todo todo = todoService.findById(id) // id를 가진 할 일들 조회
				.orElseThrow(() -> new IllegalArgumentException("Invalid todo Id:" + id)); // 조회에 실패하면 예외처리.
		model.addAttribute("todo", todo); // 조회된 할 일을 모델에 추가.
		return "u_todo"; // "u_todo" 뷰를 반환
	}

	@PostMapping("/update-todo") // "/update-todo" 경로로 POST 요청.
	public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) { // Todo 객체를 폼 데이터로 받아옵니다.
		if(result.hasErrors()) { //
			return "u_todo";
		}
		todo.setUsername(getLoggedInUsername()); // Todo 객체에 사용자 이름을 설정.
		todoService.updateTodo(todo); // 할 일을 업데이트
		return "redirect:/list-todos"; // 리다이렉션.
	}

	private String getLoggedInUsername() { // 현재 로그인한 사용자의 이름을 반환.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

	/***
	 * SecurityContextHolder: 애플리케이션의 보안 컨텍스트에 접근하기 위한 엔트리 포인트.
	 * 기본적으로 스레드 로컬을 사용하여, 현재 스레드에서 실행 중인 보안 관련 정보를 저장하고 관리.
	 *
	 * SecurityContext: Authentication 객체를 포함하고 있으며,
	 * 이 객체는 현재 인증된 사용자에 대한 세부 정보를 가지게 됨.
	 *
	 * Authentication: 현재 인증된 사용자의 정보를 나타내는 인터페이스.
	 * 인증된 사용자의 이름, 권한, 자격 증명(비밀번호 등)과 같은 정보
	 *
	 * SecurityContextHolder.getContext(): 현재 스레드의 SecurityContext를 반환.
	 * getContext().getAuthentication(): 현재 인증된 사용자에 대한 Authentication 객체를 반환
	 * authentication.getName(): 인증 객체에서 사용자 이름을 가져옴. 이 함수는 사용자의 식별자를 반환.
	 */
}