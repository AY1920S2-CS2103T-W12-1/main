package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.OrderBuilder;

public class OrderContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        OrderContainsKeywordsPredicate firstPredicate = new OrderContainsKeywordsPredicate(firstPredicateKeywordList);
        OrderContainsKeywordsPredicate secondPredicate = new OrderContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        OrderContainsKeywordsPredicate firstPredicateCopy =
            new OrderContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_transactionIdContainsKeywordsGeneralSearch_returnsTrue() {
        // One keyword
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Collections.singletonList("1234567890"));
        assertTrue(predicate.test(new OrderBuilder().withTid("1234567890").build()));

        // Multiple keywords
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("1234567890", "087654321"));
        assertTrue(predicate.test(new OrderBuilder().withTid("1234567890").build()));
    }

    @Test
    public void test_nameContainsKeywordsGeneralSearch_returnsTrue() {
        // One keyword
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new OrderBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new OrderBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new OrderBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new OrderBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_phoneContainsKeywordGeneralSearch_returnsTrue() {
        // One keyword
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Collections.singletonList("97555838"));
        assertTrue(predicate.test(new OrderBuilder().withPhone("97555838").build()));

        // Only one matching keyword
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("999", "98765432"));
        assertTrue(predicate.test(new OrderBuilder().withPhone("999").build()));
    }

    @Test
    public void test_addressContainsKeywordGeneralSearch_returnsTrue() {
        // One keyword
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Collections.singletonList("Geylang"));
        assertTrue(predicate.test(new OrderBuilder().withAddress("Lorong 10 Geylang st 10").build()));

        // Multiple keywords
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("street", "81", "Yishun"));
        assertTrue(predicate.test(new OrderBuilder().withAddress("Yishun street 81").build()));

        // Only one matching keyword
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("Geylang", "Serangoon", "Tampines"));
        assertTrue(predicate.test(new OrderBuilder().withAddress("Tampines 10").build()));

        // Mixed-case keywords
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("cHuAn", "GeYLaNg"));
        assertTrue(predicate.test(new OrderBuilder().withAddress("geylang lorong").build()));
    }

    @Test
    public void test_timeStampContainsKeywordGeneralSearch_returnsTrue() {
        // One keyword full match date and time
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Collections.singletonList("2020-02-02"));
        assertTrue(predicate.test(new OrderBuilder().withTimeStamp("2020-02-02 1500").build()));

        // Only one matching keyword of date and time
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("2020-01-01", "2020-01-02"));
        assertTrue(predicate.test(new OrderBuilder().withTimeStamp("2020-01-02 1500").build()));

        // Only matching time keyword
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("2020-01-01", "1500", "2020-01-02", "1400"));
        assertTrue(predicate.test(new OrderBuilder().withTimeStamp("2020-05-05 1500").build()));

        // Only matching dates keyword
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("2020-05-05", "1300", "2020-01-02", "1400"));
        assertTrue(predicate.test(new OrderBuilder().withTimeStamp("2020-05-05 1200").build()));
    }

    @Test
    public void test_warehouseContainsKeywordGeneralSearch_returnsTrue() {
        // One keyword full match warehouse address
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Arrays.asList("5", "Toh", "Guan", "Rd", "E", "#02-30", "S608831"));
        assertTrue(predicate.test(new OrderBuilder().withWarehouse("5 Toh Guan Rd E, #02-30 S608831").build()));

        // Only one full matching keyword of warehouse address
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("5", "Toh", "Guan", "Rd", "E", "Geylang"));
        assertTrue(predicate.test(new OrderBuilder().withWarehouse("Geylang").build()));

        // Any one word matches the given warehouse address
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("Yishun", "New", "Town"));
        assertTrue(predicate.test(new OrderBuilder().withWarehouse("Yishun Old").build()));

        // Only matches the postal code
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("123456", "760844"));
        assertTrue(predicate.test(new OrderBuilder().withWarehouse("Yishun st 71 blk 777 760844").build()));
    }

    @Test
    public void test_cashContainsKeywordGeneralSearch_returnsTrue() {
        // One keyword full match cash on delivery
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Collections.singletonList("$5"));
        assertTrue(predicate.test(new OrderBuilder().withCash("$5").build()));

        // Only one full matching keyword of cash on delivery
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("$5", "$3.50", "$0.10", "$10"));
        assertTrue(predicate.test(new OrderBuilder().withCash("$10").build()));
    }

    @Test
    public void test_commentsContainsKeywordGeneralSearch_returnsTrue() {
        // One keyword full match comment given
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Arrays.asList("Please", "say", "hi", "to", "me"));
        assertTrue(predicate.test(new OrderBuilder().withComment("Please say hi to me").build()));

        // Only one full matching keyword of comment given
        predicate =
            new OrderContainsKeywordsPredicate(Arrays.asList("5", "hi", "S608831",
                "Put", "in", "at", "my", "shoe", "rack"));
        assertTrue(predicate.test(new OrderBuilder().withComment("Put in at my shoe rack").build()));

        // Any one word matches the given comment
        predicate = new OrderContainsKeywordsPredicate(Collections.singletonList("Hi"));
        assertTrue(predicate.test(new OrderBuilder().withComment("Say hi to me when you're here!").build()));
    }

    @Test
    public void test_itemTypeContainsKeywordGeneralSearch_returnsTrue() {
        // One keyword full match comment given
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Arrays.asList("Plastic"));
        assertTrue(predicate.test(new OrderBuilder().withItemType("Plastic").build()));

        // Only one full matching keyword of comment given
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("Gold", "Silver", "Plastic"));
        assertTrue(predicate.test(new OrderBuilder().withItemType("Gold").build()));
    }

    // Test for fail cases
    @Test
    public void test_emptyKeywordGivenInGeneralSearch_returnFalse() {
        // Empty keyword given
        OrderContainsKeywordsPredicate predicate = new OrderContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new OrderBuilder().build()));
    }

    @Test
    public void test_transactionIdDoesNotContainKeywordsGeneralSearch_returnsFalse() {
        // Non-matching transaction id keyword
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Collections.singletonList("1234567890"));
        assertFalse(predicate.test(new OrderBuilder().withTid("0123947124d").build()));

        // Permutations of transaction id keyword
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("1234abc", "3456def"));
        assertFalse(predicate.test(new OrderBuilder().withTid("4321cba").build()));

        // Substring of transaction id keyword
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("1234", "abc"));
        assertFalse(predicate.test(new OrderBuilder().withTid("1234abc").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywordsGeneralSearch_returnsFalse() {
        // Non-matching keyword
        OrderContainsKeywordsPredicate predicate = new OrderContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new OrderBuilder().withName("Alice Bob").build()));

        // Permutations of keyword
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("Aeicl", "oBb"));
        assertFalse(predicate.test(new OrderBuilder().withName("Alice Bob").build()));

        // Substring of keyword
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("Alic", "Bo"));
        assertFalse(predicate.test(new OrderBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_phoneDoesNotContainKeywordsGeneralSearch_returnsFalse() {
        // Non-matching phone number
        OrderContainsKeywordsPredicate predicate = new OrderContainsKeywordsPredicate(Arrays.asList("999"));
        assertFalse(predicate.test(new OrderBuilder().withPhone("12345678").build()));

        // Permutations of phone number
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("978", "654"));
        assertFalse(predicate.test(new OrderBuilder().withPhone("879").build()));

        // Substring of phone number
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("999", "888"));
        assertFalse(predicate.test(new OrderBuilder().withPhone("99988899").build()));
    }

    @Test
    public void test_addressDoesNotContainKeywordsGeneralSearch_returnsFalse() {
        // Non-matching address
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Arrays.asList("Yishun", "st", "81"));
        assertFalse(predicate.test(new OrderBuilder().withAddress("Jurong West 36").build()));
    }

    @Test
    public void test_timeStampDoesNotContainKeywordsGeneralSearch_returnsFalse() {
        // Non-matching timestamp keyword
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Arrays.asList("2020", "1345", "01"));
        assertFalse(predicate.test(new OrderBuilder().withTimeStamp("2040-02-03 1000").build()));
    }

    @Test
    public void test_warehouseDoesNotContainKeywordsGeneralSearch_returnsFalse() {
        // Non-matching warehouse address keyword
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Arrays.asList("Toh", "134544", "#05-11"));
        assertFalse(predicate.test(new OrderBuilder().withWarehouse("Geylang Street 81, #02-30 S608831").build()));

        // Missing S in postal code keyword
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("Toh", "134544", "#05-11"));
        assertFalse(predicate.test(new OrderBuilder().withWarehouse("Geylang Street 81, #02-30 S134544").build()));

        // Missing # in postal code keyword
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("Toh", "134544", "05-11"));
        assertFalse(predicate.test(new OrderBuilder().withWarehouse("Geylang Street 81, #05-11 S134544").build()));
    }

    @Test
    public void test_cashOnDeliveryDoesNotContainKeywordsGeneralSearch_returnsFalse() {
        // Non-matching cash keyword
        OrderContainsKeywordsPredicate predicate = new OrderContainsKeywordsPredicate(Collections.singletonList("$4"));
        assertFalse(predicate.test(new OrderBuilder().withCash("$0.4").build()));

        // Substring cash keywords do not match
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("$44", "$4.0"));
        assertFalse(predicate.test(new OrderBuilder().withCash("$4").build()));
    }

    @Test
    public void test_commentsDoesNotContainKeywordsGeneralSearch_returnsFalse() {
        // Non-matching comment keyword
        OrderContainsKeywordsPredicate predicate = new OrderContainsKeywordsPredicate(Arrays.asList("hi"));
        assertFalse(predicate.test(new OrderBuilder().withComment("bye").build()));

        // Substring comment keywords do not match
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("hiiiiiiiiiiiiiiiiii", "hi"));
        assertFalse(predicate.test(new OrderBuilder().withComment("hiii").build()));
    }

    @Test
    public void test_itemTypeDoesNotContainKeywordsGeneralSearch_returnsFalse() {
        // Non-matching item type keyword
        OrderContainsKeywordsPredicate predicate = new OrderContainsKeywordsPredicate(Arrays.asList("Plastic"));
        assertFalse(predicate.test(new OrderBuilder().withItemType("Gold").build()));

        // Substring item type keyword do not match
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("Go", "ld"));
        assertFalse(predicate.test(new OrderBuilder().withItemType("Gold").build()));
    }

    // Test for overloaded search
    @Test
    public void test_transactionIdContainsKeywordsSpecificSearch_returnTrue() {
        // One matching transaction id keywords
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Collections.singletonList("1234567890"),
                true, false, false, false,
                false, false, false, false, false);
        assertTrue(predicate.test(new OrderBuilder().withTid("1234567890").build()));

        // Multiple transaction id keywords with only one transaction id matching keyword
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("12345", "1023012", "12345abc"),
            true, false, false, false, false,
            false, false, false, false);
        assertTrue(predicate.test(new OrderBuilder().withTid("12345abc").build()));
    }

    @Test
    public void test_nameContainsKeywordsSpecificSearch_returnTrue() {
        // One matching name keyword
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Collections.singletonList("Alice"),
                false, true, false, false,
                false, false, false, false, false);
        assertTrue(predicate.test(new OrderBuilder().withName("Alice Bob").build()));

        // Multiple matching name keywords
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"),
            false, true, false, false, false,
            false, false, false, false);
        assertTrue(predicate.test(new OrderBuilder().withName("Alice Bob").build()));

        // Mixed-case name keywords
        predicate = new OrderContainsKeywordsPredicate(Collections.singletonList("ALiCe"),
            false, true, false, false, false,
            false, false, false, false);
        assertTrue(predicate.test(new OrderBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_phoneContainsKeywordsSpecificSearch_returnTrue() {
        // One full matching phone keywords
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Collections.singletonList("12345678"),
                false, false, true, false, false,
                false, false, false, false);
        assertTrue(predicate.test(new OrderBuilder().withPhone("12345678").build()));

        // Only one matching phone keyword with other keywords
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("12345678", "111111"),
            false, false, true, false, false,
            false, false, false, false);
        assertTrue(predicate.test(new OrderBuilder().withPhone("12345678").build()));
    }

    @Test
    public void test_addressContainsKeywordsSpecificSearch_returnTrue() {
        // One matching address keyword
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Collections.singletonList("Geylang"),
                false, false, false, true,
                false, false, false, false, false);
        assertTrue(predicate.test(new OrderBuilder().withAddress("Geylang").build()));

        // Multiple matching address keywords
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("Geylang", "street"),
            false, false, false, true,
            false, false, false, false, false);
        assertTrue(predicate.test(new OrderBuilder().withAddress("Geylang street").build()));

        // Mixed-case address keywords
        predicate = new OrderContainsKeywordsPredicate(Collections.singletonList("geYlAnG"),
            false, false, false, true,
            false, false, false, false, false);
        assertTrue(predicate.test(new OrderBuilder().withAddress("Geylang Street").build()));
    }

    @Test
    public void test_timeStampContainsKeywordsSpecificSearch_returnTrue() {
        // Full matching timestamp keywords
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Arrays.asList("2020-02-10", "1500"),
                false, false, false, false,
                true, false, false, false, false);
        assertTrue(predicate.test(new OrderBuilder().withTimeStamp("2020-02-10 1500").build()));

        // Full matching timestamp keywords with other keywords
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("2020-02-20", "2020-01-10", "1500"),
                false, false, false, false,
                true, false, false, false, false);
        assertTrue(predicate.test(new OrderBuilder().withTimeStamp("2020-02-10 1500").build()));
    }

    @Test
    public void test_warehouseContainsKeywordsSpecificSearch_returnTrue() {
        // One matching warehouse location keyword
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Collections.singletonList("Yishun"),
                false, false, false, false,
                false, true, false, false, false);
        assertTrue(predicate.test(new OrderBuilder().withWarehouse("Yishun Industrial Area Blk 51").build()));

        // Multiple matching warehouse location keywords
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("Yishun", "St", "81"),
            false, false, false, false,
            false, true, false, false, false);
        assertTrue(predicate.test(new OrderBuilder().withWarehouse("Yishun st 81").build()));
    }

    @Test
    public void test_cashOnDeliveryContainsKeywordsSpecificSearch_returnsTrue() {
        // keywords full match cash on delivery
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Collections.singletonList("$5"),
                false, false, false, false,
                false, false, true, false, false);
        assertTrue(predicate.test(new OrderBuilder().withCash("$5").build()));

        // Only one full matching keyword of cash on delivery
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("$5", "$3.50", "$0.10", "$10"),
            false, false, false, false,
            false, false, true, false, false);
        assertTrue(predicate.test(new OrderBuilder().withCash("$10").build()));
    }

    @Test
    public void test_commentContainsKeywordsSpecificSearch_returnsTrue() {
        // keywords full match comment given
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Arrays.asList("Please", "say", "hi", "to", "me"),
                false, false, false, false,
                false, false, false, true, false);
        assertTrue(predicate.test(new OrderBuilder().withComment("Please say hi to me").build()));

        // Mixed comment keywords with full matching keyword of comment
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("5", "hi", "S608831",
                "Put", "it", "at", "my", "shoe", "rack"),
            false, false, false, false,
            false, false, false, true, false);
        assertTrue(predicate.test(new OrderBuilder().withComment("Put it at my shoe rack").build()));

        // Any one word matches the given comment
        predicate = new OrderContainsKeywordsPredicate(Collections.singletonList("Hi"),
            false, false, false, false,
            false, false, false, true, false);
        assertTrue(predicate.test(new OrderBuilder().withComment("Say hi to me when you're here!").build()));
    }

    @Test
    public void test_itemTypeContainsKeywordsSpecificSearch_returnsTrue() {
        // One keyword full match comment given
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Arrays.asList("Plastic"),
                false, false, false, false,
                false, false, false, false, true);
        assertTrue(predicate.test(new OrderBuilder().withItemType("Plastic").build()));

        // Only one full matching keyword of comment given
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("Gold", "Silver", "Plastic"),
            false, false, false, false,
            false, false, false, false, true);
        assertTrue(predicate.test(new OrderBuilder().withItemType("Gold").build()));
    }

    // Test for overloaded fail cases
    @Test void test_fieldsOtherThanSpecifiedFieldContainsKeywordSpecificSearch_returnFalse() {
        // Matching keywords in Transaction ID field but did not search with Transaction ID prefix
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Collections.singletonList("1234567890"),
                false, true, true, true,
                true, true, true, true, true);
        assertFalse(predicate.test(new OrderBuilder().withTid("1234567890").build()));

        // Matching keywords in name field but did not search with name prefix
        predicate = new OrderContainsKeywordsPredicate(Collections.singletonList("Alice"),
                true, false, true, true,
                true, true, true, true, true);
        assertFalse(predicate.test(new OrderBuilder().withName("Alice Bob").build()));

        // Matching keywords in phone field but did not search with phone prefix
        predicate = new OrderContainsKeywordsPredicate(Collections.singletonList("12345678"),
            true, true, false, true,
            true, true, true, true, true);
        assertFalse(predicate.test(new OrderBuilder().withPhone("12345678").build()));

        // Matching keywords in address field but did not search with address prefix
        predicate = new OrderContainsKeywordsPredicate(Collections.singletonList("Geylang"),
            true, true, true, false,
            true, true, true, true, true);
        assertFalse(predicate.test(new OrderBuilder().withAddress("Geylang").build()));

        // Matching keywords in timestamp field but did not search with timestamp prefix
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("2020-02-02", "1500"),
            true, true, true, true,
            false, true, true, true, true);
        assertFalse(predicate.test(new OrderBuilder().withTimeStamp("2020-02-02 1500").build()));

        // Matching keywords in warehouse address field but did not search with warehouse address prefix
        predicate = new OrderContainsKeywordsPredicate(Collections.singletonList("Geylang"),
            true, true, true, true,
            true, false, true, true, true);
        assertFalse(predicate.test(new OrderBuilder().withWarehouse("Geylang").build()));

        // Matching keywords in COD field but did not search with COD prefix
        predicate = new OrderContainsKeywordsPredicate(Collections.singletonList("$4"),
            true, true, true, true,
            true, true, false, true, true);
        assertFalse(predicate.test(new OrderBuilder().withCash("$4").build()));

        // Matching keywords in comment fields but did not search comment prefix
        predicate = new OrderContainsKeywordsPredicate(Collections.singletonList("hi"),
            true, true, true, true,
            true, true, true, false, true);
        assertFalse(predicate.test(new OrderBuilder().withComment("hi").build()));

        // Matching keywords in itemType field but did not search with itemType prefix
        predicate = new OrderContainsKeywordsPredicate(Collections.singletonList("Plastic"),
            true, true, true, true,
            true, true, true, true, false);
        assertFalse(predicate.test(new OrderBuilder().withItemType("Plastic").build()));
    }
}
