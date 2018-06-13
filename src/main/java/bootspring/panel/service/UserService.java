package bootspring.panel.service;

import bootspring.panel.model.User;

public interface UserService {
  
 public User findUserByEmail(String email);
 
 public void saveUser(User user);
}
