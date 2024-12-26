package com.project.bookseller;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @Data
    public static class TestClass {
        private String attribute;
    }

    @GetMapping("/")
    public ResponseEntity<TestClass> index() {
        TestClass testClass = new TestClass();
        testClass.setAttribute("Test");
        return ResponseEntity.ok(testClass);
    }
}
