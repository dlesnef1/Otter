package com.otter;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by David on 2/16/2016.
 */
@Repository
public interface BookRepository extends CrudRepository<Book, Long>{
    Book findByIsbn(Integer isbn);
}
