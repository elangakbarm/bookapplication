package com.valensi.bookapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.valensi.bookapp.web.rest.TestUtil;

public class ShelfTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Shelf.class);
        Shelf shelf1 = new Shelf();
        shelf1.setId(1L);
        Shelf shelf2 = new Shelf();
        shelf2.setId(shelf1.getId());
        assertThat(shelf1).isEqualTo(shelf2);
        shelf2.setId(2L);
        assertThat(shelf1).isNotEqualTo(shelf2);
        shelf1.setId(null);
        assertThat(shelf1).isNotEqualTo(shelf2);
    }
}
