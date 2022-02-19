package kr.co.rt.service;

import kr.co.rt.model.TodoEntity;
import kr.co.rt.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

@Slf4j
@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    /**
     * 테스트
     * @return 제목
     */
    public String testService() {
        TodoEntity todoEntity = TodoEntity.builder().title("My first todo item").build();
        todoRepository.save(todoEntity);
        TodoEntity savedEntity = todoRepository.findById(todoEntity.getId()).get();
        return savedEntity.getTitle();
    }

    /**
     * 투두 생성
     * @param todoEntity 투두 저장 정보
     * @return userId를 통한 실렉트 문
     */
    public List<TodoEntity> create(final TodoEntity todoEntity) {
        validate(todoEntity);

        todoRepository.save(todoEntity);

        log.info("Entity Id : {} is saved.", todoEntity.getId());

        return todoRepository.findByUserId(todoEntity.getUserId());
    }

    /**
     * 투두 조회
     * @param userId userId
     * @return userId를 통한 실렉트 문
     */
    public List<TodoEntity> retrieve(final String userId) {
        return todoRepository.findByUserId(userId);
    }

    /**
     * 투두 수정
     * @param todoEntity 투두 수정 정보
     * @return userId를 통한 실렉트 문
     */
    public List<TodoEntity> update(final TodoEntity todoEntity) {
        validate(todoEntity);

        final Optional<TodoEntity> todoEntityOptional = todoRepository.findById(todoEntity.getId());

        // 변환된 TodoEntity가 존재하면 값을 todoEntity 값으로 덮어 씌운다. p.145
        todoEntityOptional.ifPresent(todoOptEntity -> {
            todoOptEntity.setTitle(todoEntity.getTitle());
            todoOptEntity.setDone(todoEntity.getDone());

            todoRepository.save(todoOptEntity);
        });

        return retrieve(todoEntity.getUserId());
    }

    /**
     * 투두 삭제
     * @param todoEntity 투두 삭제 정보
     * @return userId를 통한 실렉트 문
     */
    public List<TodoEntity> delete(final TodoEntity todoEntity) {
        validate(todoEntity);

        try {
            todoRepository.delete(todoEntity);
        } catch (Exception exception) {
            log.error("error deleting entity ", todoEntity.getId(), exception);
            throw new RuntimeException("error deleting entity " + todoEntity.getId());
        }

        return retrieve(todoEntity.getUserId());
    }

    /**
     * 투두 엔티티 유효성 검사
     * @param todoEntity id, userId, title, done
     */
    private void validate(final TodoEntity todoEntity) {
        if (todoEntity == null) {
            log.warn("TodoEntity cannot be null.");
            throw new RuntimeException("TodoEntity cannot be null.");
        }

        if (todoEntity.getUserId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }
}