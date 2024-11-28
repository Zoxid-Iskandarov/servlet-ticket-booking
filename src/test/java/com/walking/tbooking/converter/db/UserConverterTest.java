package com.walking.tbooking.converter.db;

import com.walking.tbooking.domain.users.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserConverterTest {
    private final UserConverter converter = new UserConverter();

    @Test
    void convert_user_value_success() throws SQLException {
        var rs = mock(ResultSet.class);
        doReturn(true).when(rs).next();
        doReturn(Role.USER.name()).when(rs).getString("role");
        doReturn(new Timestamp(0)).when(rs).getTimestamp(any());

        var actual = converter.convert(rs);

        assertTrue(actual.isPresent());
        verify(rs).next();

        verify(rs).getLong(any());
        verify(rs, times(6)).getString(any());
        verify(rs).getTimestamp(any());
        verify(rs).getBoolean(any());
    }

    @Test
    void convert_admin_value_success() throws SQLException {
        var rs = mock(ResultSet.class);
        doReturn(true).when(rs).next();
        doReturn(Role.ADMIN.name()).when(rs).getString("role");
        doReturn(new Timestamp(0)).when(rs).getTimestamp(any());

        var actual = converter.convert(rs);

        assertTrue(actual.isPresent());
        verify(rs).next();

        verify(rs).getLong(any());
        verify(rs, times(6)).getString(any());
        verify(rs).getTimestamp(any());
        verify(rs).getBoolean(any());
    }

    @Test
    void convert_empty_success() throws SQLException {
        var rs = mock(ResultSet.class);
        doReturn(false).when(rs).next();

        var actual = converter.convert(rs);

        assertEquals(Optional.empty(), actual);
        verify(rs).next();

        verify(rs, never()).getLong(any());
        verify(rs, never()).getString(any());
        verify(rs, never()).getTimestamp(any());
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
        verify(rs, never()).getTimestamp(any());
        verify(rs, never()).getBoolean(any());
    }
}