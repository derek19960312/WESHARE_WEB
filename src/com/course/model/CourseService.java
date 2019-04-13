package com.course.model;

import java.util.ArrayList;
import java.util.List;

public class CourseService {
	
	private CourseDAO_interface dao;
	
	public CourseService() {
		dao = new CourseDAO();
	}

	public void addCourse(CourseVO courseVO) {
		
	}
	public List<CourseVO> getAll(){
		List<CourseVO> list = new ArrayList<>();
		
		return list;
	}
}

