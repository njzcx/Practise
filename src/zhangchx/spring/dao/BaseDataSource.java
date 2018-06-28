package zhangchx.spring.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class BaseDataSource {
	
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setJdbcTemplete(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public JdbcTemplate getJdbcTemplete() {
		return jdbcTemplate;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void test() {
		getJdbcTemplete().execute("insert into user values(1, zhangchx, female)");
		throw new RuntimeException("run例外");
	}
}
