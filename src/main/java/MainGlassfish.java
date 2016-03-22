
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainGlassfish {

	public static void main(String[] args) throws Exception {
		String webappDirLocation = "src/main/webapp/";

		// The port that we should run on can be set into an environment
		// variable
		// Look for that variable and default to 8080 if it isn't there.
		String webPort = System.getenv("PORT");
		if (webPort == null || webPort.isEmpty()) {
			webPort = "8080";
		}

		GlassFishProperties gfProps = new GlassFishProperties();
		gfProps.setPort("http-listener", Integer.valueOf(webPort)); // refer
																	// JavaDocs
																	// for the
																	// details
																	// of this
																	// API.
		gfProps.setProperty("domain-dir", "glassfishDomain");

		GlassFish glassfish = GlassFishRuntime.bootstrap()
				.newGlassFish(gfProps);
		glassfish.start();
		
	    CommandRunner runner = glassfish.getCommandRunner();


//  Heroku        	    
        String dbUrl = System.getenv("DATABASE_URL");
        
        System.out.println("-------db url: " + dbUrl);
        Matcher matcher = Pattern.compile("postgres://(.*):(.*)@(.*)/(.*)").matcher(dbUrl);
        matcher.find();
        
        String host = matcher.group(3);
	    String pHost[] =  host.split(":");
	    host = pHost[0];
        String database = matcher.group(4);
        String user = matcher.group(1);
        String password = matcher.group(2);
        String properties = "user=" + user + ":password=" + password + ":databasename=" + database + ":loglevel=4:servername=" + host;

//Fim heroku        
      
        
        
      
// LOCAL
//        String host = "localhost";
//       String database = "contribua";
//        String user = "postgres";
//       String password = "postgres";        
//        String properties = "user=" + user + ":password=" + password + ":databasename=" + database + ":loglevel=4:servername=" + host;
//Fim LOCAL     
	    
        System.out.println("-------properties: " + properties);
        
        CommandResult result = runner.run("create-jdbc-connection-pool", "--datasourceclassname", "org.postgresql.ds.PGSimpleDataSource", "--restype", "javax.sql.DataSource", 
        		//"--property", "url='" + dbUrl + "'", 
        		//"--property", "user=user:password=postgres:databasename=petclinic:server=localhost:port=5432",
        		//"--steadypoolsize", "1",
        		//"--maxpoolsize", "1",
        		"--property", properties,
        		"jdbc/contribua_pool");
        
        System.out.println("------output of create conn pool: " + result.getOutput());
        
	    result = runner.run("create-jdbc-resource", "--connectionpoolid", "jdbc/contribua_pool", 
	    		"contribuaDS");
	    
        System.out.println("------output of create jdbc: " + result.getOutput());

      //  result = runner.run("set-log-level", "javax.enterprise.system.container.web=INFO:javax.enterprise.system.container.ejb=FINEST");
//        
//       System.out.println("------output of set log level: " + result.getOutput());
	    
		Deployer deployer = glassfish.getDeployer();

		// Create a scattered web application.
		ScatteredArchive archive = new ScatteredArchive("contribua",
				ScatteredArchive.Type.WAR, new File(webappDirLocation));
		// target/classes directory contains my complied servlets
		archive.addClassPath(new File("target", "classes"));
                System.out.println("URI " + archive.toURI());
		deployer.deploy(archive.toURI());

	}

}
