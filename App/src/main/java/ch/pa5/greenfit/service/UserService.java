package ch.pa5.greenfit.service;

import ch.pa5.greenfit.repository.UserOptionRepository;
import ch.pa5.greenfit.repository.UserRepository;
import ch.pa5.greenfit.repository.entity.UserEntity;
import ch.pa5.greenfit.repository.entity.UserOptionEntity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.security.Principal;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

  private final EntityManager entityManager;
  private final UserRepository userRepository;
  private final UserOptionRepository userOptionRepository;

  private Optional<String> getAuthenticatedUsername() {
    return Optional.ofNullable(SecurityContextHolder.getContext())
        .map(SecurityContext::getAuthentication)
        .map(Authentication::getName);
  }

  public UserEntity findUser() {
    val userNameOptional = getAuthenticatedUsername();
    if (userNameOptional.isEmpty()) {
      throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Not logged in");
    }
    return userRepository
        .findByExternalId(SecurityContextHolder.getContext().getAuthentication().getName())
        .or(this::saveUser)
        .orElseThrow();
  }

  @Transactional
  public Optional<UserEntity> saveUser() {
    val userName = getAuthenticatedUsername().orElseThrow();
    val userEntity = new UserEntity();
    userEntity.setExternalId(userName);
    val savedPortion = userRepository.save(userEntity);
    try {
      // first time users have an exception here
      entityManager.refresh(savedPortion);
    } catch (Exception ignored) {
    }
    return userRepository.findById(savedPortion.getId());
  }

  @Transactional
  public Optional<UserEntity> saveUserOption(UserOptionEntity userOptionEntity) {
    val user = findUser();
    user.getOptions().setAge(userOptionEntity.getAge());
    user.getOptions().setWeight(userOptionEntity.getWeight());
    user.getOptions().setHeight(userOptionEntity.getHeight());
    userRepository.save(user);
    entityManager.refresh(user);
    return userRepository.findById(user.getId());
  }
}
