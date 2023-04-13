import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

class DeliveryCardTest {

    String dateDelivery(int day) {
        return LocalDate.now().plusDays(day).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));}
    @BeforeEach
    void setUP() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
    }

    @Test
    void shouldTestPositive() {
        $("[data-test-id=city] input").setValue("Саратов");
        $("[data-test-id=date] input").setValue(dateDelivery(3));
        $("[data-test-id=name] input").setValue("Илон Маск");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $(withText("Встреча успешно забронирована на")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldTestEmptyCity() {
        $("[data-test-id=city] input").setValue("");
        $("[data-test-id=date] input").setValue(dateDelivery(3));
        $("[data-test-id=name] input").setValue("Илон Маск");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldBe(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestEmptyDate() {
        $("[data-test-id=city] input").setValue("Саратов");
        $("[data-test-id=name] input").setValue("Илон Маск");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldBe(text("Неверно введена дата"));
    }

    @Test
    void shouldTestEmptyName() {
        $("[data-test-id=city] input").setValue("Саратов");
        $("[data-test-id=date] input").setValue(dateDelivery(3));
        //$("[data-test-id=name] input").setValue("Ivanov");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldBe(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestEmptyPhone() {
        $("[data-test-id=city] input").setValue("Саратов");
        $("[data-test-id=date] input").setValue(dateDelivery(3));
        $("[data-test-id=name] input").setValue("Илон Маск");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldBe(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestNegativeEmptyCheckBox() {
        $("[data-test-id=city] input").setValue("Саратов");
        $("[data-test-id=date] input").setValue(dateDelivery(3));
        $("[data-test-id=name] input").setValue("Илон Маск");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $(byText("Забронировать")).click();
        $("[data-test-id=agreement].input_invalid").shouldBe(visible);
    }

    @Test
    void shouldTestWrongCity() {
        $("[data-test-id=city] input").setValue("Энгельс");
        $("[data-test-id=date] input").setValue(dateDelivery(3));
        $("[data-test-id=name] input").setValue("Илон Маск");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldBe(text("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldTestNegativeDate() {
        $("[data-test-id=city] input").setValue("Саратов");
        $("[data-test-id=date] input").setValue(dateDelivery(1));
        $("[data-test-id=name] input").setValue("Илон Маск");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldBe(text("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldTestNegativeName() {
        $("[data-test-id=city] input").setValue("Саратов");
        $("[data-test-id=date] input").setValue(dateDelivery(3));
        $("[data-test-id=name] input").setValue("Ilon Mask");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldBe(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestNegativePhone() {
        $("[data-test-id=city] input").setValue("Саратов");
        $("[data-test-id=date] input").setValue(dateDelivery(3));
        $("[data-test-id=name] input").setValue("Илон Маск");
        $("[data-test-id=phone] input").setValue("+712345678");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldBe(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

}