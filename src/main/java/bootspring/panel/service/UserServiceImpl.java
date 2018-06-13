package bootspring.panel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bootspring.panel.model.Role;
import bootspring.panel.model.User;
import bootspring.repository.RoleRespository;
import bootspring.repository.UserRepository;
import java.util.Arrays;
import java.util.HashSet;

@Service("userService")
public class UserServiceImpl implements UserService {
 
	 @Autowired
	 private UserRepository userRepository;
	 
	 @Autowired
	 private RoleRespository roleRespository;
	
	 @Override
	 public User findUserByEmail(String email) {
	  return userRepository.findByEmail(email);
	 }
	
	 @Override
	 public void saveUser(User user) {
		  user.setPassword(user.getPassword());
		  user.setActive(1);
		  Role userRole = roleRespository.findByRole("ADMIN");
		  user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		  userRepository.save(user);
	 }

}
