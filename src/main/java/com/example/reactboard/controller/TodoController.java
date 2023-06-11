package com.example.reactboard.controller;

import com.example.reactboard.dto.ResponseDTO;
import com.example.reactboard.dto.TodoDTO;
import com.example.reactboard.model.TodoEntity;
import com.example.reactboard.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
public class TodoController {
    @Autowired
    private TodoService todoService;

    @GetMapping("/test")
    public ResponseEntity<?> testTodo(){
        String str = todoService.testService();
        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto){
        try{
            String temporaryUserId = "temporary-user"; //temporaryr user id

//             1. TodoEntity 로 변환한다.
            TodoEntity entity = TodoDTO.toEntity(dto);
//             2. id를 null로 초기화한다. 생성 당시에는 id가 없어야 하기 때문이다.
            entity.setId(null);
//             3. 임시 유저 아이디를 설정해준다. 이 부분은  4장 인증과 인가에서 수정할 예정이다. 지금은
//             인증과 인가 기능이 없으므로 한 유저(teporary-user)만 로그인 없이 사용 가능한 애플리케이션인 셈이다.
            entity.setUserId(temporaryUserId);
//             4. 서비스를 이용해 Todo엔티티를 생성한다.
             List<TodoEntity> entities = todoService.create(entity);
//             5. 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환한다.
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
//             6. 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다.
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
//             7. ResponseDTO를 리턴한다.
//             8. 혹시 예외가 나는 경우 dto 대신 error에 메시지를 넣어 리턴한다.
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e){
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response =  ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveTodoList(){
        String temporaryUserId = "temporary-user";

        List<TodoEntity> entities = todoService.retrieve(temporaryUserId);

        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto){
        String temporaryUserId = "temporary-user";

        // 1. dto를 entity로 변환한다.
        TodoEntity entity = TodoDTO.toEntity(dto);
        // 2. id를 temporaryUserId로 초기화한다. 여기는 4장 인증과 인가에서 수정할 예정이다.
        entity.setUserId(temporaryUserId);
        // 3. 서비스를 이용해 entity를 업데이트한다.
        List<TodoEntity> entities = todoService.update(entity);
        // 4. 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환한다.
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
        // 5. 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다.
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
        // 6. ResponseDTO를 리턴한다.
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto){
        try{
            String temporaryUserId = "temporary-user";

            // 1. TodoEntity로 변환한다.
            TodoEntity entity = TodoDTO.toEntity(dto);
            // 2. 임시유저 아이디를 설정해준다.
            entity.setUserId(temporaryUserId);
            // 3. 서비스를 이용해 entity를 삭제한다.
            List<TodoEntity> entities = todoService.delete(entity);
            // 4. 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO리스트로 변환한다.
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            // 5. 변환된 TodoDTO리스트를 이용해 ResponseDTO를 초기화한다.
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
            // 6. ResponseDTO를 리턴한다.
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            // 8. 혹시 예외가 나는 경우 dto대신 error에 메시지를 넣어 리턴한다.
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }


}
