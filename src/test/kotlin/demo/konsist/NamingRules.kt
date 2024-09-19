package demo.konsist

import demo.NamingRules
import org.junit.jupiter.api.Test

interface NamingRules : NamingRules {

    @Test
    override fun `there are no poorly named packages`() {
        // TODO: exercise left to the reader...
    }

    @Test
    override fun `there are no poorly named classes`() {
        // TODO: exercise left to the reader...
    }
}
