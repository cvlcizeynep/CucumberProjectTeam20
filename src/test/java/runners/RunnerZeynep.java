package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class) // test çalıştırıcı notasyonu
@CucumberOptions( // Seneryoların nerede ve nasıl çalışacağı, hangi raporun kullanılmasıyla alakalı seçenekleri ayarlar
        monochrome = true,
        features = "src/test/resources/features/apifeature",
        glue = {"stepdefinitions"},
        tags = "@US13API",
        dryRun = false
)
public class RunnerZeynep {

}