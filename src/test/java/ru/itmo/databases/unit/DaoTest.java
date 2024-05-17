package ru.itmo.databases.unit;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit Tests for Dao")
@TestMethodOrder(MethodOrderer.DisplayName.class) // порядок выполнения (запуска тестов) - по алфавиту @DisplayName
public class DaoTest {
    private Dao dao; //объявляем ссылку на тетсируемый объект

    @BeforeAll
    static void setup() {
        System.out.println("@BeforeAll - executes once before all test methods in this class");
    }
    //@BeforeAll - тогда эти инструкции будут выполняться перед любым действием

    @BeforeEach
    void init() {
        System.out.println("@BeforeEach - executes before each test method in this class");
        dao = new Dao(); //перед каждым методом инициализируем новый объект, чтобы тестированием было чистым
        // @BeforeEach - тогда эти инструкции будут выполняться перед каждым методом (но не перед assert, именно перед методом)
    }

    //Как формируется имя метода теста
    // TestClass_TestMethod_ConditionAndExpectedResult - это если с классом
    // TestMethod_Condition_ExpectedResult   - например AddString_Null_ThrowsException
    //  где:
    //  TestMethod - имя метода, к-рый тестируем
    //  Condition - условие, по к-рому осущ-ся тест(условие вызова метода
    // ExpectedResult - какой рез-т мы ожидаем

    // а TestClass пишем в названии тест-метода, когда у нас нет структуры, при которой на исходный класс создается
    // свой тест класс, т.е. когда у нас 1 класс теста на все существующие исходные классы,
    // например один тест-метода из одного класса, другой тест-метода из другого класса

    @Test //каждый тестовый метод отмечается такой аннотацией
    public void AddString_Null_ThrowsException() throws TestException {
        String stringToAdd = null;
        assertThrows(TestException.class, //ожидаем, что будет выброшен экземпляр класса TestException
                () -> dao.addString(stringToAdd), //лямбда и вызов метода, который мы тестируем
                "AddString_Null_ThrowsException failed");
    }
    // assert - они статические, через них делаем предположение, что метод сделает, напр, если мы ждем что будет нулл,
    // или что будет эксепшен, или что метод вернет массив,
    // перед assert нет имени класса, потмоу что они импортирваны через import static


    @Test
    public void AddString_Empty_ThrowsException() throws TestException {
        String stringToAdd = "";   //задаем пустую строчку
        assertThrows(TestException.class,   //предполагаем, что с пустой строчкой будет выброшен TestException
                () -> dao.addString(stringToAdd),
                "AddString_Empty_ThrowsException failed");
    }

    @Test
    public void AddString_NullOrEmpty_ThrowsException() throws TestException {
        assertAll("throws on empty or null",
                () -> assertThrows(TestException.class,
                        () -> dao.addString(null),
                        "null failed"),
                () -> assertThrows(TestException.class,
                        () -> dao.addString(""),
                        "empty failed")
        );
    }
    //assertAll - тест будет пройдет, если все условия теста пройдут (все лямбды)

    @Test
    public void AddString_Blank_NotThrowsException() throws TestException {
        assertDoesNotThrow(() -> dao.addString(" "), "blank failed");
    }

    @Test
    public void AddString_Unique_ReturnTrue() {
        assertAll("true on unique string",
                () -> assertTrue(dao.addString("hello"), "hello failed"),
                () -> assertTrue(dao.addString("world"), "world failed"),
                () -> assertTrue(dao.addString("black"), "black failed"),
                () -> assertTrue(dao.addString("sun"), "sun failed"),
                () -> assertTrue(dao.addString("tree"), "tree failed")
        );
    }

    @Test
    public void AddString_NonUnique_ReturnFalse() throws TestException {
        dao.addString("hello");
        dao.addString("world");
        dao.addString("black");
        dao.addString("sun");
        dao.addString("tree");

        assertAll("false on non unique string",
                () -> assertFalse(dao.addString("hello"), "hello failed"),
                () -> assertFalse(dao.addString("world"), "world failed"),
                () -> assertFalse(dao.addString("black"), "black failed"),
                () -> assertFalse(dao.addString("sun"), "sun failed"),
                () -> assertFalse(dao.addString("tree"), "tree failed")
        );
    }

    @Test
    @Disabled("Not Implemented Yet")
    public void NumberOfStrings_Empty_ReturnZero() {
        assertEquals(0, dao.getNumberOfStrings());
    }
    // @Disabled - так отмечаем метод, который сейчас не должен сработать, напр если мы его не дописали,
    // а нам надо запустить остальные тесты

}