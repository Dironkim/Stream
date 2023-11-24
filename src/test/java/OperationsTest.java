import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OperationsTest {

    @Test
    public void testCategoriesToString() {
        // Подготовка данных из JSON
        String path = "src/main/resources/new3.json";
        // Преобразование JSON в объект InternetShop
        InternetShop shop = JsonToObject.convert(path);
        // Вызов тестируемого метода
        String result = Operations.categoriesToString(shop);
        // Проверка результата
        String expected = "Smartphones: iPhone 13, Samsung Galaxy S21; Laptops: MacBook Air, Dell XPS 13";
        assertEquals(expected, result);
    }
    @Test
    public void testAverageProductPrice() {
        // Подготовка данных из JSON
        String path = "src/main/resources/new3.json";
        // Преобразование JSON в объект InternetShop
        InternetShop shop = JsonToObject.convert(path);
        // Вызов тестируемого метода
        double result = Operations.averageProductPrice(shop);
        // Проверка результата
        double expected = (79999 + 69999 + 99999 + 89999) / 4.0;
        assertEquals(expected, result, 0.001);  // Позволяет небольшую погрешность из-за работы с double
    }
    @Test
    public void testApplyDiscountToProducts() {
        // Подготовка данных из JSON
        String path = "src/main/resources/new3.json";
        // Преобразование JSON в объект InternetShop
        InternetShop shop = JsonToObject.convert(path);
        Operations.applyDiscountToProducts(shop, "Summer Smartphone Sale");

        // Проверка результата
        assertEquals(67999, shop.getCategories().get(0).getProducts().get(0).getPrice());  // iPhone 13
        assertEquals(59499, shop.getCategories().get(0).getProducts().get(1).getPrice());  // Samsung Galaxy S21
        assertEquals(99999, shop.getCategories().get(1).getProducts().get(0).getPrice());  // MacBook Air
        assertEquals(89999, shop.getCategories().get(1).getProducts().get(1).getPrice());  // Dell XPS 13
    }
    @Test
    public void testFindMostExpensiveProductInEachCategory() {
        // Подготовка данных из JSON
        String path = "src/main/resources/new3.json";
        // Преобразование JSON в объект InternetShop
        InternetShop shop = JsonToObject.convert(path);
        // Вызов тестируемого метода
        Map<String, InternetShop.Product> result = Operations.findMostExpensiveProductInEachCategory(shop);
        // Проверка результата
        assertNotNull(result);
        assertEquals(79999, result.get("Smartphones").getPrice());  // Самый дорогой продукт в категории Smartphones
        assertEquals(99999, result.get("Laptops").getPrice());       // Самый дорогой продукт в категории Laptops
    }
    @Test
    public void testAddPromotionToCategory(){
        // Подготовка данных из JSON
        String path = "src/main/resources/new3.json";
        // Преобразование JSON в объект InternetShop
        InternetShop shop = JsonToObject.convert(path);

        Operations.AddPromotionToCategory(shop, "Laptops Discount", 5,"Laptops");

        // Проверка результата
        List<InternetShop.Promotion> promotions = shop.getCategories().get(1).getProducts().get(0).getPromotions();
        assertEquals(2, promotions.size());
        assertEquals("Laptops Discount", promotions.get(1).getName());
        assertEquals(5, promotions.get(1).getDiscount().getPercentage());
    }
}
