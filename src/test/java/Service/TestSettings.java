package Service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

public abstract class TestSettings {

    private AutoCloseable openMocks;
    
    @BeforeEach
    public void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
    }
    
    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
    }
}
