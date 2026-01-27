import java.nio.file.Path;

public class ConfigTest {
    public static void main(String[] args) {
        System.out.println("=== Testing AppConfig Serialization ===");
        testAppConfig();
        
        System.out.println("\n=== Testing DatabaseConfig Serialization ===");
        testDatabaseConfig();
    }
    
    private static void testAppConfig() {
        try {
            AppConfig config = new AppConfig("MyApp", "1.0", 100, 30000);
            System.out.println("Original config: " + config);
            System.out.println("Original lastLoaded: " + config.getLastLoaded());
            
            Path configFile = Path.of("config.ser");
            ConfigManager.saveConfig(config, configFile);
            System.out.println("\nConfig saved to " + configFile);
            
            Thread.sleep(1000);
            
            AppConfig loaded = ConfigManager.loadConfig(configFile);
            System.out.println("\nLoaded config: " + loaded);
            System.out.println("Loaded lastLoaded: " + loaded.getLastLoaded());
            
            System.out.println("\nVerification:");
            System.out.println("  App name matches: " + config.getAppName().equals(loaded.getAppName()));
            System.out.println("  Version matches: " + config.getVersion().equals(loaded.getVersion()));
            System.out.println("  Max connections matches: " + (config.getMaxConnections() == loaded.getMaxConnections()));
            System.out.println("  Timeout matches: " + (config.getTimeout() == loaded.getTimeout()));
            System.out.println("  LastLoaded is different (transient): " + !config.getLastLoaded().equals(loaded.getLastLoaded()));
            
        } catch (Exception e) {
            System.err.println("Error testing AppConfig: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testDatabaseConfig() {
        try {
            DatabaseConfig dbConfig = new DatabaseConfig("localhost", 5432, "admin", "secret123");
            System.out.println("Original database config: " + dbConfig);
            System.out.println("Original password: " + dbConfig.getPassword());
            
            Path dbConfigFile = Path.of("dbconfig.ser");
            ConfigManager.saveDatabaseConfig(dbConfig, dbConfigFile);
            System.out.println("\nDatabase config saved to " + dbConfigFile);
            
            DatabaseConfig loadedDb = ConfigManager.loadDatabaseConfig(dbConfigFile);
            System.out.println("\nLoaded database config: " + loadedDb);
            System.out.println("Loaded password: " + loadedDb.getPassword());
            
            System.out.println("\nVerification:");
            System.out.println("  Host matches: " + dbConfig.getHost().equals(loadedDb.getHost()));
            System.out.println("  Port matches: " + (dbConfig.getPort() == loadedDb.getPort()));
            System.out.println("  Username matches: " + dbConfig.getUsername().equals(loadedDb.getUsername()));
            System.out.println("  Password matches (decrypted): " + dbConfig.getPassword().equals(loadedDb.getPassword()));
            
        } catch (Exception e) {
            System.err.println("Error testing DatabaseConfig: " + e.getMessage());
            e.printStackTrace();
        }
    }
}