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
                .withIssuer("TI-SplendorGameServer-Group-11")
                .withClaim("jti", UUID.randomUUID().toString())
                .withIssuedAt(Date.from(Instant.now()))
//                OPTIONAL parameter so that the token expires after a certain time (1 day)
//                .withExpiresAt(Date.from(Instant.now().plusSeconds(60 * 60 * 24)))
                .withClaim("gameId", user.getGameId())
                .withClaim("playerName", user.getPlayerName())
                .sign(algorithm);
        // Creates a token with the player name and game id
        // The token is signed with the secret token
        // The token is encrypted with SHA256
        // "jti" is a unique identifier for the token
        // "iat" is the time the token was issued

        return playerToken;
    }

    private String createSecret() {
        // Generates a random secret token
        // The secret token is 256 bytes long
        SecureRandom random = new SecureRandom();
        byte[] secret = new byte[256];
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
                    .withIssuer("TI-SplendorGameServer-Group-11")
                    .build()
                    .verify(token).getClaims();
            // Verifies the token with the secret token
            // Verifying : JWT has the last part of the token as a signature which it then checks with the secret token and the algorithm
            // The token is verified with SHA256
            // This returns the claims of the token already decoded (Base64)
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
