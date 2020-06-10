package rosipetkova.cmdtester;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.reflections.Reflections;
import picocli.CommandLine;
import rosipetkova.cmdtester.interfaces.ApiTest;

import java.io.File;
import java.util.Map;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "cmdtester", version = "0.0.1")
public final class App implements Callable<Integer> {
    @CommandLine.Option(names = "-c", required = true, defaultValue = "config.json", description = "path to config file", paramLabel = "FILE")
    private File f;
    private Map<?, ?> config;

    private ObjectMapper mapper;
    private Reflections ref;

    public App(ObjectMapper m, Reflections r) {
        mapper = m;
        ref = r;
    }

    @Override
    public Integer call() {
        // Load the configuration
        try {
            config = mapper.readValue(f, Map.class);
        } catch (Exception ex) {
            // TODO - log something
            return 101;
        }

        try {
            // Find all classes that implement ApiTest, create instance and execute tests
            for (Class<?> cl : ref.getSubTypesOf(ApiTest.class)) {
                ApiTest test = (ApiTest) cl.getDeclaredConstructor().newInstance();
                if (!test.execute(config)) {
                    throw new Exception();
                }
            }
        } catch (Exception ex) {
            // TODO - log something
            return 102;
        }

        return null;
    }

    public static void main(String[] args) {
        ObjectMapper m = new ObjectMapper();
        Reflections r = new Reflections("rosipetkova.cmdtester");
        App app = new App(m, r);
        System.exit(new CommandLine(app).execute(args));
    }
}
