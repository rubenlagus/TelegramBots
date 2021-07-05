package org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.support.AInterceptor;
import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.support.BInterceptor;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AInterceptorRegistryTest {

    private final static Update UPDATE             = null;
    private final static PayloadStorage STORAGE    = null;

    @Test
    public void shouldCallBeforeAndAfterInterceptor(){
        InterceptorRegistry registry = new InterceptorRegistry();

        Interceptor aInterceptor = Mockito.mock(AInterceptor.class);
        Interceptor bInterceptor = Mockito.mock(BInterceptor.class);
        Interceptor cInterceptor = Mockito.mock(Interceptor.class);
        Interceptor dInterceptor = Mockito.mock(Interceptor.class);

        registry.registerInterceptor(aInterceptor);
        registry.registerInterceptor(bInterceptor);
        registry.registerInterceptor(cInterceptor);
        registry.registerInterceptor(dInterceptor);

        assertEquals(registry.getFirstAnnotatedInterceptor(Stage.BEFORE).size(), 1);
        assertEquals(registry.getFirstAnnotatedInterceptor(Stage.AFTER).size(), 1);
        assertEquals(registry.getLastAnnotation(Stage.BEFORE).size(), 1);
        assertEquals(registry.getLastAnnotation(Stage.AFTER).size(), 1);

        when(aInterceptor.before(UPDATE, STORAGE)).thenReturn(false);
        when(bInterceptor.before(UPDATE, STORAGE)).thenReturn(false);
        when(cInterceptor.before(UPDATE, STORAGE)).thenReturn(false);
        when(dInterceptor.before(UPDATE, STORAGE)).thenReturn(false);

        InOrder order = inOrder(aInterceptor, bInterceptor);

        assertFalse(registry.callBeforeInterceptor(UPDATE, STORAGE));

        order.verify(aInterceptor).before(UPDATE, STORAGE);
        order.verify(bInterceptor).before(UPDATE, STORAGE);

        verify(cInterceptor).before(UPDATE, STORAGE);
        verify(dInterceptor).before(UPDATE, STORAGE);

        doNothing().when(aInterceptor).after(UPDATE, STORAGE);
        doNothing().when(bInterceptor).after(UPDATE, STORAGE);
        doNothing().when(cInterceptor).after(UPDATE, STORAGE);
        doNothing().when(dInterceptor).after(UPDATE, STORAGE);

        order = inOrder(bInterceptor, aInterceptor);

        registry.callAfterInterceptor(UPDATE, STORAGE, new ArrayList<>());

        order.verify(bInterceptor).after(UPDATE, STORAGE);
        order.verify(aInterceptor).after(UPDATE, STORAGE);

        verify(cInterceptor).after(UPDATE, STORAGE);
        verify(dInterceptor).after(UPDATE, STORAGE);
    }


}