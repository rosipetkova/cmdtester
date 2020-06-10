package rosipetkova.cmdtester;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.reflections.Reflections;
import rosipetkova.cmdtester.interfaces.ApiTest;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class AppTest {
    @Test
    public void objectMapperError() throws IOException {
        ObjectMapper m = mock(ObjectMapper.class);
        when(m.readValue(ArgumentMatchers.<File>any(), ArgumentMatchers.<Class<Object>>any())).thenThrow(new IOException());

        Reflections r = mock(Reflections.class);

        App app = new App(m, r);
        assertEquals(101, app.call());
    }

    @Test
    public void testsFailError() {
        ObjectMapper m = mock(ObjectMapper.class);
        Reflections r = mock(Reflections.class);

        class TmpTest implements ApiTest {
            @Override
            public boolean execute(Map<?, ?> config) {
                return false;
            }
        }

        Set<Class<? extends ApiTest>> classes = new HashSet<>();
        classes.add(TmpTest.class);
        when(r.getSubTypesOf(ApiTest.class)).thenReturn(classes);

        App app = new App(m, r);
        assertEquals(102, app.call());
    }

    @Test
    public void success() {
        ObjectMapper m = mock(ObjectMapper.class);
        Reflections r = mock(Reflections.class);

        App app = new App(m, r);
        assertNull(app.call());
    }
}