package kr.co.rt.service;

import kr.co.rt.model.TodoEntity;
import kr.co.rt.persistence.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by A on 2022-02-12 오후 9:58
 * rt / kr.co.rt.service
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

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;
//
//    public String testService() {
//        return "Test Service";
//    }

    public String testService() {
        TodoEntity todoEntity = TodoEntity.builder().title("My first todo item").build();
        todoRepository.save(todoEntity);
        TodoEntity savedEntity = todoRepository.findById(todoEntity.getId()).get();
        return savedEntity.getTitle();
    }
}