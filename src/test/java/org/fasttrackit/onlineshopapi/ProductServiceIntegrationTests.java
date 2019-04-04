package org.fasttrackit.onlineshopapi;


import org.fasttrackit.onlineshopapi.domain.Product;
import org.fasttrackit.onlineshopapi.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopapi.service.ProductService;
import org.fasttrackit.onlineshopapi.transfer.CreateProductRequest;
import org.fasttrackit.onlineshopapi.transfer.UpdateProductRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceIntegrationTests {

    @Autowired
    private ProductService productService;

    @Test
    public void testCreateProduct_whenValidRequest_thenReturnProductWithId() {


        Product product = createProduct();


        assertThat(product, notNullValue());
        assertThat(product.getId(), greaterThan(0L));

    }

    private Product createProduct() {
        CreateProductRequest request = new CreateProductRequest();
        request.setName("Laptop");
        request.setPrice(10);
        request.setQuantity(3);
        request.setSku("321sk2");


        return productService.createProduct(request);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetProduct_whenProductNotFound_thenThrowResourceNotFoundException() throws ResourceNotFoundException {
        productService.getProduct(0);

    }

    @Test
    public void testGetProduct_whenExistingIdThanReturnMatchingProduct() throws ResourceNotFoundException {


        Product product = createProduct();

        Product retrivedProduct = productService.getProduct(product.getId());

        assertThat(retrivedProduct.getId(), is(product.getId()));
        assertThat(retrivedProduct.getName(), is(product.getName()));


    }


    @Test
    public void testUpdateProduct_whenValidRequestWithAllFields_thenReturnUpdatingProduct() {


        Product createdProduct = createProduct();

        UpdateProductRequest request = new UpdateProductRequest();

        request.setName(createProduct().getName() + "_Edited");
        request.setPrice(createProduct().getPrice() + 10);
        request.setQuantity(createProduct().getQuantity() + 10);
        request.setSku(createProduct().getSku() + "_Edited");


        Product updatedProduct = null;
        try {
            updatedProduct = productService.updateProduct(createdProduct.getId(), request);
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }


        assertThat(updatedProduct.getName(), is(request.getName()));
        assertThat(updatedProduct.getName(), not(is(createdProduct.getName())));


        assertThat(updatedProduct.getPrice(), is(request.getPrice()));
        assertThat(updatedProduct.getQuantity(), is(request.getQuantity()));
        assertThat(updatedProduct.getSku(), is(request.getSku()));

        assertThat(updatedProduct.getId(), is(createdProduct.getId()));


    }

    //todo: Implement negative tests for update and tests for update with some of the fields only



    @Test(expected = ResourceNotFoundException.class)

    public void testDeleteProduct_whenExistingId_thenProductIsDeleted() throws ResourceNotFoundException {



        Product createdProduct = createProduct();

        productService.deleteProduct(createdProduct.getId());
        productService.getProduct(createdProduct.getId());



    }
}

