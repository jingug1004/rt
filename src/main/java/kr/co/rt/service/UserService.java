package kr.co.rt.service;

import kr.co.rt.model.UserEntity;
import kr.co.rt.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 유저 생성
     *
     * @param userEntity 유저 저장 정보
     * @return userId를 통한 실렉트 문
     */
    public UserEntity create(final UserEntity userEntity) {
        validate(userEntity);

        final String email = userEntity.getEmail();

        if (userRepository.existsByEmail(email)) {
            log.warn("l~ Email already exists {}", email);
            throw new RuntimeException("l~ Email already exists");
        }

        return userRepository.save(userEntity);
    }

    /**
     * 자격 인증서, 자격증 가져옴
     *
     * @param email    이메일
     * @param password 패스워드
     * @return 이메일과 패스워드를 통한 자격 인증서
     */
    public UserEntity getByCredentials(final String email, final String password, final PasswordEncoder encoder) {
        final UserEntity originalUser = userRepository.findByEmail(email);

        if (originalUser != null && encoder.matches(password, originalUser.getPassword())) {
            return originalUser;
        }

        return null;
    }

//    /**
//     * 유저 조회
//     * @param userId userId
//     * @return userId를 통한 실렉트 문
//     */
//    public List<UserEntity> retrieve(final String userId) {
//        return userRepository.findByUserId(userId);
//    }
//
//    /**
//     * 유저 수정
//     * @param userEntity 유저 수정 정보
//     * @return userId를 통한 실렉트 문
//     */
//    public List<UserEntity> update(final UserEntity userEntity) {
//        validate(userEntity);
//
//        final Optional<UserEntity> todoEntityOptional = userRepository.findById(userEntity.getId());
//
//        // 변환된 TodoEntity가 존재하면 값을 userEntity 값으로 덮어 씌운다. p.145
//        todoEntityOptional.ifPresent(todoOptEntity -> {
//            todoOptEntity.setTitle(userEntity.getTitle());
//            todoOptEntity.setDone(userEntity.getDone());
//
//            userRepository.save(todoOptEntity);
//        });
//
//        return retrieve(userEntity.getUserId());
//    }
//
//    /**
//     * 유저 삭제
//     * @param userEntity 유저 삭제 정보
//     * @return userId를 통한 실렉트 문
//     */
//    public List<UserEntity> delete(final UserEntity userEntity) {
//        validate(userEntity);
//
//        try {
//            userRepository.delete(userEntity);
//        } catch (Exception exception) {
//            log.error("error deleting entity ", userEntity.getId(), exception);
//            throw new RuntimeException("error deleting entity " + userEntity.getId());
//        }
//
//        return retrieve(userEntity.getUserId());
//    }

    /**
     * 유저 엔티티 유효성 검사
     *
     * @param userEntity id, userId, title, done
     */
    private void validate(final UserEntity userEntity) {
        if (userEntity == null) {
            log.warn("UserEntity cannot be null.");
            throw new RuntimeException("UserEntity cannot be null.");
        }

        if (userEntity.getEmail() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown getEmail.");
        }
    }
}