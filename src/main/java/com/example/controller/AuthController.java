package com.example.controller;

import com.example.config.JwtProvider;
import com.example.modal.User;
import com.example.repository.UserRepository;
import com.example.response.AuthResponse;
import com.example.service.CustomUserDetailsService;
import com.example.service.EmailService;
import com.example.service.TwoFactorOTP;
import com.example.service.TwoFactorOTPService;
import com.example.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private TwoFactorOTPService twoFactorOTPService;

    @Autowired
    private EmailService emailService;

    @PostMapping(value = "/signup", produces = "application/json")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) throws Exception {
        User isEmailExist = userRepository.findByEmail(user.getEmail());

        if (isEmailExist != null) {
            throw new Exception("Email is already used with another account");
        }

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setUsername(user.getUsername());

        User savedUser = userRepository.save(newUser);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword()
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtProvider.generateToken(auth);

        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("Registered Successfully");
        res.setUserData(savedUser);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping(value = "/signin", produces = "application/json")
    public ResponseEntity<AuthResponse> login(@RequestBody User user) throws Exception {
        String username = user.getUsername();
        String email = user.getEmail();
        String password = user.getPassword();

        Authentication auth = authenticate(email, password);

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtProvider.generateToken(auth);

        User authUser = userRepository.findByEmail(email);

        if(user.getTwoFactorAuth().isInEnabled()){
            AuthResponse res = new AuthResponse();
            res.setMessage("Two factor auth is enabled");
            res.setTwoFactorAuthEnabled(true);
            String otp = OtpUtils.generateOtp();

            TwoFactorOTP oldTwoFactorOTP = twoFactorOTPService.findByUser(authUser.getId());
            if(oldTwoFactorOTP!=null){
                twoFactorOTPService.deleteTwoFactorOtp(oldTwoFactorOTP);
            }

            TwoFactorOTP newTwoFactorOtp = twoFactorOTPService.createTwoFactorOtp(authUser, otp, jwt);

            emailService.sendVerificationOtpEmail(email, otp);

            res.setSession(newTwoFactorOtp.getId());

            return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
        }

        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("Login Successfully");

        return new ResponseEntity<>(res, HttpStatus.FOUND);
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        if(userDetails==null){
            throw new BadCredentialsException("Invalid Email");
        }
        if(!password.equals(userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @PostMapping("/two-factor/otp/{otp}")
    public ResponseEntity<AuthResponse> verifySigningOtp(@PathVariable String otp, @RequestParam String id) throws Exception {
        TwoFactorOTP twoFactorOTP = twoFactorOTPService.findById(id);

        if(twoFactorOTPService.verifyTwoFactorOtp(twoFactorOTP, otp)){
            AuthResponse res = new AuthResponse();
            res.setMessage("Two factor authentication verified");
            res.setTwoFactorAuthEnabled(true);
            res.setJwt(twoFactorOTP.getJwt());

            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        throw new Exception("Invalid OTP");
    }
}
