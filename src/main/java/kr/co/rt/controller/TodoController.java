package kr.co.rt.controller;

import kr.co.rt.dto.ResponseDTO;
import kr.co.rt.dto.TodoDTO;
import kr.co.rt.model.TodoEntity;
import kr.co.rt.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by A on 2022-02-12 오후 1:12
 * rt / kr.co.rt.controller
 * No pain, No gain!
 * What :
 * Why :
 * How :
 * << 개정이력(Modification Information) >>
 * 수정일         수정자          수정내용
 * -------       --------       ---------------------------
 * 2018/04/01     김진국          최초 생성
 * 2017/05/27     이몽룡          인증이 필요없는 URL을 패스하는 로직 추가
 *
 * @author 개발팀 김진국
 * @version 1.0
 * @see
 * @since 2018/04/01
 */

@RestController
@RequestMapping("todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/test")
    public ResponseEntity<?> testTodo() {
        String str = todoService.testService();
        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDTO<String> responseDTO = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.ok().body(responseDTO);
    }

    /**
     * 투두(할 일) 생성
     *
     * @param userId  JWT Bearer 토큰 인증된 유저 아이디
     * @param todoDTO 단일 투두
     * @return 투두 리스트
     */
    @PostMapping
    public ResponseEntity<?> createTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO todoDTO) {
        try {
            TodoEntity todoEntity = TodoDTO.todoEntity(todoDTO);

            todoEntity.setId(null);
            todoEntity.setUserId(userId);
            todoEntity.setDone(false);

            List<TodoEntity> entities = todoService.create(todoEntity);
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception exception) {
            String error = exception.getMessage();
            ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    /**
     * 투두 리스트 조회
     *
     * @param userId JWT Bearer 토큰 인증된 유저 아이디
     * @return 투두 리스트
     */
    @GetMapping
    public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId) {
        List<TodoEntity> entities = todoService.retrieve(userId);
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(responseDTO);
    }

    /**
     * 투두 수정
     *
     * @param userId  JWT Bearer 토큰 인증된 유저 아이디
     * @param todoDTO 단일 투두
     * @return 투두 리스트
     */
    @PutMapping
    public ResponseEntity<?> updateTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO todoDTO) {
        TodoEntity todoEntity = TodoDTO.todoEntity(todoDTO);

        todoEntity.setUserId(userId);

        List<TodoEntity> entities = todoService.update(todoEntity);
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(responseDTO);
    }

    /**
     * 투두 삭제
     *
     * @param userId  JWT Bearer 토큰 인증된 유저 아이디
     * @param todoDTO 단일 투두
     * @return 투두 리스트
     */
    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO todoDTO) {
        try {
            TodoEntity todoEntity = TodoDTO.todoEntity(todoDTO);

            todoEntity.setUserId(userId);

            List<TodoEntity> entities = todoService.delete(todoEntity);
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception exception) {
            String error = exception.getMessage();
            ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}