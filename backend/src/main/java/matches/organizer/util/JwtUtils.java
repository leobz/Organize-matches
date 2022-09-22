package matches.organizer.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import matches.organizer.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Component
public class JwtUtils {



    private static String secret = "This_is_secret";
    private static long expiryDuration = 60 * 60;

    public String generateJwt(User user){

        long milliTime = System.currentTimeMillis();
        long expiryTime = milliTime + expiryDuration * 1000;

        Date issuedAt = new Date(milliTime);
        Date expiryAt = new Date(expiryTime);

        // claims
        Claims claims = Jwts.claims()
                .setIssuer(user.getId().toString())
                .setIssuedAt(issuedAt)
                .setExpiration(expiryAt);

        // optional claims

        claims.put("idUser",user.getId().toString());
        claims.put("name", user.getFullName());
        claims.put("emailId", user.getEmail());
        claims.put("phone",user.getPhone());


        // generate jwt using claims
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public void verify(String authorization) throws Exception {

        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authorization).getBody();
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access denied.");
        }

    }
}