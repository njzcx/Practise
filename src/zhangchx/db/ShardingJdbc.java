package zhangchx.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.junit.Test;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.rule.BindingTableRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.jdbc.ShardingDataSource;
import com.google.common.collect.Range;

public class ShardingJdbc {
	
	public ShardingRule buildShardingConfig() {
		//数据源  
        Map<String, DataSource> dataSourceMap = new HashMap<>(2);  
        dataSourceMap.put("sharding_0", createDataSource("sharding_00"));  
        dataSourceMap.put("sharding_1", createDataSource("sharding_01"));  
          
        DataSourceRule dataSourceRule = new DataSourceRule(dataSourceMap);  
          
        //分表分库的表，第一个参数是逻辑表名，第二个是实际表名，第三个是实际库  
        TableRule orderTableRule = new TableRule("t_order", false, Arrays.asList("t_order_0", "t_order_1"), dataSourceRule, null, null, null);  
        TableRule orderItemTableRule = new TableRule("t_order_item", false, Arrays.asList("t_order_item_0", "t_order_item_1"), dataSourceRule, null, null, null);  
          
        /** 
         * DatabaseShardingStrategy 分库策略 
         * 参数一：根据哪个字段分库 
         * 参数二：分库路由函数 
         * TableShardingStrategy 分表策略 
         * 参数一：根据哪个字段分表 
         * 参数二：分表路由函数 
         *  
         */  
        ShardingRule shardingRule = new ShardingRule(dataSourceRule, Arrays.asList(orderTableRule, orderItemTableRule),  
                Arrays.asList(new BindingTableRule(Arrays.asList(orderTableRule, orderItemTableRule))),  
                new DatabaseShardingStrategy("user_id", new ModuloDatabaseShardingAlgorithm()),  
                new TableShardingStrategy("order_id", new ModuloTableShardingAlgorithm()));  
        
        return shardingRule;
	}
	
	@Test
	public void shardingQuery() throws Exception {
        DataSource dataSource = new ShardingDataSource(buildShardingConfig());  
        String sql = "SELECT i.* FROM t_order o JOIN t_order_item i ON o.order_id=i.order_id";  
        try (Connection conn = (Connection) dataSource.getConnection();  
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
//                pstmt.setInt(1, 11);  
//                pstmt.setInt(2, 1001);
            try (ResultSet rs = pstmt.executeQuery()) {  
                while(rs.next()) {  
                    System.out.println(rs.getInt(1) + "|" + rs.getInt(2) + "|" + rs.getInt(3));  
                }  
            }  
        }
	}
	
	public void shardingInsert() throws Exception {
		DataSource dataSource = new ShardingDataSource(buildShardingConfig()); 
		String insertsql = "INSERT INTO t_order (order_id, user_id) VALUES (?, ?)";
		try (Connection conn = (Connection) dataSource.getConnection();  
				PreparedStatement pstmt = conn.prepareStatement(insertsql)) {
			for (int i = 0; i < 100; i++) {
				pstmt.setInt(1, 1000 + i);
				pstmt.setInt(2, 10 + i);
				pstmt.addBatch();
			}
			int[] success = pstmt.executeBatch();
			System.out.println(success.length);
		}
		
		String insertsql2 = "INSERT INTO t_order_item (item_id, order_id, user_id) VALUES (?, ?, ?)";
		try (Connection conn = (Connection) dataSource.getConnection();  
				PreparedStatement pstmt = conn.prepareStatement(insertsql2)) {
			for (int i = 0; i < 100; i++) {
				pstmt.setInt(1, i);
				pstmt.setInt(2, 1000 + i);
				pstmt.setInt(3, 10 + i);
				pstmt.addBatch();
			}
			int[] success = pstmt.executeBatch();
			System.out.println(success.length);
		}
	}

	/**
	 * 创建数据源
	 * 
	 * @param dataSourceName
	 * @return
	 */
	private static DataSource createDataSource(String dataSourceName) {
		BasicDataSource result = new BasicDataSource();
		result.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
		result.setUrl(String.format("jdbc:mysql://localhost:3306/%s",
				dataSourceName));
		result.setUsername("sharding");
		result.setPassword("sharding");
		return result;
	}
}

