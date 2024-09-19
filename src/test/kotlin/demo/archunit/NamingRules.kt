package demo.archunit

import com.tngtech.archunit.junit.ArchTest
import demo.NamingRules

interface NamingRules : ArchUnitRules, NamingRules {

    @ArchTest
    override fun `there are no poorly named packages`() {
        // TODO: exercise left to the reader...
    }

    @ArchTest
    override fun `there are no poorly named classes`() {
        // TODO: exercise left to the reader...
    }
}
