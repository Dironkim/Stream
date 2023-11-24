import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Operations {

    public static String categoriesToString(InternetShop shop) {
        return shop.getCategories().stream()
                .map(category -> category.getName() + ": " +
                        category.getProducts().stream()
                                .map(InternetShop.Product::getName)
                                .collect(Collectors.joining(", ")))
                .collect(Collectors.joining("; "));
    }
    public static double averageProductPrice(InternetShop shop) {
        return shop.getCategories().stream()
                .flatMap(category -> category.getProducts().stream())
                .filter(product -> product.getPrice() > 0)
                .mapToInt(InternetShop.Product::getPrice)
                .average()
                .orElse(0);
    }
    public static void applyDiscountToProducts(InternetShop shop, String promotionName) {
        shop.getCategories().forEach(category ->
                category.getProducts().forEach(product ->
                        product.getPromotions().stream()
                                .filter(promotion -> promotion.getName().equals(promotionName))
                                .findFirst()
                                .ifPresent(promotion -> {
                                    int discountedPrice = applyDiscount(product.getPrice(), promotion);
                                    product.setPrice(discountedPrice);
                                })
                )
        );
    }

    private static int applyDiscount(int originalPrice, InternetShop.Promotion promotion) {
        int percentage = promotion.getDiscount().getPercentage();
        return (int) (originalPrice * (1.0 - percentage / 100.0));
    }

    public static Map<String, InternetShop.Product> findMostExpensiveProductInEachCategory(InternetShop shop) {
        return shop.getCategories().stream()
                .collect(Collectors.toMap(
                        InternetShop.Category::getName,
                        category -> category.getProducts().stream()
                                .sorted((p1, p2) -> Integer.compare(p2.getPrice(), p1.getPrice()))
                                .findFirst()
                                .orElse(null)
                ));
    }

    public static void AddPromotionToCategory(InternetShop shop, String promotionName, int discountPercentage, String categoryName) {
        InternetShop.Promotion newPromotion = new InternetShop.Promotion();
        newPromotion.setName(promotionName);

        InternetShop.Discount discount = new InternetShop.Discount();
        discount.setPercentage(discountPercentage);
        newPromotion.setDiscount(discount);
        newPromotion.setFreeShipping(false); // Например, акция не предоставляет бесплатную доставку

        shop.getCategories().stream()
                .filter(category -> category.getName().equals(categoryName))
                .flatMap(category -> category.getProducts().stream())
                .forEach(product -> {
                    List<InternetShop.Promotion> promotions = product.getPromotions();
                    promotions.add(newPromotion);
                    product.setPromotions(promotions);
                });
    }
}
