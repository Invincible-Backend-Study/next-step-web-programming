package next.user.service;

import com.jwp.outbound.user.infrastructure.UserDao;
import core.annotation.Service;
import next.common.error.DomainExceptionCode;
import next.user.payload.request.CreateUserDto;


@Service
public class CreateUserService {

    private final UserDao userDao = UserDao.getInstance();


    public synchronized void execute(final CreateUserDto createUserDto) { // 동시에 서로 다른 쓰레드가 해당 메서드에 접근하는 것을 막음 (트랜잭션을 구현하지 못해서 lock 처리함)

        if (userDao.findById(createUserDto.getUserId()).isPresent()) { //중복 아이디 검증
            throw DomainExceptionCode.PREVIOUS_SIGN_UP_USER_ID.createError(createUserDto.getName());
        }
        if (userDao.findByName(createUserDto.getName()).isPresent()) { //중복 이름 검증
            throw DomainExceptionCode.PREVIOUS_SIGN_UP_USER_NAME.createError(createUserDto.getUserId());
        }

        //userDao.insert(createUserDto.toEntity());
    }

}
