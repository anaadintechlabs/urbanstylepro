package com.urbanstyle.user.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.User;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	 
	 Optional<User> findByEmail(String email);

	// Optional<User> findByUsernameOrEmail(String username, String email);

	 List<User> findByIdIn(List<Long> userIds);

	 Optional<User> findByName(String username);

	 //Boolean existsByUsername(String username);

     Boolean existsByEmail(String email);

	Optional<User> findByIdAndBlocked(Long id, boolean b);

	List<User> findByUserType(String type, Pageable pg);

	List<User> findByUserTypeAndDeactivated(String string, boolean b, Pageable pg);

	long countByDeactivated(boolean b);
	

	@Query(" from User Where userType =?1")
	List<User> getAllUsers(String userType, Pageable pagable);

	long countByUserType(String userType);

	@Query("from User Where userType =?1 and joinDate between ?2 and ?3")
	List<User> getAllUsersByDateRange(String userType, Date startDate, Date endDate, Pageable pagable);

	
	@Query("Select u FROM User u where  u.userType =?1  AND "+
			"LOWER(u.name) LIKE %?2% OR LOWER(u.lastName) LIKE %?2% OR " +  
			"CONCAT(LOWER(u.name),' ',LOWER(u.lastName)) LIKE LOWER(concat('%',?2,'%'))" + 
			"OR CONCAT(LOWER(u.lastName),' ',LOWER(u.name)) LIKE LOWER(concat('%',?2,'%'))"+
			"OR LOWER(u.email) LIKE %?2%")
	List<User> getAllUsersBySearchString(String userType, String searchString, Pageable pagable);

	@Query("Select count(u) FROM User u where  u.userType =?1  AND "+
			"LOWER(u.name) LIKE %?2% OR LOWER(u.lastName) LIKE %?2% OR " +  
			"CONCAT(LOWER(u.name),' ',LOWER(u.lastName)) LIKE LOWER(concat('%',?2,'%'))" + 
			"OR CONCAT(LOWER(u.lastName),' ',LOWER(u.name)) LIKE LOWER(concat('%',?2,'%'))"+
			"OR LOWER(u.email) LIKE %?2%")
	long getAllUsersCountBySearchString(String userType, String searchString);

	@Query("select count(u) from User u Where u.userType =?1 and u.joinDate between ?2 and ?3")
	long getAllUsersCountByDateRange(String userType, Date startDate, Date endDate);
     

}
