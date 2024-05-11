package board.demo.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // 존재 이유 없으면??
public class SayHelloController {

	// "say-hello" URL에 대한 요청을 처리하여 "Hello! What are you learning today?"라는 문자열 반환
	// localhost:8080/say-hello를 요청하면, 실행되어 해당 문자열을 반환
	@RequestMapping("say-hello")
	@ResponseBody
	public String sayHello() {
		return "Hello! What are you learning today?";
	} // 어떤 결과가 실행될까??

	// URL에 대한 요청을 처리하여 HTML 형태의 문자열을 반환.
	// HTML 문서의 기본 구조를 문자열로 만들어 반환. 왜 이렇게?
	@RequestMapping("say-hello-html")
	@ResponseBody
	public String sayHelloHtml() {
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<title> My First HTML Page - Changed</title>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("My first html page with body - Changed");
		sb.append("</body>");
		sb.append("</html>");

		return sb.toString();
	}

	// URL에 대한 요청을 처리하고, "sayHello"라는 이름의 JSP 파일로 응답 전송.
	// JSP 파일은 서버에서 동적으로 HTML을 생성하여 클라이언트에게 전달.
	// /src/main/resources/META-INF/resources/WEB-INF/jsp/ 디렉토리 밑에 하는게 일반적.
	// jsp 파일이 이 요청을 처리하는데 사용
	@RequestMapping("say-hello-jsp")
	public String sayHelloJsp() {
		return "sayHello";
	}
}