package org._3HCompany.microservices.uaa.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org._3HCompany.microservice.common.models.dto.application.base.BaseDTO;
import org._3HCompany.microservice.common.models.enums.RequestTypeEnum;
import org._3HCompany.microservice.common.models.enums.TableEnum;
import org._3HCompany.microservices.uaa.util.ApplicationProperties;
import org._3HCompany.microservices.uaa.util.ApplicationUtilities;
import org._3HCompany.microservices.uaa.util.StringUtility;
import org._3HCompany.microservices.uaa.models.entity.User;
import org._3HCompany.microservices.uaa.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECURE_KEY = "7044736E745142676A4F485178674F76656250626F756D34533848524B625347";
    private final UserRepository userRepository;

    public JwtService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String extractUserName(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public BaseDTO parseToken() {

        BaseDTO baseDto;
        HttpServletRequest request = ApplicationUtilities.getInstance().getCurrentHttpRequest();
        MessageStatus messageStatus = getJwtTokenFromRequest(request);

        if (!messageStatus.getStatus().equals(HttpStatus.OK))
            return BaseDTO.getError(messageStatus.getMessage());

        String jwt = messageStatus.getMessage();
        Optional<User> user = userRepository.findUserByUsername(extractUserName(jwt));

        if (isTokenExpired(jwt)) {
            baseDto = BaseDTO.getError(ApplicationProperties.getProperty("token-is-expired"));
        } else if (user.isPresent()) {
            baseDto = BaseDTO.getSuccess(TableEnum.USER, RequestTypeEnum.GET);
            baseDto.setData(user.get());
        } else {
            baseDto = BaseDTO.getError(String.format(
                    ApplicationProperties.getProperty("not-found-error-message-detail-1"),
                    TableEnum.USER.getTitle()));
        }
        return baseDto;
    }

    MessageStatus getJwtTokenFromRequest(HttpServletRequest request) {

        if (StringUtility.stringIsNullOrEmpty(request.getHeader("Authorization"))) {
            return MessageStatus.builder().message("unauthenticated").status(HttpStatus.UNAUTHORIZED).build();
        }
        String bearerToken = request.getHeader("Authorization");
        if (StringUtility.isEmpty(bearerToken) || (!bearerToken.startsWith("Bearer ") && !bearerToken.startsWith("bearer ")))
            return MessageStatus.builder().message("unauthenticated_token").status(HttpStatus.UNAUTHORIZED).build();

        String jwt = bearerToken.substring(7);
        String exceptionKey = "unauthenticated_invalid";
        try {
            Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(jwt);
            return MessageStatus.builder().message(jwt).status(HttpStatus.OK).build();
        } catch (SignatureException ex) {
            //logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            //logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            exceptionKey = "unauthenticated_expired";
            //logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            //logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            //logger.error("JWT claims string is empty.");
        }
        return MessageStatus.builder().message(exceptionKey).status(HttpStatus.UNAUTHORIZED).build();
    }

    public String generateToken(UserDetails userDetails) {
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        claims.put("role", userDetails.getAuthorities());
        return generateToken(claims, userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 24)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return getTokenExpirtionDate(token).before(new Date(System.currentTimeMillis()));
    }

    private Date getTokenExpirtionDate(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                ;
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECURE_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
