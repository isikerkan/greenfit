package ch.pa5.greenfit.filter;

import ch.pa5.greenfit.service.UserService;
import gg.jte.TemplateEngine;
import gg.jte.output.WriterOutput;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.web.server.WebFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class PartialFilter implements Filter {

  private final TemplateEngine templateEngine;
  private final UserService userService;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse resp = (HttpServletResponse) response;
    String hxRequestHeader = req.getHeader("hx-request");

    val path =
        Optional.ofNullable(req.getQueryString())
            .map(query -> "%s?%s".formatted(req.getServletPath(), query))
            .orElse(req.getServletPath());

    if (path.startsWith("/icon/") || path.startsWith("/js/") || path.equals("/exit")) {
      chain.doFilter(request, response);
    } else {
      if (hxRequestHeader == null) {
        resp.setContentType("text/html");
        val output = new WriterOutput(resp.getWriter());
        val user = userService.findUser();
        templateEngine.render("index.jte", Map.of("currentPath", path, "user", user), output);
      } else {
        chain.doFilter(request, response);
      }
    }
  }
}
