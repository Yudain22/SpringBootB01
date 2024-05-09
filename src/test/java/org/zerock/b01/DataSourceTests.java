package org.zerock.b01;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.*;

@SpringBootTest
@Log4j2
public class DataSourceTests {
  @Autowired
  private DataSource dataSource;

  @Test
  public void testConnection() throws SQLException {
    @Cleanup Connection conn = dataSource.getConnection();
    @Cleanup PreparedStatement stmt = conn.prepareStatement("SELECT now(),?");
    stmt.setString(1, "asd");
    @Cleanup ResultSet rs = stmt.executeQuery();
    rs.next();
    log.info(rs.getString(1));
    log.info(rs.getString(2));
    Assertions.assertNotNull(conn);
  }
}
