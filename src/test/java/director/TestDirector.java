package director;

import com.codeborne.selenide.WebDriverRunner;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestDirector {
    @Test
    @Order(1)
    public void authAsDirector() {
        open("http://localhost:3000");
        $("[data-test='header-user-actions-auth-button']").shouldHave(exactText("Войти"));
        $("[data-test='header-user-actions-auth-button']").click();
        confirm();
        $("[data-test='header-user-actions'] .username").shouldHave(exactText("Антипин Д. А."));
        System.out.println("auth as director successful");
    }

    static {
        Configuration.fileDownload = FileDownloadMode.FOLDER;
    }

    @Test
    @Order(2)
    public void uploadReport() {
        //open("http://localhost:3000/profile/inst-project-reports/");

        $("[data-test='header-user-actions'] div.user").click();
        $$("[data-test='dropdown-list-router-link']").find(exactText("Отчёты")).click();

        assertThat(WebDriverRunner.url()).isEqualTo("http://localhost:3000/profile/inst-project-reports/institute_of_distance_and_evening_education/1");
        //$$("[data-test='base-accordion-locator']").find(exactText("Все отчёты")).click();

        $(byText("Все отчёты")).click();

        assertThat(WebDriverRunner.url()).isEqualTo("http://localhost:3000/profile/inst-project-reports/all/1");
        $("[data-test='institute-director-project-reports-filter-accordion']").click();

        var checkbox = $("[data-test='institute-director-project-reports-not-delivered'] input.checkbox");

        if (!checkbox.isSelected()) {
            checkbox.click();
        }

        checkbox.shouldBe(checked);

        File file = $("[data-test='institute-director-project-reports-upload-report']").download();

        assertThat(file).exists();
        assertThat(file.length()).isGreaterThan(0);
        assertThat(file.getName()).isEqualTo("Несданные отчёты.xlsx");
        System.out.println("report uploaded " + file.getName());
    }

    @Test
    public void uploadReportAlt() {
        assertThat(WebDriverRunner.url()).isEqualTo("http://localhost:3000/profile/inst-project-reports/all/1");

        $("[data-test='institute-director-project-reports-filter-accordion']").click();

        var checkbox = $("[data-test='institute-director-project-reports-delivered'] input.checkbox");

        if (!checkbox.isSelected()) {
            checkbox.click();
        }

        checkbox.shouldBe(checked);

        $("[data-test='institute-director-project-reports-upload-disabled']").should(exist);
        System.out.println("upload-disabled exists");
    }
}
