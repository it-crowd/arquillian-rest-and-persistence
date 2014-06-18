package pl.itcrowd.blog.arquillian_rest_and_persistence.it;

import org.jboss.logging.Logger;
import org.junit.Rule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;
import org.unitils.core.Unitils;
import org.unitils.core.dbsupport.DefaultSQLHandler;
import org.unitils.database.DatabaseModule;
import org.unitils.dbmaintainer.DBMaintainer;
import org.unitils.dbmaintainer.clean.DBCleaner;
import org.unitils.dbmaintainer.structure.ConstraintsDisabler;
import org.unitils.dbmaintainer.util.DatabaseModuleConfigUtils;

import javax.sql.DataSource;
import java.util.Properties;

public abstract class UnitilsAwareTest {

    @SuppressWarnings({"UnusedDeclaration"})
    @Rule
    public TestWatchman unitilsLuncher = new TestWatchman() {
        @Override
        public void finished(FrameworkMethod method)
        {
            if (isOnServer()) {
                return;
            }
            Unitils.getInstance().getTestListener().afterTestMethod(UnitilsAwareTest.this, method.getMethod(), null);
            Unitils.getInstance().getTestListener().afterTestTearDown(UnitilsAwareTest.this, method.getMethod());
        }

        @Override
        public void starting(FrameworkMethod method)
        {
            logger.info("Starting test " + method.getName());
            if (isOnServer()) {
                return;
            }
            final Unitils unitils = Unitils.getInstance();
            final Properties configuration = unitils.getConfiguration();
            configuration.setProperty(DBMaintainer.PROPKEY_KEEP_RETRYING_AFTER_ERROR_ENABLED, "TRUE");
            final DataSource dataSource;
            dataSource = unitils.getModulesRepository().getModuleOfType(DatabaseModule.class).getDataSourceAndActivateTransactionIfNeeded();
            final DefaultSQLHandler handler = new DefaultSQLHandler(dataSource);
            DatabaseModuleConfigUtils.getConfiguredDatabaseTaskInstance(ConstraintsDisabler.class, configuration, handler).disableConstraints();
            DatabaseModuleConfigUtils.getConfiguredDatabaseTaskInstance(DBCleaner.class, configuration, handler).cleanSchemas();
            Unitils.getInstance().getTestListener().beforeTestSetUp(UnitilsAwareTest.this, method.getMethod());
            Unitils.getInstance().getTestListener().beforeTestMethod(UnitilsAwareTest.this, method.getMethod());
        }

        private boolean isOnServer()
        {
            return null == getClass().getResource("/unitils.properties");
        }
    };

    private Logger logger = Logger.getLogger(getClass());
}
