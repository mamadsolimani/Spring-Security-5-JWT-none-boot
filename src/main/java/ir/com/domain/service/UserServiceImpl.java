package ir.com.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ir.com.domain.entities.UserApp;
import ir.com.domain.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserApp create(UserApp userApp) throws Exception {

		try {
			userApp = userRepository.create(userApp);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return userApp;
	}

	@Override
	public UserApp edit(UserApp userApp) throws Exception {

		try {
			userApp = userRepository.create(userApp);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return userApp;
	}
	
	@Override
	public boolean remove(Long id) throws Exception {
		
		boolean result = false;

		try {
			result = userRepository.remove(id);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public boolean exists(String username) throws Exception {
		
		boolean result = false;

		try {
			result = userRepository.exists(username);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	@Override
	public UserApp loadUserByUsername(String username) throws UsernameNotFoundException {

		UserApp userApp = null;

		try {
			userApp = userRepository.loadUserByUsername(username);

			if (userApp == null) {
				throw new UsernameNotFoundException("username not found");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return userApp;
	}

	@Override
	public UserApp findById(Long id) throws Exception {

		UserApp userApp = null;

		try {
			userApp = userRepository.findById(id);

			if (userApp == null) {
				throw new UsernameNotFoundException("username not found");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return userApp;
	}

	@Override
	public List<UserApp> findAll() throws Exception {

		List<UserApp> userApps = null;

		try {
			userApps = userRepository.findAll();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return userApps;
	}

}
