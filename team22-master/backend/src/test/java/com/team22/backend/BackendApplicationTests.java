package com.team22.backend;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.util.Collections;
import java.util.OptionalInt;
import java.util.Set;
import java.util.Date;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.Before;
import com.team22.backend.Entity.*;
import com.team22.backend.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@RunWith(SpringRunner.class)
//@SpringBootTest
@DataJpaTest
public class BackendApplicationTests {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
    private TestEntityManager entityManager;

	private Validator validator;
	
	@Before
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
	@Test
    public void testProductCharNotP(){
        Product p2 = new Product();
        p2.setProductIds("C1");
         try {
            entityManager.persist(p2);
            entityManager.flush();

            //fail("Should not pass to this line");
        } catch(javax.validation.ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            assertEquals(violations.isEmpty(), false);
            assertEquals(violations.size(), 1);
        }
    }
    @Test
    public void testProductNemeSize(){
        Product p1 = new Product();
        p1.setProductName("Dr");
         try {
            entityManager.persist(p1);
            entityManager.flush();

            //fail("Should not pass to this line");
        } catch(javax.validation.ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            assertEquals(violations.isEmpty(), false);
            assertEquals(violations.size(), 2);
        }
    }
    @Test
    public void testProductIdsCannotBeNull() {
        Product p3 = new Product();
		p3.setProductIds(null);
		   try {
            entityManager.persist(p3);
            entityManager.flush();
            fail("productIds must not be null to be valid");
        } catch(javax.validation.ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            assertEquals(violations.isEmpty(), false);
            assertEquals(violations.size(), 1);
        }
    }
    @Test
    public void testProductIdsCanNull() {
        Product p = new Product();
		p.setProductIds("P55");
		
		   try {
            entityManager.persist(p);
            entityManager.flush();
        } catch(javax.validation.ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            assertEquals(violations.isEmpty(), false);
            assertEquals(violations.size(), 1);
        }
    }
    @Test(expected=javax.persistence.PersistenceException.class)
    public void testProductIdsMustBeUnique() {
        Product p4 = new Product();
		p4.setProductIds("P55");
        entityManager.persist(p4);
        entityManager.flush();

        Product p5 = new Product();
		p5.setProductIds("P55");
        entityManager.persist(p5);
        entityManager.flush();

        fail("Should not pass to this line");
    }

    @Test
    public void testProductIdsNotDigit(){
        Product p6 = new Product();
		p6.setProductIds("Pdefff");
         try {
            entityManager.persist(p6);
            entityManager.flush();

            fail("Should not pass to this line");
        } catch(javax.validation.ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            assertEquals(violations.isEmpty(), false);
            assertEquals(violations.size(), 1);
        }
    }

}