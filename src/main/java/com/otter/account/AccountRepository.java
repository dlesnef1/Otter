package com.otter.account;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by David on 3/14/16.
 */

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
}
