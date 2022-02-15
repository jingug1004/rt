package kr.co.rt.controller;

import kr.co.rt.dto.ResponseDTO;
import kr.co.rt.dto.TestRequestBodyDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
@RequestMapping("test")
public class TestController {

    @GetMapping("test")
    public String testController() {
        return "Hellow World1123";
    }

    @GetMapping("/{id}")
    public String testControllerWithPathVariables(@PathVariable(required = false) int id) {
        return "Hello World " + id;
    }

    @GetMapping("/testReqParam")
    public String testControllerReqParam(@RequestParam(required = false) int id) {
        return "Hello World testReqParam " + id;
    }

    @GetMapping("/testReqBody")
    public String testControllerReqParam(@RequestBody TestRequestBodyDTO testRequestBodyDTO) {
        return "Hello World testReqParam ID " + testRequestBodyDTO.getId() +
                " message: " + testRequestBodyDTO.getMessage();
    }

    @GetMapping("/testRespBody")
    public ResponseDTO<String> testControllerRespBody() {
        List<String> list = new ArrayList<>();
        list.add("Hello world! I'm ResponseDTO");
        ResponseDTO<String> responseDTO = ResponseDTO.<String>builder().data(list).build();
        return responseDTO;
    }

    @GetMapping("/testRespEntity")
    public ResponseEntity<?> testControllerRespEntity() {
        List<String> list = new ArrayList<>();
        list.add("Hello world! I'm ResponseDTO. and you got 400!");
        ResponseDTO<String> responseDTO = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.badRequest().body(responseDTO);
    }
}