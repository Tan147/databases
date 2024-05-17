package ru.itmo.databases.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit Tests for Service")
public class ServiceTest {
    @Mock       //подложный объект
    Dao dao;

    Service service;

    @BeforeEach
    void init() {
        System.out.println("@BeforeEach - executes before each test method in this class");
        service = new Service(dao);
    }

    @Test
    public void saveString_Null_ReturnFailed() throws TestException {
        String expected = "FAIL";

        Mockito.when(dao.addString(Mockito.isNull())).thenThrow(TestException.class);
        Assertions.assertEquals(expected, service.saveString(null));
    }
    //Mockito.when - вызвали метод у подложного объекта
    //Mockito.isNull() - вызвали метод у подложного аргумента

    @Test
    public void saveString_NotNullAndNotEmpty_ReturnSuccess() throws TestException {
        String expected = "SUCCESS";

        Mockito.when(dao.addString(Mockito.any(String.class))).thenReturn(true);

        Assertions.assertAll(
                "not null and not empty",
                () -> Assertions.assertEquals(expected, service.saveString("hello")),
                () -> Assertions.assertEquals(expected, service.saveString("world")),
                () -> Assertions.assertEquals(expected, service.saveString("black")),
                () -> Assertions.assertEquals(expected, service.saveString("sun")),
                () -> Assertions.assertEquals(expected, service.saveString("tree"))
        );
    }
}