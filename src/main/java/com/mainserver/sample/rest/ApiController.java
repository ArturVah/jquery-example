package com.mainserver.sample.rest;

import com.mainserver.sample.domain.Category;
import com.mainserver.sample.domain.QuestionHeader;
import com.mainserver.sample.domain.SubCategory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ApiController {

    private List<Category> allCategories = Arrays.asList(
            new Category(1, "Java"),
            new Category(2, "Networking"),
            new Category(3, "C#")
    );

    private List<SubCategory> allSubCategories = Arrays.asList(
            new SubCategory(1, "Java basics", 1),
            new SubCategory(2, "Stream API", 1),
            new SubCategory(3, "OSI Model", 2),
            new SubCategory(4, "Routers and switches", 2),
            new SubCategory(5, "Events and delegates", 3),
            new SubCategory(6, "LINQ", 3)
    );

    private List<QuestionHeader> allQuestionHeaders = Arrays.asList(
            new QuestionHeader(1, "What do you mean by Constructor?", 1),
            new QuestionHeader(2, "What is meant by Local variable and Instance variable?", 1),
            new QuestionHeader(3, "What is the difference between map and flatMap?", 2),
            new QuestionHeader(4, "What is the return type of Consumer functional interface?", 2)
    );


    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

    @GetMapping("/subCategories")
    public ResponseEntity<List<SubCategory>> getCategories(@RequestParam("categoryId") int categoryId) {
        List<SubCategory> subCategories = this.allSubCategories
                .stream()
                .filter(it -> it.getCategoryId() == categoryId)
                .collect(Collectors.toList());

        return new ResponseEntity<>(subCategories, HttpStatus.OK);
    }

    @GetMapping("/questions")
    ResponseEntity<List<QuestionHeader>> getQuestions(@RequestParam("topicId") int topicId) {
        List<QuestionHeader> questions = allQuestionHeaders
                .stream()
                .filter(it -> it.getTopicId() == topicId)
                .collect(Collectors.toList());

        return new ResponseEntity<>(questions, HttpStatus.OK);
    }
}