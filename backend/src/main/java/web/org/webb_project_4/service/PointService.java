package web.org.webb_project_4.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import web.org.webb_project_4.dto.PointDto;
import web.org.webb_project_4.dto.PointRequest;
import web.org.webb_project_4.dto.PointResponse;
import web.org.webb_project_4.model.Points;
import web.org.webb_project_4.model.Users;
import web.org.webb_project_4.repository.PointRepository;
import web.org.webb_project_4.repository.UserRepository;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PointService {
    private final UserRepository userRepository;
    private final PointRepository pointRepository;

    public PointResponse add(PointRequest request, String username) throws ChangeSetPersister.NotFoundException{
        long start = System.nanoTime();
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        Double x = request.getX();
        Double y = request.getY();
        Double r = request.getR();
        boolean isHit = PointUtil.check(x, y, r);
        String localTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        long executionTime = System.nanoTime()-start ;

        var result = Points.builder()
                .x(x)
                .y(y)
                .r(r)
                .localTime(localTime)
                .executionTime(executionTime)
                .result(isHit)
                .user(user)
                .build();
        System.out.println(result);
        pointRepository.save(result);
        return PointResponse.builder()
                .result(isHit)
                .localTime(localTime)
                .executionTime(executionTime)
                .build();
    }

    public List<PointDto> getAllByUsername(String username){
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<Points> pointsList = pointRepository.findAllByUserId(user.getId());
        return PointDto.addToPointDtoList(pointsList);
    }

    @Transactional
    public boolean deleteAllByUsername(String username){
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        pointRepository.deletePointByUserId(user.getId());
        return true;
    }
}
