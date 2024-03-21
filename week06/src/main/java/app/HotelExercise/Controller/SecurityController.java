package app.HotelExercise.Controller;

import app.HotelExercise.DAO.SecurityDAO;
import app.HotelExercise.DTO.TokenDTO;
import app.HotelExercise.Entities.User;
import app.HotelExercise.DTO.UserDTO;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import io.javalin.validation.ValidationException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;

import java.util.Date;
import java.util.List;

public class SecurityController {
    SecurityDAO securityDAO;
    ObjectMapper objectMapper;
    EntityManagerFactory emf;

    public SecurityController(EntityManagerFactory emf) {
        securityDAO = SecurityDAO.getUserDAOInstance(emf);
        this.emf = emf;
    }

    public String createToken(UserDTO user) {
        String ISSUER;
        String TOKEN_EXPIRE_TIME;
        String SECRET_KEY;

        if (System.getenv("DEPLOYED") != null) {
            ISSUER = System.getenv("ISSUER");
            TOKEN_EXPIRE_TIME = System.getenv("TOKEN_EXPIRE_TIME");
            SECRET_KEY = System.getenv("SECRET_KEY");
        } else {
            ISSUER = "Thomas Hartmann";
            TOKEN_EXPIRE_TIME = "1800000"; // 30 minutes in milliseconds
            SECRET_KEY = Utils.getPropertyValue("SECRET_KEY", "config.properties");
        }
        return createToken(user, ISSUER, TOKEN_EXPIRE_TIME, SECRET_KEY);
    }

    public User getVerifiedUser(String username, String password) throws ValidationException {
        try (EntityManager em = emf.createEntityManager()) {
            List<User> users = em.createQuery("SELECT u FROM User u").getResultList();
            users.stream().forEach(user -> System.out.println(user.getUsername() + " " + user.getPassword()));
            User user = em.find(User.class, username);
            if (user == null)
                throw new EntityNotFoundException("No user found with username: " + username); // RuntimeException
            user.getRoles().size(); // force roles to be fetched from db
            if (!user.verifyPassword(password))
                throw new RuntimeException("Wrong password");
            return user;
        }
    }

    public String createToken(UserDTO user, String ISSUER, String TOKEN_EXPIRE_TIME, String SECRET_KEY) {
        // https://codecurated.com/blog/introduction-to-jwt-jws-jwe-jwa-jwk/
        try {
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(user.getUsername())
                    .issuer(ISSUER)
                    .claim("username", user.getUsername())
                    .claim("roles", user.getRoles().stream().reduce((s1, s2) -> s1 + "," + s2).get())
                    .expirationTime(new Date(new Date().getTime() + Integer.parseInt(TOKEN_EXPIRE_TIME)))
                    .build();
            Payload payload = new Payload(claimsSet.toJSONObject());

            JWSSigner signer = new MACSigner(SECRET_KEY);
            JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
            JWSObject jwsObject = new JWSObject(jwsHeader, payload);
            jwsObject.sign(signer);
            return jwsObject.serialize();

        } catch (JOSEException e) {
            e.printStackTrace();
            throw new Exception("Could not create token");
        }
    }

    public Handler register() {
        return (ctx) -> {
            ObjectNode returnObject = objectMapper.createObjectNode();
            try {
                UserDTO userInput = ctx.bodyAsClass(UserDTO.class);
                User created = securityDAO.createUser(userInput.getUsername(), userInput.getPassword());

                String token = createToken(new UserDTO(created));
                ctx.status(HttpStatus.CREATED).json(new TokenDTO(token, userInput.getUsername()));
            } catch (EntityExistsException e) {
                ctx.status(HttpStatus.UNPROCESSABLE_CONTENT);
                ctx.json(returnObject.put("msg", "User already exists"));
            }
        };
    }

    public Handler login() {
        return (ctx) -> {
            ObjectNode returnObject = objectMapper.createObjectNode(); // for sending json messages back to the client
            try {
                UserDTO user = ctx.bodyAsClass(UserDTO.class);
                System.out.println("USER IN LOGIN: " + user);

                User verifiedUserEntity = securityDAO.getVerifiedUser(user.getUsername(), user.getPassword());
                String token = createToken(new UserDTO(verifiedUserEntity));
                ctx.status(200).json(new TokenDTO(token, user.getUsername()));

            } catch (EntityNotFoundException | ValidationException e) {
                ctx.status(401);
                System.out.println(e.getMessage());
                ctx.json(returnObject.put("msg", e.getMessage()));
            }
        };
    }

    public Handler createUser() {
        return ctx -> {
            String username = ctx.formParam("username");
            String password = ctx.formParam("password");
            ctx.json(securityDAO.createUser(username, password));
        };
    }

    public Handler createRole() {
        return ctx -> {
            String role = ctx.formParam("role");
            ctx.json(securityDAO.createRole(role));
        };
    }

    public Handler getAllUsers() {
        User[] users = securityDAO.getAllUsers().toArray(new User[0]);
        return ctx -> ctx.json(users).status(200);
    }

    public Handler authenticate() {
        // To check the users roles against the allowed roles for the endpoint (managed
        // by javalins accessManager)
        // Checked in 'before filter' -> Check for Authorization header to find token.
        // Find user inside the token, forward the ctx object with userDTO on attribute
        // When ctx hits the endpoint it will have the user on the attribute to check
        // for roles (ApplicationConfig -> accessManager)
        ObjectNode returnObject = objectMapper.createObjectNode();
        return (ctx) -> {
            if (ctx.method().toString().equals("OPTIONS")) {
                ctx.status(200);
                return;
            }
            String header = ctx.header("Authorization");
            if (header == null) {
                ctx.status(HttpStatus.FORBIDDEN).json(returnObject.put("msg", "Authorization header missing"));
                return;
            }
            String token = header.split(" ")[1];
            if (token == null) {
                ctx.status(HttpStatus.FORBIDDEN).json(returnObject.put("msg", "Authorization header malformed"));
                return;
            }
            UserDTO verifiedTokenUser = verifyToken(token);
            if (verifiedTokenUser == null) {
                ctx.status(HttpStatus.FORBIDDEN).json(returnObject.put("msg", "Invalid User or Token"));
            }
            System.out.println("USER IN AUTHENTICATE: " + verifiedTokenUser);
            ctx.attribute("user", verifiedTokenUser);
        };
    }

}
