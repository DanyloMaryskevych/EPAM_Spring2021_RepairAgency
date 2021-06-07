package com.example.Broken_Hammer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBManager {
    private final String CONTEXT_LOOKUP;
    private final String DATA_SOURCE_LOOKUP;

    {
        CONTEXT_LOOKUP = "java:/comp/env";
        DATA_SOURCE_LOOKUP = "jdbc/mysql";
    }

    public DataSource getDataSource() throws NamingException {
        Context context = (Context) new InitialContext().lookup(CONTEXT_LOOKUP);
        return (DataSource) context.lookup(DATA_SOURCE_LOOKUP);
    }

    public Connection getConnection() throws NamingException, SQLException {
        return getDataSource().getConnection();
    }

    public static DBManager getDBManager() {
        return new DBManager();
    }
}
