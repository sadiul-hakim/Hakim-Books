package org.learn.book_management_system.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> model = userRepository.findByEmail(username);
        if (model.isEmpty()) {
            log.warn("CustomUserDetailsService.loadUserByUsername() :: Could not find user with email {}", username);
            return new CustomUserDetails(new UserModel());
        }

        return new CustomUserDetails(model.get());
    }
}
