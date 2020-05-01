package user;

import base.generic.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findManagerWithLowestNumOfCustomers();
}
