package com.team22.backend;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.Before;
import com.team22.backend.Entity.*;
import com.team22.backend.Repository.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.text.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CheckProductTests {

	@Autowired
    private CheckProductRepository checkproductRepository;
    private CheckingRepository checkingRepository;
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
    public void testCheckCommentize(){
        CheckProduct cp = new CheckProduct();
        cp.setCheckComment("Dr");
         try {
            entityManager.persist(cp);
            entityManager.flush();
            //fail("Should not pass to this line");
        } catch(javax.validation.ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            assertEquals(violations.isEmpty(), false);
            assertEquals(violations.size(), 4);
        }
    }
    @Test
    public void testCheckProductCannotBeNull() {
        CheckProduct cp1 = new CheckProduct();
        cp1.setCheckLevel(null);
        cp1.setCheckComment(null);
        cp1.setCheckDate(null);
        cp1.setCheckTime(null);
        cp1.setChecking(null);
        cp1.setProduct(null);
		   try {
            entityManager.persist(cp1);
            entityManager.flush();
            fail("CheckProduct must not be null to be valid");
        } catch(javax.validation.ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            assertEquals(violations.isEmpty(), false);
            assertEquals(violations.size(), 4);
        }
    }
    @Test
    public void testCheckProductComplete() {
        // Product pd =  productRepository.findByProdId(1L);
        // Checking ck = checkingRepository.findByCheckingId(1L);

        String cDate = ("01:02:2019");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:MM:yyyy");
        LocalDate checkDate = LocalDate.parse(cDate,formatter);
        
        String  checkTime   =   ("14:25");
        
        SimpleDateFormat ft = new SimpleDateFormat ("HH:mm"); 
        Date ti = new Date();
        try {
           ti = ft.parse(checkTime); 
             System.out.println(ti); 
        } catch (ParseException e) { 
         System.out.println("Unparseable using " + ft); 
         }
        Instant instant = Instant.ofEpochMilli(ti.getTime());
        LocalTime time = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();

        CheckProduct cp2 = new CheckProduct();
        cp2.setCheckLevel(55);
        cp2.setCheckComment("pooo");
        cp2.setCheckDate(checkDate);
        cp2.setCheckTime(time);
       // cp2.setChecking(pd);
      //  cp2.setProduct(ck);
		   try {
            entityManager.persist(cp2);
            entityManager.flush();
        } catch(javax.validation.ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            assertEquals(violations.isEmpty(), false);
            assertEquals(violations.size(), 2);
        }
    }
    @Test
    (expected=javax.persistence.PersistenceException.class)
    public void testCheckLevelMustBeUnique() {
        String cDate = ("01:02:2019");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:MM:yyyy");
        LocalDate checkDate = LocalDate.parse(cDate,formatter);

        String  checkTime   =   ("14:25");
        SimpleDateFormat ft = new SimpleDateFormat ("HH:mm"); 
        Date ti = new Date();
        try {
           ti = ft.parse(checkTime); 
             System.out.println(ti); 
        } catch (ParseException e) { 
         System.out.println("Unparseable using " + ft); 
         }
        Instant instant = Instant.ofEpochMilli(ti.getTime());
        LocalTime time = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();

        CheckProduct cp3 = new CheckProduct();
        cp3.setCheckLevel(55);
        cp3.setCheckComment("pooo");
        cp3.setCheckDate(checkDate);
        cp3.setCheckTime(time);
        entityManager.persist(cp3);
        entityManager.flush();

        CheckProduct cp4 = new CheckProduct();
        cp4.setCheckLevel(55);
        cp4.setCheckComment("pooo");
        cp4.setCheckDate(checkDate);
        cp4.setCheckTime(time);
      //  try{
            entityManager.persist(cp4);
            entityManager.flush();
        // }catch(javax.persistence.PersistenceException e) {
        //     System.out.println(); 
        //     System.out.println();   
        //     System.out.println("\n\n\n\n\n\n\n\n\n" + e + "----------------->>testProductIdsMustBeUnique \n\n\n\n\n\n\n\n\n\n\n");
        //     System.out.println(); 
        //     System.out.println(); 
        // }
    }
    @Test
    public void tescheckLevelDigit(){
        CheckProduct cp5 = new CheckProduct();
		cp5.setCheckLevel(5555);
         try {
            entityManager.persist(cp5);
            entityManager.flush();
            fail("Should not pass to this line");
        } catch(javax.validation.ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            System.out.println(e);
            assertEquals(violations.isEmpty(), false);
            assertEquals(violations.size(), 4);
        }
    }
    @Test
    public void tescheckLevelDecimalMax(){
        CheckProduct cp6 = new CheckProduct();
		cp6.setCheckLevel(120);
         try {
            entityManager.persist(cp6);
            entityManager.flush();
            fail("Should not pass to this line");
        } catch(javax.validation.ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            System.out.println(e);
            assertEquals(violations.isEmpty(), false);
            assertEquals(violations.size(), 4);
        }
    }
    @Test
    public void tescheckLevelDecimalMin(){
        CheckProduct cp7 = new CheckProduct();
		cp7.setCheckLevel(-20);
         try {
            entityManager.persist(cp7);
            entityManager.flush();
            fail("Should not pass to this line");
        } catch(javax.validation.ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            System.out.println(e);
            assertEquals(violations.isEmpty(), false);
            assertEquals(violations.size(), 4);
        }
    }
}