package org._3HCompany.microservices.uaa.security;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org._3HCompany.microservice.common.models.dto.application.base.BaseDTO;
import org._3HCompany.microservice.common.models.enums.ColumnEnum;
import org._3HCompany.microservice.common.models.enums.RequestTypeEnum;
import org._3HCompany.microservice.common.models.enums.TableEnum;
import org._3HCompany.microservices.uaa.util.ApplicationProperties;
import org._3HCompany.microservices.uaa.models.entity.Person;
import org._3HCompany.microservices.uaa.models.entity.UnRegisterMobile;
import org._3HCompany.microservices.uaa.models.entity.User;
import org._3HCompany.microservices.uaa.repository.PersonRepository;
import org._3HCompany.microservices.uaa.repository.UnRegisterMobileRepository;
import org._3HCompany.microservices.uaa.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UnRegisterMobileRepository unRegisterMobileRepository;
    private final PersonRepository personRepository;

    public BaseDTO generateRandomCode(String mobile) {

        Random random = new Random();
        StringBuilder randomCode = new StringBuilder();
        String pattern = "^09[0-9]{9}$";

        if (mobile.trim().matches(pattern)) {
            for (int i = 0; i < 5; i++) {
                int number = random.nextInt(10);
                randomCode.append(number);
            }
        } else {
            return BaseDTO.getError(ApplicationProperties.getProperty("invalid-mobile-format"));
        }

        UnRegisterMobile unRegisterMobile = UnRegisterMobile.builder()
                .mobile(mobile.trim())
                .randomCode(randomCode.toString())
                .build();

        unRegisterMobileRepository.save(unRegisterMobile);
        BaseDTO baseDTO = BaseDTO.getSuccess(TableEnum.UnRegisterMobile, RequestTypeEnum.INSERT);
        baseDTO.setData(unRegisterMobile);
        return baseDTO;
    }

    @Transactional
    public BaseDTO register(RegisterRequest request) {

        List<String> errorMessage = userValidation(request);

        if (!errorMessage.isEmpty()) {
            return BaseDTO.getDuplicateError(TableEnum.USER, errorMessage);
        }

        Person person= Person.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .mobileNumber(request.getMobile())
                .email(request.getEmail())
                .mobileConfirmed(true)
                .build();
        person=personRepository.save(person);

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .person(person)
                .build();

        user = userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);

        AuthenticationResponseDto data = AuthenticationResponseDto.builder()
                .username(user.getUsername())
                .firstName(user.getPerson().getFirstName())
                .lastName(user.getPerson().getLastName())
                .token(jwtToken)
                .build();

        BaseDTO baseDTO = BaseDTO.getSuccess(TableEnum.USER, RequestTypeEnum.INSERT);
        baseDTO.setData(data);
        return baseDTO;
    }

    public BaseDTO authenticate(AuthenticationRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            return BaseDTO.getError(ApplicationProperties.getProperty("invalid-username-and-or-password"));
        }

        User user = userRepository.findUserByUsername(request.getUsername()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);

        AuthenticationResponseDto data = AuthenticationResponseDto.builder()
                .username(user.getUsername())
                .firstName(user.getPerson().getFirstName())
                .lastName(user.getPerson().getLastName())
                .token(jwtToken)
                .build();

        BaseDTO baseDTO = BaseDTO.getSuccess(TableEnum.USER, RequestTypeEnum.GET);
        baseDTO.setData(data);
        return baseDTO;

    }

    public BaseDTO checkUserAuthentication() {
        return jwtService.parseToken();
    }

    private List<String> userValidation(RegisterRequest request) {
        List<String> errorMessage = new ArrayList<>();

        if (!request.getPassword().equals(request.getConfirmPassword()))
            errorMessage.add(ColumnEnum.PASSWORD.getTitle());

        if (userRepository.findUserByUsername(request.getUsername()).isPresent())
            errorMessage.add(ColumnEnum.USERNAME.getTitle());

        if (userRepository.findUserByPersonEmail(request.getEmail()).isPresent())
            errorMessage.add(ColumnEnum.EMAIL.getTitle());

        if (userRepository.findUserByPersonMobileNumber(request.getMobile()).isPresent())
            errorMessage.add(ColumnEnum.MOBILE_NUMBER.getTitle());

        return errorMessage;
    }

}
