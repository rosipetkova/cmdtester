package rosipetkova.cmdtester.tests;

import rosipetkova.cmdtester.interfaces.ApiTest;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class First implements ApiTest {
    @Override
    public boolean execute(Map<?, ?> config) {
        // TODO - rest assured here
        return true;
    }
}
