package board.demo.todo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

//Database (MySQL) 
//Static List of todos => Database (H2, MySQL)

//JPA
// Bean -> Database Table

@Entity
@Getter
@Setter
public class Todo {

	public Todo() {
		
	}
	
	public Todo(int id, String username, String description, LocalDate targetDate, boolean done) {
		super();
		this.id = id;
		this.username = username;
		this.description = description;
		this.targetDate = targetDate;
		this.done = done;
	}

	@Id
	@GeneratedValue
	private int id;

	private String username;
	
	@Size(min=10, message="10자 이상 입력해주시기 바랍니다.")
	private String description;
	private LocalDate targetDate;
	private boolean done;

	@Override
	public String toString() {
		return "Todo [id=" + id + ", username=" + username + ", description=" + description + ", targetDate="
				+ targetDate + ", done=" + done + "]";
	}

}
