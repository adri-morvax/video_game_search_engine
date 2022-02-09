package fr.lernejo.fileinjector;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class LauncherTest {
    @Test
    void test_without_good_url() {
        String path = new File("src/test/resources").getAbsolutePath();
        String jsonPath = path + "/fd.json";
        assertThrows(IOException.class, () -> Launcher.main(new String[]{jsonPath}));
    }


    @Test
    void test_with_empty_parameters() {
        assertThrows(IOException.class, () -> Launcher.main(new String[]{""}));
    }


}
