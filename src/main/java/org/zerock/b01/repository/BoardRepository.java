package org.zerock.b01.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.zerock.b01.domain.Board;

public interface BoardRepository extends JpaRepository<Board,Long>, BoardSearch{
  @Query(value = "SELECT now()", nativeQuery = true)
  String getTime();
}
