package com.example.day1practice.Controller;

import com.example.day1practice.Api.ApiException;
import com.example.day1practice.Api.ApiResponse;
import com.example.day1practice.Model.Todo;
import com.example.day1practice.Model.User;
import com.example.day1practice.Service.TodoService;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/todo")
@RestController
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @GetMapping("/getAllTodos")
    public ResponseEntity<?> getAllTodos(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(todoService.getAllTodos(user.getId()));
    }
    // اي اند بوينت مافيها AuthenticationPrincipal   ، معناها تكون PermitAll
    // @AuthenticationPrincipal هذا الأنوتيشن لازم نمرر معه يوزر اوبجيكت نعتبره الي دخله اليوزر وبعدها ياخذ الأي دي حقه ويشيك بالداتا بيس وبعدها بيرسل للسيرفس الأي دي بسسس
    @GetMapping("/getMyTodos")
    public ResponseEntity<?> getMyTodos(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(todoService.getMyTodos(user.getId()));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTodo(@RequestBody @Valid Todo todo,@AuthenticationPrincipal User user ){
        todoService.createTodo(todo, user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("created"));
    }

    @DeleteMapping("/deleteTodo/{todoId}")
    public ResponseEntity<?> deleteTodo(@AuthenticationPrincipal User user ,@PathVariable Integer todoId){
        todoService.deleteTodo(user.getId(),todoId);
        return ResponseEntity.status(200).body(new ApiResponse("deleted"));
    }

    @PutMapping("/updateTodo/{todoId}")
    public ResponseEntity<?> updateTodo (@AuthenticationPrincipal User user, @PathVariable Integer todoId, @RequestBody @Valid Todo todo){
        todoService.updateTodo(user.getId(), todoId,todo);
        return ResponseEntity.status(200).body(new ApiResponse("updated"));
    }

    @GetMapping("/getTodo/{id}")
    public ResponseEntity<?> getTodo(@PathVariable Integer id){
        todoService.getTodo(id);
        return ResponseEntity.status(200).body(new ApiResponse("created"));
    }

}
