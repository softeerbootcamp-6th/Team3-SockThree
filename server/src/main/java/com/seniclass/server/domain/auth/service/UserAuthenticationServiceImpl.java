package com.seniclass.server.domain.auth.service;

import com.seniclass.server.domain.auth.domain.AuthenticatedUser;
import com.seniclass.server.domain.auth.enums.UserRole;
import com.seniclass.server.domain.auth.exception.errorcode.AuthErrorCode;
import com.seniclass.server.domain.student.domain.Student;
import com.seniclass.server.domain.student.enums.Gender;
import com.seniclass.server.domain.student.repository.StudentRepository;
import com.seniclass.server.domain.teacher.domain.Teacher;
import com.seniclass.server.domain.teacher.repository.TeacherRepository;
import com.seniclass.server.domain.user.domain.User;
import com.seniclass.server.domain.user.repository.UserRepository;
import com.seniclass.server.global.exception.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final PasswordService passwordService;

    @Override
    public AuthenticatedUser authenticate(String email, String password) {
        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new CommonException(AuthErrorCode.LOGIN_FAILED));

        if (!passwordService.matches(password, user.getPassword())) {
            throw new CommonException(AuthErrorCode.LOGIN_FAILED);
        }

        return new UserAdapter(user);
    }

    @Override
    public AuthenticatedUser findByIdAndValidate(String userId) {
        Long id = parseUserId(userId);
        User user =
                userRepository
                        .findById(id)
                        .orElseThrow(() -> new CommonException(AuthErrorCode.USER_NOT_FOUND));

        return new UserAdapter(user);
    }

    @Override
    public AuthenticatedUser findByEmailAndValidate(String email) {
        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new CommonException(AuthErrorCode.USER_NOT_FOUND));

        return new UserAdapter(user);
    }

    @Override
    public String createStudent(
            String name, String email, Integer age, Gender gender, String encodedPassword) {
        Student student = Student.createStudent(name, email, age, gender, encodedPassword);
        Student savedStudent = studentRepository.save(student);
        return savedStudent.getId().toString();
    }

    @Override
    public Teacher createTeacher(
            String name,
            String email,
            Integer age,
            Gender gender,
            String encodedPassword,
            String instruction) {
        Teacher teacher =
                Teacher.createTeacher(name, email, age, gender, encodedPassword, instruction);
        return teacherRepository.save(teacher);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private Long parseUserId(String userId) {
        try {
            return Long.parseLong(userId.split("_")[1]);
        } catch (Exception e) {
            throw new CommonException(AuthErrorCode.INVALID_TOKEN);
        }
    }

    private static class UserAdapter implements AuthenticatedUser {
        private final User user;

        public UserAdapter(User user) {
            this.user = user;
        }

        @Override
        public String getId() {
            return user.getRole().name() + "_" + user.getId();
        }

        @Override
        public String getEmail() {
            return user.getEmail();
        }

        @Override
        public String getPassword() {
            return user.getPassword();
        }

        @Override
        public UserRole getRole() {
            return user.getRole();
        }
    }
}
