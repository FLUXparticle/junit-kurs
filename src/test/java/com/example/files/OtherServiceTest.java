package com.example.files;

import com.google.inject.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

class OtherServiceTest {

    @Test
    void fileDoesNotExists() {
        FileService fileService = Mockito.mock(FileService.class);

        Injector injector = Guice.createInjector(
                binder -> binder.bind(FileService.class).toInstance(fileService)
        );

        OtherService otherService = injector.getInstance(OtherService.class);

        otherService.doWork();

    }

    @Test
    void cannotReadFile() {

    }

}
