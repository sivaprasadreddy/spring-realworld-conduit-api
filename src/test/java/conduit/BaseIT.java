package conduit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@Import(ContainersConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class BaseIT {
    @Autowired
    protected MockMvc mockMvc;
}
