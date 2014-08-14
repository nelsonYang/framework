package com.framework.ehcache.datasourcerouteprovider;

import com.framework.datasource.routeprovider.DataSourceRouteProvider;
import com.framework.route.entity.RouteInfo;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest
        extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
      DataSourceRouteProvider dataSourceRouteProvider = new DataSourceRouteProvider();
      dataSourceRouteProvider.registerRoutes();
      Map<String,String> map = new HashMap<String,String>(2,1);
      map.put("companyId", "20000,100000000");
      Map<String, RouteInfo<DataSource>>  resultMap= dataSourceRouteProvider.getRoutes("user", map);
    }
}
