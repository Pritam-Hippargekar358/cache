//package com.redis.cache.dto;
//
//import org.springframework.data.domain.AuditorAware;
//
//import java.util.Optional;
//
//public class AuditorAwareImpl implements AuditorAware<String> {
//
//    @Override
//    public Optional<String> getCurrentAuditor() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return Optional.empty();
//        }
//
//        // try catch is a workaround for ignore class cast exception when running tests
//
//        try {
//            SignedUser user = (SignedUser) authentication.getPrincipal();
//
//            return Optional.of(user.getUserId().toString());
//
//        } catch (ClassCastException e) {
//
//            try {
//                User user = (User) authentication.getPrincipal();
//
//                if (user.getUsername().equals("spring")) {
//                    // set value as userId, required in tests
//                    return Optional.of("testUser");
//                }
//
//                return Optional.of("testUser");
//
//            } catch (ClassCastException e1) {
//                // anonymousUser, in case of scheduled jobs
//                return Optional.of("anonymousUser");
//            }
//
//        }
//    }
//}
