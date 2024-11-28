package com.walking.tbooking.converter.db;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FlightConverterTest {
    private final FlightConverter converter = new FlightConverter();

    @Test
    void convert_value_success() throws SQLException {
        var rs = mock(ResultSet.class);
        doReturn(true).when(rs).next();
        doReturn(new Timestamp(0)).when(rs).getTimestamp(any());

        var actual = converter.convert(rs);

        assertTrue(actual.isPresent());
        verify(rs).next();

        verify(rs).getLong(any());
        verify(rs, times(2)).getTimestamp(any());
        verify(rs, times(4)).getInt(any());
    }

    @Test
    void convert_empty_success() throws SQLException {
        var rs = mock(ResultSet.class);
        doReturn(false).when(rs).next();

        var actual = converter.convert(rs);

        assertTrue(actual.isEmpty());
        verify(rs).next();

        verify(rs, never()).getLong(any());
        verify(rs, never()).getTimestamp(any());
        verify(rs, never()).getInt(any());
    }

    @Test
    void convert_SQLException_failed() throws SQLException {
        var rs = mock(ResultSet.class);
        doThrow(SQLException.class).when(rs).next();

        Executable executable = () -> converter.convert(rs);

        assertThrows(SQLException.class, executable);
        verify(rs).next();

        verify(rs, never()).getLong(any());
        verify(rs, never()).getTimestamp(any());
        verify(rs, never()).getInt(any());
    }

    @Test
    void convertMany_value_success() throws SQLException {
        var rs = mock(ResultSet.class);
        doReturn(true)
                .doReturn(false)
                .when(rs).next();
        doReturn(new Timestamp(0)).when(rs).getTimestamp(any());

        var actual = converter.convertMany(rs);
        assertEquals(1, actual.size());
        verify(rs, times(2)).next();

        verify(rs).getLong(any());
        verify(rs, times(2)).getTimestamp(any());
        verify(rs, times(4)).getInt(any());
    }

    @Test
    void convertMany_multipleValues_success() throws SQLException {
        var rs = mock(ResultSet.class);
        doReturn(true)
                .doReturn(true)
                .doReturn(false)
                .when(rs).next();
        doReturn(new Timestamp(0)).when(rs).getTimestamp(any());

        var actual = converter.convertMany(rs);
        assertEquals(2, actual.size());
        verify(rs, times(3)).next();

        verify(rs, times(2)).getLong(any());
        verify(rs, times(4)).getTimestamp(any());
        verify(rs, times(8)).getInt(any());
    }

    @Test
    void convertMany_empty_success() throws SQLException {
        var rs = mock(ResultSet.class);
        doReturn(false).when(rs).next();

        var actual = converter.convertMany(rs);
        assertTrue(actual.isEmpty());
        verify(rs).next();

        verify(rs, never()).getLong(any());
        verify(rs, never()).getTimestamp(any());
        verify(rs, never()).getInt(any());
    }

    @Test
    void convertMany_SQLException_failed() throws SQLException {
        var rs = mock(ResultSet.class);
        doThrow(SQLException.class).when(rs).next();

        Executable executable = () -> converter.convertMany(rs);
        assertThrows(SQLException.class, executable);
        verify(rs).next();

        verify(rs, never()).getLong(any());
        verify(rs, never()).getTimestamp(any());
        verify(rs, never()).getInt(any());
    }
}