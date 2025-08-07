package com.seniclass.server.domain.teacher.service;

import com.seniclass.server.domain.auth.dto.CareerRegisterRequest;
import com.seniclass.server.domain.teacher.domain.Career;
import com.seniclass.server.domain.teacher.domain.Teacher;
import com.seniclass.server.domain.teacher.repository.CareerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CareerService {

    private final CareerRepository careerRepository;

    public Career createCareer(CareerRegisterRequest request, Teacher teacher) {
        Career career = Career.createCareer(request.name(), request.type(), teacher);
        return careerRepository.save(career);
    }
}
