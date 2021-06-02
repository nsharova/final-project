package com.company.nsharova.controller;

import com.company.nsharova.Paths;
import com.company.nsharova.extractor.Extractor;
import com.company.nsharova.model.entity.Course;
import com.company.nsharova.model.entity.Level;
import com.company.nsharova.service.CourseService;
import com.company.nsharova.validator.Validator;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddCourseCommand implements Command {

  private final Extractor<Course, HttpServletRequest> courseExtractor;
  private final Validator<Course> courseValidator;
  private final CourseService courseService;

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    String destination = Paths.ADD_COURSE_PAGE;

    if ("GET".equals(request.getMethod())) {
      request.setAttribute("levels", Level.values());
    } else if ("POST".equals(request.getMethod())) {
      Map<String, String> errors = new HashMap<>();

      Course course = courseExtractor.extractFrom(request);
      courseValidator.validate(course, errors);

      if (errors.isEmpty()) {
        courseService.create(course);
        destination = "/controller?command=courses";
      } else {
        HttpSession session = request.getSession();
        session.setAttribute("errors", errors);
        session.setAttribute("tempCourse", course);
        request.setAttribute("levels", Level.values());
      }
    }

    return destination;
  }

  @Override
  public String getName() {
    return "add_course";
  }
}
