package candidate;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCandidate {
    @Test
    @Order(1)
    public void authAsCandidate() {
        open("http://localhost:3000");
        $("[data-test='header-user-actions-auth-button']").shouldHave(exactText("Войти"));
        $("[data-test='header-user-actions-auth-button']").click();
        dismiss();
        confirm();
    }
}
