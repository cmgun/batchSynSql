package com.cmgun.batchsynSQL;



import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * @author chenqilin on 2018/6/6.
 */
public class BatchSynSql {

    public static void main(String[] args) {
        String driver = "com.mysql.jdbc.Driver";

        try {
            Properties props = Resources.getResourceAsProperties("database.properties");
            for (String key : props.stringPropertyNames()) {
                if (key.contains("jdbcUrl")) {
                    String env = key.split("\\.")[2];
                    System.out.println("------开始处理env:" + env + "SQL同步-----");
                    // 获取username和password
                    String userNameKey = "datasource.username." + env;
                    String passwordKey = "datasource.password." + env;
                    String username = props.getProperty(userNameKey);
                    String password = props.getProperty(passwordKey);
                    String url = props.getProperty(key);

                    Class.forName(driver).newInstance();
                    Connection conn = DriverManager.getConnection(url, username, password);
                    try {

                        ScriptRunner runner = new ScriptRunner(conn);
                        runner.setAutoCommit(true);
                        runner.setFullLineDelimiter(true);
                        // 分隔符
                        runner.setDelimiter("go");
                        runner.setSendFullScript(false);
                        runner.setStopOnError(false);
                        runner.runScript(Resources.getResourceAsReader("sql.sql"));
                        conn.close();
                    } catch (Exception e) {
                        System.err.println(e);
                        continue;
                    } finally {
                        try {
                            if(conn != null){
                                conn.close();
                            }
                        } catch (Exception e) {
                            if(conn != null){
                                conn = null;
                            }
                        }
                    }
                    System.out.println("------结束处理env:" + env + "SQL同步-----");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
