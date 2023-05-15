package com.project.visit;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VisitApplication {

	public static void main(String[] args) {
		SpringApplication.run(VisitApplication.class, args);

		List<Test> l = new ArrayList<>();
		l.stream().peek(t -> {
			t.setName(t.getName() + "13232");
			t.setNum(t.getNum() + 2);
		});
	}

	@Getter
	@Setter
	public static class Test {

		private String name;

		private Long num;
	}

}
