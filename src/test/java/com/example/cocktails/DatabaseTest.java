package com.example.cocktails;

import org.dbunit.*;
import org.dbunit.dataset.*;
import org.dbunit.dataset.xml.*;
import org.h2.jdbcx.*;

import javax.sql.*;
import java.sql.*;

public class DatabaseTest extends DataSourceBasedDBTestCase {

    public void testGivenDataSetEmptySchema_whenDataSetCreated_thenTablesAreEqual() throws Exception {
        IDataSet expectedDataSet = getDataSet();
        ITable expectedTable = expectedDataSet.getTable("CLIENTS");

        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("CLIENTS");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    public void testDeleteItem() throws Exception {
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader().getResourceAsStream("data2.xml"));
        ITable expectedTable = expectedDataSet.getTable("ITEMS");

        Connection connection = getDataSource().getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM ITEMS where id = '5'");
        statement.execute();

        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("ITEMS");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Override
    protected DataSource getDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();

        dataSource.setURL("jdbc:h2:mem:default;MODE=LEGACY;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:schema.sql'");
        dataSource.setUser("sa");
        dataSource.setPassword("sa");

        return dataSource;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(getClass().getClassLoader().getResourceAsStream("data.xml"));
    }

/*
    @Override
    protected DatabaseOperation getSetUpOperation() {
        return DatabaseOperation.REFRESH;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() {
        return DatabaseOperation.DELETE_ALL;
    }
*/

}
