package be.howest.ti.game.web.tokens;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class JsonWebToken implements TokenManager{

    private final String secretToken;
    private final Algorithm algorithm;

    public JsonWebToken() {
        this.secretToken = createSecret();
        this.algorithm = Algorithm.HMAC256(secretToken);
    }

    public String createToken(SplendorHTTPPlayer user) {

        String playerToken = JWT.create()
                .withIssuer("auth0")
                .withClaim("jti", UUID.randomUUID().toString())
                .withIssuedAt(Date.from(Instant.now()))
                .withClaim("gameId", user.getGameId())
                .withClaim("playerName", user.getPlayerName())
                .sign(algorithm);

        return playerToken;
    }

    private String createSecret() {
        SecureRandom random = new SecureRandom();
        byte[] secret = new byte[64];
        random.nextBytes(secret);

        StringBuilder sb = new StringBuilder();
        for (byte b : secret) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    @Override
    public <T> String createToken(T user) {
        if (user instanceof SplendorHTTPPlayer) {
            return createToken((SplendorHTTPPlayer) user);
        } else {
            throw new IllegalArgumentException("Unsupported user type");
        }
    }


    public Map<String, Claim> verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretToken);
            return JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build()
                    .verify(token).getClaims();
        } catch (JWTVerificationException exception) {
            //Invalid signature/claims
            throw new InvalidTokenException();
        }
    }

    @Override
    public SplendorHTTPPlayer parseToken(String token) {
        Map<String, Claim> verifiedToken = verifyToken(token);
        return new SplendorHTTPPlayer(verifiedToken.get("gameId").asInt(), verifiedToken.get("playerName").asString());
    }
}
