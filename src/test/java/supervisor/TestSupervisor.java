package supervisor;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestSupervisor {
    @Test
    @Order(1)
    public void authAsSupervisor() {
        open("http://localhost:3000");
        $("[data-test='header-user-actions-auth-button']").shouldHave(exactText("Войти"));
        $("[data-test='header-user-actions-auth-button']").click();
        confirm();
        $("[data-test='header-user-actions'] .username").shouldHave(exactText("Антипин Д. А."));
        System.out.println("auth as supervisor successful");
    }
}
