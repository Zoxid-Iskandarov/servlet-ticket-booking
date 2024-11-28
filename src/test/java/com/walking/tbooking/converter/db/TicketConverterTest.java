package com.walking.tbooking.converter.db;

import com.walking.tbooking.domain.ticket.ServiceClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TicketConverterTest {
    private final TicketConverter converter = new TicketConverter();

    @Test
    void convert_value_success() throws SQLException {
        var rs = mock(ResultSet.class);
        doReturn(true)
                .doReturn(false)
                .when(rs).next();
        doReturn("A1").when(rs).getString("seat_number");
        doReturn(ServiceClass.BUSINESS.name()).when(rs).getString("service_class");

        var actual = converter.convert(rs);

        assertEquals(1, actual.size());
        verify(rs, times(2)).next();

        verify(rs, times(3)).getLong(any());
        verify(rs, times(2)).getString(any());
        verify(rs, times(2)).getInt(any());
        verify(rs).getBoolean(any());
    }

    @Test
    void convert_multipleValues_success() throws SQLException {
        var rs = mock(ResultSet.class);
        doReturn(true)
                .doReturn(true)
                .doReturn(false)
                .when(rs).next();
        doReturn("A1").when(rs).getString("seat_number");
        doReturn(ServiceClass.BUSINESS.name()).when(rs).getString("service_class");


        var actual = converter.convert(rs);

        assertEquals(2, actual.size());
        verify(rs, times(3)).next();

        verify(rs, times(6)).getLong(any());
        verify(rs, times(4)).getString(any());
        verify(rs, times(4)).getInt(any());
        verify(rs, times(2)).getBoolean(any());
    }

    @Test
    void convert_empty_success() throws SQLException {
        var rs = mock(ResultSet.class);
        doReturn(false).when(rs).next();

        var actual = converter.convert(rs);

        assertTrue(actual.isEmpty());
        verify(rs).next();

        verify(rs, never()).getLong(any());
        verify(rs, never()).getString(any());
        verify(rs, never()).getInt(any());
        verify(rs, never()).getBoolean(any());
    }

    @Test
    void convert_SQLException_failed() throws SQLException {
        var rs = mock(ResultSet.class);
        doThrow(SQLException.class).when(rs).next();

        Executable executable = () -> converter.convert(rs);

        assertThrows(SQLException.class, executable);
        verify(rs).next();

        verify(rs, never()).getLong(any());
        verify(rs, never()).getString(any());
        verify(rs, never()).getInt(any());
        verify(rs, never()).getBoolean(any());
    }
}