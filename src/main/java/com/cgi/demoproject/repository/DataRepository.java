package com.cgi.demoproject.repository;

import com.cgi.demoproject.model.DatabaseFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends JpaRepository<DatabaseFile,String> {
}
