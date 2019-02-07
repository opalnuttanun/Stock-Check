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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CheckProductTests {

	@Autowired
	private CheckProductRepository checkproductRepository;
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
            assertEquals(violations.size(), 3);
        }
    }
    @Test
    public void testCheckLevelCannotBeNull() {
        CheckProduct cp1 = new CheckProduct();
        cp1.setCheckLevel(null);
        cp1.setCheckComment(null);
        cp1.setCheckDate(null);
		   try {
            entityManager.persist(cp1);
            entityManager.flush();
            fail("CheckLevel must not be null to be valid");
        } catch(javax.validation.ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            assertEquals(violations.isEmpty(), false);
            assertEquals(violations.size(), 3);
        }
    }
    @Test
    public void testCheckProductComplete() {
        String cDate = ("01:02:2019");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:MM:yyyy");
        LocalDate checkDate = LocalDate.parse(cDate,formatter);
        CheckProduct cp2 = new CheckProduct();
        cp2.setCheckLevel(55);
        cp2.setCheckComment("pooo");
        cp2.setCheckDate(checkDate);
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
        CheckProduct cp3 = new CheckProduct();
        cp3.setCheckLevel(55);
        cp3.setCheckComment("pooo");
        cp3.setCheckDate(checkDate);
        entityManager.persist(cp3);
        entityManager.flush();

        CheckProduct cp4 = new CheckProduct();
        cp4.setCheckLevel(55);
        cp4.setCheckComment("pooo");
        cp4.setCheckDate(checkDate);
        
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
}