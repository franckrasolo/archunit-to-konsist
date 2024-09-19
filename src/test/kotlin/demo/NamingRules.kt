package demo

import org.junit.platform.commons.annotation.Testable

interface NamingRules {
    val basePackage: String

    @Testable
    fun `there are no poorly named packages`()

    @Testable
    fun `there are no poorly named classes`()
}
