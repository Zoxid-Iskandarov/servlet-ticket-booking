package com.walking.tbooking.converter.db;

import com.walking.tbooking.domain.passenger.Gender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PassengerConverterTest {
    private final PassengerConverter converter = new PassengerConverter();

    @Test
    void convert_male_value_success() throws SQLException {
        var rs = mock(ResultSet.class);
        doReturn(true).when(rs).next();
        doReturn(Gender.MALE.name()).when(rs).getString("gender");
        doReturn(new Date(0)).when(rs).getDate(any());

        var actual = converter.convert(rs);

        assertTrue(actual.isPresent());
        verify(rs).next();

        verify(rs, times(2)).getLong(any());
        verify(rs, times(5)).getString(any());
        verify(rs).getDate(any());
    }

    @Test
    void convert_female_value_success() throws SQLException {
        var rs = mock(ResultSet.class);
        doReturn(true).when(rs).next();
        doReturn(Gender.FEMALE.name()).when(rs).getString("gender");
        doReturn(new Date(0)).when(rs).getDate(any());

        var actual = converter.convert(rs);

        assertTrue(actual.isPresent());
        verify(rs).next();

        verify(rs, times(2)).getLong(any());
        verify(rs, times(5)).getString(any());
        verify(rs).getDate(any());
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
        verify(rs, never()).getDate(any());
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
        verify(rs, never()).getDate(any());
    }

    @Test
    void convertMany_value_success() throws SQLException {
        var rs = mock(ResultSet.class);
        doReturn(true)
                .doReturn(false)
                .when(rs).next();
        doReturn(Gender.MALE.name()).when(rs).getString("gender");
        doReturn(new Date(0)).when(rs).getDate(any());

        var actual = converter.convertMany(rs);

        assertEquals(1, actual.size());
        verify(rs, times(2)).next();

        verify(rs, times(2)).getLong(any());
        verify(rs, times(5)).getString(any());
        verify(rs).getDate(any());
    }

    @Test
    void convertMany_multipleValues_success() throws SQLException {
        var rs = mock(ResultSet.class);
        doReturn(true)
                .doReturn(true)
                .doReturn(false)
                .when(rs).next();
        doReturn(Gender.MALE.name()).when(rs).getString("gender");
        doReturn(new Date(0)).when(rs).getDate(any());

        var actual = converter.convertMany(rs);

        assertEquals(2, actual.size());
        verify(rs, times(3)).next();

        verify(rs, times(4)).getLong(any());
        verify(rs, times(10)).getString(any());
        verify(rs, times(2)).getDate(any());
    }

    @Test
    void convertMany_empty_success() throws SQLException {
        var rs = mock(ResultSet.class);
        doReturn(false).when(rs).next();

        var actual = converter.convertMany(rs);

        assertEquals(0, actual.size());
        assertTrue(actual.isEmpty());
        verify(rs).next();

        verify(rs, never()).getLong(any());
        verify(rs, never()).getString(any());
        verify(rs, never()).getDate(any());
    }

    @Test
    void convertMany_SQLException_failed() throws SQLException {
        var rs = mock(ResultSet.class);
        doThrow(SQLException.class).when(rs).next();

        Executable executable = () -> converter.convertMany(rs);

        assertThrows(SQLException.class, executable);
        verify(rs).next();

        verify(rs, never()).getLong(any());
        verify(rs, never()).getString(any());
        verify(rs, never()).getDate(any());
    }
}