package web.org.webb_project_4.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import web.org.webb_project_4.dto.PointDto;
import web.org.webb_project_4.dto.PointRequest;
import web.org.webb_project_4.dto.PointResponse;
import web.org.webb_project_4.service.JwtService;
import web.org.webb_project_4.service.PointService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/result")
@RequiredArgsConstructor
@CrossOrigin
public class PointController {
    private final PointService pointService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<PointResponse> register(@RequestBody PointRequest pointRequest, @RequestHeader("Authorization") String authHeader){
        try{
            return ResponseEntity.ok(pointService.add
                    (
                            pointRequest,
                            jwtService.extractUsername(jwtService.extractJwtTokenFromHeader(authHeader))
                    )
            );
        }catch (UsernameNotFoundException | ChangeSetPersister.NotFoundException e){
            return (ResponseEntity<PointResponse>) ResponseEntity.notFound();
        }
    }

    @GetMapping
    public ResponseEntity<List<PointDto>> getAllByUsername(@RequestHeader("Authorization") String authHeader){
        try{
            return ResponseEntity.ok(pointService.getAllByUsername(jwtService.extractUsername(jwtService.extractJwtTokenFromHeader(authHeader))));
        }catch (UsernameNotFoundException e){
            return (ResponseEntity<List<PointDto>>) ResponseEntity.notFound();
        }
    }
}