class ModuloDatabaseShardingAlgorithm implements SingleKeyDatabaseShardingAlgorithm<Integer>{  
	  
    @Override  
    public String doEqualSharding(Collection<String> availableTargetNames, ShardingValue<Integer> shardingValue) {  
        for (String each : availableTargetNames) {  
            if (each.endsWith(shardingValue.getValue() % 2 + "")) {  
                return each;  
            }  
        }  
        throw new IllegalArgumentException();  
    }  
  
    @Override  
    public Collection<String> doInSharding(Collection<String> availableTargetNames, ShardingValue<Integer> shardingValue) {  
        Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());  
        for (Integer value : shardingValue.getValues()) {  
            for (String tableName : availableTargetNames) {  
                if (tableName.endsWith(value % 2 + "")) {  
                    result.add(tableName);  
                }  
            }  
        }  
        return result;  
    }  
  
    @Override  
    public Collection<String> doBetweenSharding(Collection<String> availableTargetNames,  
            ShardingValue<Integer> shardingValue) {  
        Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());  
        Range<Integer> range = (Range<Integer>) shardingValue.getValueRange();  
        for (Integer i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {  
            for (String each : availableTargetNames) {  
                if (each.endsWith(i % 2 + "")) {  
                    result.add(each);  
                }  
            }  
        }  
        return result;  
    }  
}  

final class ModuloTableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<Integer> {  
    
    /** 
    *  select * from t_order from t_order where order_id = 11  
    *          └── SELECT *  FROM t_order_1 WHERE order_id = 11 
    *  select * from t_order from t_order where order_id = 44 
    *          └── SELECT *  FROM t_order_0 WHERE order_id = 44 
    */  
    public String doEqualSharding(final Collection<String> tableNames, final ShardingValue<Integer> shardingValue) {  
        for (String each : tableNames) {  
            if (each.endsWith(shardingValue.getValue() % 2 + "")) {  
                return each;  
            }  
        }  
        throw new IllegalArgumentException();  
    }  
      
    /** 
    *  select * from t_order from t_order where order_id in (11,44)   
    *          ├── SELECT *  FROM t_order_0 WHERE order_id IN (11,44)  
    *          └── SELECT *  FROM t_order_1 WHERE order_id IN (11,44)  
    *  select * from t_order from t_order where order_id in (11,13,15)   
    *          └── SELECT *  FROM t_order_1 WHERE order_id IN (11,13,15)   
    *  select * from t_order from t_order where order_id in (22,24,26)   
    *          └──SELECT *  FROM t_order_0 WHERE order_id IN (22,24,26)  
    */  
    public Collection<String> doInSharding(final Collection<String> tableNames, final ShardingValue<Integer> shardingValue) {  
        Collection<String> result = new LinkedHashSet<>(tableNames.size());  
        for (Integer value : shardingValue.getValues()) {  
            for (String tableName : tableNames) {  
                if (tableName.endsWith(value % 2 + "")) {  
                    result.add(tableName);  
                }  
            }  
        }  
        return result;  
    }  
      
    /** 
    *  select * from t_order from t_order where order_id between 10 and 20  
    *          ├── SELECT *  FROM t_order_0 WHERE order_id BETWEEN 10 AND 20  
    *          └── SELECT *  FROM t_order_1 WHERE order_id BETWEEN 10 AND 20  
    */  
    public Collection<String> doBetweenSharding(final Collection<String> tableNames, final ShardingValue<Integer> shardingValue) {  
        Collection<String> result = new LinkedHashSet<>(tableNames.size());  
        Range<Integer> range = (Range<Integer>) shardingValue.getValueRange();  
        for (Integer i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {  
            for (String each : tableNames) {  
                if (each.endsWith(i % 2 + "")) {  
                    result.add(each);  
                }  
            }  
        }  
        return result;  
    }  
}  
