package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.Product;
import za.ac.cput.factory.CategoryFactory;
import za.ac.cput.factory.ProductFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    private static Category category;
    private static Set<Product> products = new HashSet<>();

    @BeforeAll
    static void setUp() {
        try {
            // Create category with an empty set of products initially
            category = CategoryFactory.createCategory(
                    "Tools",
                    "Various tools for home improvement",
                    "C:\\Users\\Rupert Van Niekerk\\Documents\\ShareX\\Screenshots\\2024-04\\msedge_F7HspUtQqf.png",
                    products
            );

            // Create products with BigDecimal for price
            Product product1 = ProductFactory.createProduct(
                    "Power Drill",
                    "High-speed power drill for home and professional use",
                    BigDecimal.valueOf(699.99), // Use BigDecimal for price
                    "C:\\Users\\User\\OneDrive\\Desktop\\Hardware-Ecommerce (24Aug)\\Images\\PowerDrill.jfif",
                    category
            );
            Product product2 = ProductFactory.createProduct(
                    "Electric Saw",
                    "Efficient electric saw for cutting wood and metal",
                    BigDecimal.valueOf(499.99), // Use BigDecimal for price
                    "C:\\Users\\User\\OneDrive\\Desktop\\Hardware-Ecommerce (24Aug)\\Images\\Circular-Saw-230mm.png",
                    category
            );
            products.add(product1);
            products.add(product2);

            // Re-create category with products now added
            category = CategoryFactory.createCategory(
                    category.getName(),
                    category.getDescription(),
                    category.getImage(),
                    products
            );

        } catch (IOException | SQLException e) {
            throw new RuntimeException("Failed to create test category", e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create products", e);
        }
    }

    @Test
    @Order(1)
    void a_create() {
        Category created = categoryService.create(category);
        assertNotNull(created, "The created category should not be null");
        assertEquals(category.getName(), created.getName(), "The category name should match");
        assertEquals(category.getDescription(), created.getDescription(), "The category description should match");
        assertEquals(category.getImage(), created.getImage(), "The category image URL should match");
        assertNotNull(created.getProducts(), "The category products should not be null");
        assertEquals(products.size(), created.getProducts().size(), "The number of products should match");
        System.out.println("Created Category: " + created);
    }

    @Test
    @Order(2)
    void b_read() {
        Category read = categoryService.read(category.getCategoryId());
        assertNotNull(read);
        System.out.println(read);
    }

    @Test
    @Order(3)
    @Disabled
    void c_delete() {
        categoryService.delete(category.getCategoryId());
        System.out.println("Category deleted where Category ID: " + category.getCategoryId());
    }

    @Test
    @Order(4)
    void d_getAll() {
        Set<Category> categories = categoryService.getAll();
        assertFalse(categories.isEmpty());
        System.out.println(categories);
    }
}
