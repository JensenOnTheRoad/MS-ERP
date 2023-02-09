import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
/**
 * AutoConfigureMockMvc配置好了MockMvc
 */
@AutoConfigureMockMvc
public class test {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_add_item() {

    }

    @Test
    public void should_get_item_list() throws Exception {

    }
}
