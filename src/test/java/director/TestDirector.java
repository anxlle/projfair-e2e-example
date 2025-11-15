package director;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;

import java.io.File;

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
    }
    @Test
    public void uploadReport() {
        //open("http://localhost:3000");
        open("http://localhost:3000/profile/inst-project-reports/");

        var checkbox = $("[data-test='institute-director-project-reports-not-delivered'] .checkbox");

        if (!checkbox.isSelected()) {
            System.out.println("not delivered checkbox is not clicked. Clicking");
            checkbox.click();
        } else {
            System.out.println("not delivered checkbox is active");
        }

        checkbox.shouldBe(checked);

        File file = $("[data-test='institute-director-project-reports-upload-report']").download();

        assertThat(file).exists();
        assertThat(file.length()).isGreaterThan(0);
        assertThat(file.getName()).isEqualTo("Несданные отчёты.xlsx");

        //System.out.println("success download file " + file.getAbsolutePath());
    }
}
