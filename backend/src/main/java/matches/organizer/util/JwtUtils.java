package matches.organizer.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import matches.organizer.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class JwtUtils {

    private  List<String> tokenBlackList;
    Logger logger = LoggerFactory.getLogger(JwtUtils.class);



    private static String secret = "This_is_secret";
    private static long expiryDuration = 60 * 60;

    public JwtUtils(List<String> tokenBlackList) {
        this.tokenBlackList = tokenBlackList;
    }

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

        // generate jwt using claims
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public void verify(String authorization) throws Exception {
        try {
            if (isInBlackList(authorization)) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access denied.");
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authorization).getBody();
            logger.debug("THE TOKEN IS OK");

        } catch(Exception e) {
            logger.error("INVALID TOKEN: " + authorization + ". ACCESS DENIED");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access denied.");
        }

    }


    public void addTokenToBlacklist(String token){
        tokenBlackList.add(token);
        Iterator iter = tokenBlackList.iterator();
        logger.debug("Blacklist: ");
        while(iter.hasNext()){
            String itemList = (String)iter.next();
            logger.debug(itemList);
        }


    }

    private Boolean isInBlackList(String token){
        Boolean isInBlackList = tokenBlackList.contains(token);
        logger.debug("Is the token in the blacklist? " + isInBlackList);
        return isInBlackList;
    }

    public String getUserFromToken(String token) {
        try {
            String userID = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getIssuer();
            return userID;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access denied.");
        }
    }

    public Date getTokenExpirationDate(String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getExpiration();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access denied.");
        }
    }


}