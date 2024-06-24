package seoulmate.membership;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String userId = request.getParameter("user_id");
        String userPassword = request.getParameter("user_password");

        // DB 연결 정보
        String jdbcDriver = "oracle.jdbc.driver.OracleDriver"; // JDBC 드라이버 클래스
        String dbUrl = "jdbc:oracle:thin:@14.42.124.21:1521:xe"; // DB URL
        String dbId = "C##KEAM"; // DB 사용자 아이디
        String dbPw = "1234"; // DB 사용자 비밀번호

        MemberDAO dao = new MemberDAO(jdbcDriver, dbUrl, dbId, dbPw);
        MemberDTO member = dao.getMemberDTO(userId, userPassword);

        if (member != null && member.getUser_id() != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", member);
            response.sendRedirect("Default.jsp"); // 로그인 성공 후 리디렉션
        } else {
            response.sendRedirect("login.jsp?error=1"); // 로그인 실패 시 리디렉션
        }
    }
}
